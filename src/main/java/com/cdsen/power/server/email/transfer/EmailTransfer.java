package com.cdsen.power.server.email.transfer;

import com.cdsen.email.Constant;
import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.oss.OssClientManager;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.email.dao.po.EmailPO;
import com.cdsen.power.server.email.model.vo.EmailVO;
import com.cdsen.power.server.email.model.vo.SimpleEmailVO;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/11/5 10:40
 */
public class EmailTransfer {

    public static final Function<EmailPO, SimpleEmailVO> PO_TO_SIMPLE_VO = po -> {
        SimpleEmailVO vo = new SimpleEmailVO();
        vo.setId(po.getId());
        vo.setSubject(po.getSubject());
        vo.setFrom(po.getFrom());
        vo.setIsNew(po.getIsNew());
        vo.setReplySign(po.getReplySign());
        vo.setSentDate(po.getSentDate());
        vo.setContainAttachment(StringUtils.isNotBlank(po.getAttachments()));
        return vo;
    };

    public static final Function<EmailPO, EmailVO> PO_TO_VO = po -> {
        OssClient ossClient = OssClientManager.getClient("EMAIL");
        EmailVO vo = new EmailVO();
        vo.setId(po.getId());
        vo.setSubject(po.getSubject());
        vo.setFrom(po.getFrom());
        vo.setIsNew(po.getIsNew());
        vo.setReplySign(po.getReplySign());
        vo.setSentDate(po.getSentDate());
        String attachments = po.getAttachments();
        vo.setContainAttachment(StringUtils.isNotBlank(attachments));
        vo.setMessageId(po.getMessageId());
        String content = new String(po.getContent(), StandardCharsets.UTF_8);
        vo.setContent(content);
        vo.setTo(po.getTo());
        vo.setCc(po.getCc());
        vo.setBcc(po.getBcc());
        vo.setIsHtml(po.getIsHtml());
        if (vo.getContainAttachment()) {
            Long userId = SecurityUtils.currentUserDetails().getUserId();
            String[] split = attachments.split(",");
            if (split.length == 1 && content.contains(Constant.SRC_CID_PREFIXX)) {
                String url = ossClient.generatePreSignedUrl(30, TimeUnit.MINUTES, userId.toString().concat("/").concat(po.getUid()).concat("/").concat(split[0]));
                String rex = Constant.CID_SIGN + StringUtils.substringBetween(content, Constant.SRC_CID_PREFIXX, "\"");
                vo.setContent(vo.getContent().replace(rex, url));
            } else {
                List<Map<String, String>> attachmentList = Arrays.stream(split).map(x -> {
                    String url = ossClient.generatePreSignedUrl(30, TimeUnit.MINUTES, userId.toString().concat("/").concat(po.getUid()).concat("/").concat(x));
                    if (content.contains(x)) {
                        vo.setContent(vo.getContent().replace(x, url));
                    } else if (x.contains(Constant.JPG) || x.contains(Constant.PNG)) {
                        String cid = StringUtils.substringBeforeLast(x, ".");
                        if (content.contains(cid)) {
                            vo.setContent(vo.getContent().replace(cid, url));
                        }
                    }
                    Map<String, String> map = new HashMap<>(2);
                    map.put("fileName", x);
                    map.put("url", url);
                    return map;
                }).collect(Collectors.toList());
                vo.setAttachments(attachmentList);
            }
        }
        return vo;
    };
}

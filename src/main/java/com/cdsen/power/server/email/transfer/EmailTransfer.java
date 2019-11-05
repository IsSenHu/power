package com.cdsen.power.server.email.transfer;

import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.oss.OssClientManager;
import com.cdsen.power.server.email.dao.po.EmailPO;
import com.cdsen.power.server.email.model.vo.EmailVO;
import com.cdsen.power.server.email.model.vo.SimpleEmailVO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        vo.setContainAttachment(StringUtils.isNotBlank(po.getAttachments()));
        vo.setMessageId(po.getMessageId());
        vo.setContent(po.getContent());
        vo.setTo(po.getTo());
        vo.setCc(po.getCc());
        vo.setBcc(po.getBcc());
        if (vo.getContainAttachment()) {
            List<Map<String, String>> attachmentList = Arrays.stream(po.getAttachments().split(",")).map(x -> {
                Map<String, String> map = new HashMap<>(2);
                map.put("fileName", x);
                map.put("url", ossClient.generatePreSignedUrl(30, TimeUnit.MINUTES, po.getMessageId().concat("-").concat(x)));
                return map;
            }).collect(Collectors.toList());
            vo.setAttachments(attachmentList);
        }
        return vo;
    };
}

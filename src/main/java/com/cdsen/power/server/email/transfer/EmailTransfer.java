package com.cdsen.power.server.email.transfer;

import com.cdsen.power.server.email.dao.po.EmailPO;
import com.cdsen.power.server.email.model.vo.SimpleEmailVO;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

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
}

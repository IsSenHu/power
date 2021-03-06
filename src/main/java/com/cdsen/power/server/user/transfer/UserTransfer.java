package com.cdsen.power.server.user.transfer;

import com.cdsen.power.server.user.dao.po.UserPO;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.vo.UserVO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/5 20:09
 */
public class UserTransfer {

    public static final Function<UserPO, UserVO> PO_TO_VO = po -> {
        UserVO vo = new UserVO();
        vo.setId(po.getId());
        vo.setUsername(po.getUsername());
        vo.setPassword(null);
        vo.setIsAccountNonLocked(po.getIsAccountNonLocked());
        vo.setIsAccountNonExpired(po.getIsAccountNonExpired());
        vo.setIsEnabled(po.getIsEnabled());
        vo.setIsCredentialsNonExpired(po.getIsCredentialsNonExpired());
        vo.setIntroduction(po.getIntroduction());
        vo.setAvatar(po.getAvatar());
        vo.setEmail(po.getEmail());
        if (po.getCreateTime() != null) {
            vo.setCreateTime(po.getCreateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        }
        return vo;
    };

    public static final Function<UserCreateAO, UserPO> AO_TO_PO = ao -> {
        UserPO po = new UserPO();
        po.setUsername(ao.getUsername());
        po.setIsAccountNonLocked(true);
        po.setIsAccountNonExpired(true);
        po.setIsEnabled(ao.getIsEnabled());
        po.setIsCredentialsNonExpired(true);
        po.setEmail(ao.getEmail());
        po.setIntroduction(ao.getIntroduction());
        return po;
    };
}

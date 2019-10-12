package com.cdsen.power.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.cdsen.interfaces.Codes;
import com.cdsen.interfaces.DubboResult;
import com.cdsen.interfaces.user.service.UserApiService;
import com.cdsen.interfaces.user.vo.UserVO;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.session.SessionManage;
import com.cdsen.power.core.security.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author HuSen
 * create on 2019/10/12 14:48
 */
@Service
@Component
public class UserApiServiceImpl implements UserApiService {

    private final JwtUtils jwtUtils;
    private final SessionManage sessionManage;

    public UserApiServiceImpl(JwtUtils jwtUtils, @Qualifier("redisSessionManage") SessionManage sessionManage) {
        this.jwtUtils = jwtUtils;
        this.sessionManage = sessionManage;
    }

    @Override
    public DubboResult<UserVO> find(String token) {
        if (StringUtils.isBlank(token)) {
            return DubboResult.of(Codes.PARAM_ERROR, "Token不能为空!");
        }

        String username = jwtUtils.getUsernameFromToken(token);
        if (StringUtils.isBlank(username)) {
            return DubboResult.of(Codes.PARAM_ERROR, "错误的Token!");
        }

        Session session = sessionManage.getSession(username);
        if (Objects.isNull(session)) {
            return DubboResult.of(Codes.PARAM_ERROR, "错误的Token!");
        }

        Long userId = session.getUserId();
        if (Objects.isNull(userId)) {
            return DubboResult.of(Codes.PARAM_ERROR, "错误的Token!");
        }

        if (!session.isEnabled()) {
            return DubboResult.of(Codes.PARAM_ERROR, "当前用户不可用!");
        }

        if (!session.isAccountNonLocked()) {
            return DubboResult.of(Codes.PARAM_ERROR, "当前用户已被锁定!");
        }

        UserVO vo = new UserVO();
        vo.setUserId(userId);
        vo.setUsername(username);
        return DubboResult.of(vo);
    }
}

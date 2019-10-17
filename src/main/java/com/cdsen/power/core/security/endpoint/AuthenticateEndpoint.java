package com.cdsen.power.core.security.endpoint;

import com.cdsen.apollo.AppProperties;
import com.cdsen.apollo.ConfigUtils;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.security.model.LoginVO;
import com.cdsen.power.core.security.model.Token;
import com.cdsen.power.core.security.model.UserDetailsImpl;
import com.cdsen.power.core.security.util.JwtUtils;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.user.model.cons.UserStatusType;
import com.cdsen.power.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuSen
 * create on 2019/8/29 9:55
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticateEndpoint {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateEndpoint(UserService userService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登录，获取token
     *
     * @param login 登录数据模型
     * @return 登录结果
     */
    @PostMapping("/login")
    public JsonResult<Token> login(@RequestBody LoginVO login) {
        return userService.login(login);
    }

    /**
     * 登出，注销当前用户
     *
     * @param request HttpServletRequest
     * @return 登出结果
     */
    @PostMapping("/logout")
    public JsonResult logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    /**
     * 检查token
     *
     * @param request HttpServletRequest
     * @return 是否合法
     */
    @PostMapping("/check")
    public JsonResult<Boolean> check(HttpServletRequest request) {
        String header = ConfigUtils.getProperty(AppProperties.Security.HEADER, "authorization");
        String token = request.getHeader(header);
        boolean expired = jwtUtils.isExpired(token);
        return JsonResult.of(!expired);
    }

    /**
     * 用户锁定
     *
     * @param request HttpServletRequest
     * @return 锁定结果
     */
    @PostMapping("/lock")
    public JsonResult lock(HttpServletRequest request) {
        String header = ConfigUtils.getProperty(com.cdsen.apollo.AppProperties.Security.HEADER, "authorization");
        String token = request.getHeader(header);
        Long userId = SecurityUtils.currentUserDetails().getUserId();
        return userService.changeUserStatus(userId, token, UserStatusType.ACCOUNT_NON_LOCKED, false);
    }

    /**
     * 用户解锁
     *
     * @param password 密码
     * @param request  HttpServletRequest
     * @return 解锁结果
     */
    @PostMapping("/unlock/{password}")
    public JsonResult unlock(@PathVariable String password, HttpServletRequest request) {
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String header = ConfigUtils.getProperty(com.cdsen.apollo.AppProperties.Security.HEADER, "authorization");
            String token = request.getHeader(header);
            Long userId = userDetails.getUserId();
            return userService.changeUserStatus(userId, token, UserStatusType.ACCOUNT_NON_LOCKED, true);
        } else {
            return JsonResult.of(10003, "密码错误");
        }
    }

    /**
     * 是否被锁定
     *
     * @return 是否被锁定
     */
    @PostMapping("/isLocked")
    public JsonResult<Boolean> isLocked() {
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        return JsonResult.of(!userDetails.isAccountNonLocked());
    }
}

package com.cdsen.power.core.security.endpoint;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.security.model.LoginVO;
import com.cdsen.power.core.security.model.Token;
import com.cdsen.power.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/8/29 9:55
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticateEndpoint {

    private final UserService userService;

    public AuthenticateEndpoint(UserService userService) {
        this.userService = userService;
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
     * @return 登出结果
     */
    @PostMapping("/logout")
    public JsonResult logout() {
        return userService.logout();
    }
}

package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.cons.PreAuthorizes;
import com.cdsen.power.server.user.model.query.UserQuery;
import com.cdsen.power.server.user.model.vo.UserVO;
import com.cdsen.power.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/5 16:13
 */
@Slf4j
@RestController
@SecurityModule(name = "用户")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册用户
     *
     * @param ao 注册用户数据模型
     * @return 注册结果
     */
    @PutMapping
    public JsonResult<UserVO> register(@RequestBody UserCreateAO ao) {
        JsonResult<UserVO> result;
        // 为什么不放在一个方法中执行，因为开启了Redis的事务，进行查询的方法不会有返回值，所以查询先在事务外执行
        // 当进行写入操作的时候则在事务内执行
        boolean enable = userService.checkUsername(ao.getUsername());
        result = enable ? userService.register(ao) : JsonResult.of(10003, "用户名不可用");
        return result;
    }

    /**
     * 激活用户
     *
     * @param username 用户名
     * @param code     验证码
     * @return 激活结果
     */
    @GetMapping("/activate/{username}/{code}")
    public JsonResult<UserVO> activate(@PathVariable String username, @PathVariable Integer code) {
        boolean correct = userService.verifyCode(username, code);
        return correct ? userService.activate(username) : JsonResult.of(10004, "验证失败");
    }

    /**
     * 分页查询用户
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PreAuthorize(PreAuthorizes.User.QUERY)
    @Permission("分页查询用户")
    @PostMapping("/page")
    public JsonResult<PageResult<UserVO>> page(@RequestBody IPageRequest<UserQuery> iPageRequest) {
        return userService.page(iPageRequest);
    }
}

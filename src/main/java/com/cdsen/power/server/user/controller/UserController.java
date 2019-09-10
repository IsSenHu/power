package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.redis.lock.DistributedLock;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.vo.UserVO;
import com.cdsen.power.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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
    private final DistributedLock distributedLock;

    public UserController(UserService userService, DistributedLock distributedLock) {
        this.userService = userService;
        this.distributedLock = distributedLock;
    }

    /**
     * 注册用户
     *
     * @param ao 注册用户数据模型
     * @return 注册结果
     */
    @PutMapping
    public JsonResult<UserVO> register(@RequestBody UserCreateAO ao) {
        RLock rLock = distributedLock.getLock(ao.getUsername());
        JsonResult<UserVO> result;
        try {
            // 为什么不放在一个方法中执行，因为开启了Redis的事务，进行查询的方法不会有返回值，所以查询先在事务外执行
            // 当进行写入操作的时候则在事务内执行
            // 同时在这个过程中对预注册的用户名加锁，保证了整体的事务完整和原子性
            // 并且是对用户名加锁，所以其他用户名进来注册并不会阻塞，只阻塞相同的用户名
            boolean isLock = rLock.tryLock(5, TimeUnit.SECONDS);
            if (isLock) {
                boolean enable = userService.checkUsername(ao.getUsername());
                result = enable ? userService.register(ao) : JsonResult.of(10003, "用户名不可用");
            } else {
                result = JsonResult.of(10005, "当前系统繁忙");
            }
        } catch (Exception e) {
            log.error("卧槽，竟然出问题了:", e);
            result = JsonResult.of(10005, "注册失败，请稍后重试");
        } finally {
            rLock.unlock();
        }
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
}

package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.redis.lock.DistributedLock;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.ao.UserUpdateAO;
import com.cdsen.power.server.user.model.cons.PreAuthorizes;
import com.cdsen.power.server.user.model.cons.UserStatusType;
import com.cdsen.power.server.user.model.query.UserQuery;
import com.cdsen.power.server.user.model.vo.UserVO;
import com.cdsen.power.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.security.access.prepost.PreAuthorize;
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
        RLock lock = distributedLock.getLock(ao.getUsername());
        try {
            boolean success = lock.tryLock(5, 15, TimeUnit.SECONDS);
            if (success) {
                JsonResult<UserVO> result;
                // 为什么不放在一个方法中执行，因为开启了Redis的事务，进行查询的方法不会有返回值，所以查询先在事务外执行
                // 当进行写入操作的时候则在事务内执行
                boolean enable = userService.checkUsername(ao.getUsername(), false);
                result = enable ? userService.register(ao) : JsonResult.of(10003, "用户名不可用");
                return result;
            }
        } catch (Exception e) {
            log.error("分布式锁异常:", e);
        } finally {
            lock.unlock();
        }
        return JsonResult.of(10004, "当前系统繁忙");
    }

    /**
     * 创建新用户
     *
     * @param ao 创建新用户用户模型
     * @return 创建结果
     */
    @PreAuthorize(PreAuthorizes.User.CREATE)
    @Permission("创建新用户")
    @PutMapping("/create")
    public JsonResult<UserVO> create(@RequestBody UserCreateAO ao) {
        RLock lock = distributedLock.getLock(ao.getUsername());
        try {
            boolean success = lock.tryLock(5, 15, TimeUnit.SECONDS);
            if (success) {
                JsonResult<UserVO> result;
                boolean enable = userService.checkUsername(ao.getUsername(), true);
                result = enable ? userService.create(ao) : JsonResult.of(10003, "用户名不可用");
                return result;
            }
        } catch (Exception e) {
            log.error("分布式锁异常:", e);
        } finally {
            lock.unlock();
        }
        return JsonResult.of(10004, "当前系统繁忙");
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

    /**
     * 修改用户状态
     *
     * @param id     ID
     * @param type   状态类型
     * @param status 状态
     * @return 修改结果
     */
    @PreAuthorize(PreAuthorizes.User.CHANGE_USER_STATUS)
    @Permission("修改用户状态")
    @PostMapping("/changeUserStatus/{id}/{type}/{status}")
    public JsonResult<Boolean> changeUserStatus(@PathVariable Long id, @PathVariable UserStatusType type, @PathVariable Boolean status) {
        return userService.changeUserStatus(id, type, status);
    }

    /**
     * 删除用户
     *
     * @param id ID
     * @return 删除结果
     */
    @PreAuthorize(PreAuthorizes.User.DELETE)
    @Permission("删除用户")
    @DeleteMapping("/{id}")
    public JsonResult<UserVO> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    /**
     * 根据ID查询用户
     *
     * @param id ID
     * @return 用户
     */
    @PreAuthorize(PreAuthorizes.User.FIND_BY_ID)
    @Permission("根据ID查询用户")
    @GetMapping("/findById/{id}")
    public JsonResult<UserVO> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * 根据ID更新用户
     *
     * @param ao 根据ID更新用户数据模型
     * @return 更新结果
     */
    @PreAuthorize(PreAuthorizes.User.UPDATE_BY_ID)
    @Permission("根据ID更新用户")
    @PostMapping("/updateById")
    public JsonResult<UserVO> updateById(@RequestBody UserUpdateAO ao) {
        return userService.updateById(ao);
    }
}

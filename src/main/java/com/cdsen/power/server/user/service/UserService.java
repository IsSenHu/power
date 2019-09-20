package com.cdsen.power.server.user.service;

import com.cdsen.power.core.AppProperties;
import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.security.model.LoginVO;
import com.cdsen.power.core.security.model.Token;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.ao.UserUpdateAO;
import com.cdsen.power.server.user.model.cons.UserStatusType;
import com.cdsen.power.server.user.model.query.UserQuery;
import com.cdsen.power.server.user.model.vo.UserVO;

/**
 * @author HuSen
 * create on 2019/8/29 10:29
 */
public interface UserService {

    /**
     * 登录，获取token
     *
     * @param login 登录数据模型
     * @return 登录结果
     */
    JsonResult<Token> login(LoginVO login);

    /**
     * 登出，注销当前用户
     *
     * @return 登出结果
     */
    JsonResult logout();

    /**
     * 注册用户
     *
     * @param ao 注册用户数据模型
     * @return 注册结果
     */
    JsonResult<UserVO> register(UserCreateAO ao);

    /**
     * 是否能够激活
     *
     * @param username 用户名
     * @param code     验证码
     * @return 激活结果
     */
    boolean verifyCode(String username, Integer code);

    /**
     * 激活用户
     *
     * @param username 用户名
     * @return 激活结果
     */
    JsonResult<UserVO> activate(String username);

    /**
     * 检查用户名是否可用
     *
     * @param username 用户名
     * @param inner    是否为内部调用
     * @return 是否可用
     */
    boolean checkUsername(String username, boolean inner);

    /**
     * 创建或刷新超级管理员
     *
     * @param role  超级角色名称
     * @param admin 超级管理员
     */
    void createOrRefreshSuperAdmin(String role, AppProperties.Admin admin);

    /**
     * 分页查询用户
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<UserVO>> page(IPageRequest<UserQuery> iPageRequest);

    /**
     * 修改用户状态
     *
     * @param id     ID
     * @param type   状态类型
     * @param status 状态
     * @return 修改结果
     */
    JsonResult<Boolean> changeUserStatus(Long id, UserStatusType type, Boolean status);

    /**
     * 创建新用户
     *
     * @param ao 创建新用户用户模型
     * @return 创建结果
     */
    JsonResult<UserVO> create(UserCreateAO ao);

    /**
     * 删除用户
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<UserVO> delete(Long id);

    /**
     * 根据ID查询用户
     *
     * @param id ID
     * @return 用户
     */
    JsonResult<UserVO> findById(Long id);

    /**
     * 根据ID更新用户
     *
     * @param ao 根据ID更新用户数据模型
     * @return 更新结果
     */
    JsonResult<UserVO> updateById(UserUpdateAO ao);
}

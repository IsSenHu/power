package com.cdsen.power.server.user.service.impl;

import com.cdsen.power.core.*;
import com.cdsen.power.core.jpa.FindUtils;
import com.cdsen.power.core.security.model.LoginVO;
import com.cdsen.power.core.security.model.Token;
import com.cdsen.power.core.security.model.UserInfo;
import com.cdsen.power.core.security.util.JwtUtils;
import com.cdsen.power.core.util.JsonUtils;
import com.cdsen.power.core.util.VerifyCodeUtils;
import com.cdsen.power.server.config.service.ConfigService;
import com.cdsen.power.server.email.model.ao.SimpleMailAO;
import com.cdsen.power.server.email.service.MailService;
import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.dao.po.RolePO;
import com.cdsen.power.server.user.dao.po.RolePermissionPO;
import com.cdsen.power.server.user.dao.po.UserPO;
import com.cdsen.power.server.user.dao.repository.PermissionRepository;
import com.cdsen.power.server.user.dao.repository.RolePermissionRepository;
import com.cdsen.power.server.user.dao.repository.RoleRepository;
import com.cdsen.power.server.user.dao.repository.UserRepository;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.ao.UserUpdateAO;
import com.cdsen.power.server.user.model.cons.RedisKey;
import com.cdsen.power.server.user.model.cons.UserStatusType;
import com.cdsen.power.server.user.model.query.UserQuery;
import com.cdsen.power.server.user.model.vo.UserVO;
import com.cdsen.power.server.user.service.UserService;
import com.cdsen.power.server.user.transfer.UserTransfer;
import com.cdsen.user.SecurityConfig;
import com.cdsen.user.UserLoginInfo;
import com.cdsen.user.UserManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/8/29 10:29
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String CUSTOMER = "customer";
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final UserManager userManager;
    private final ConfigService configService;
    private final SecurityConfig securityConfig;
    private final BusinessConfig businessConfig;

    public UserServiceImpl(PasswordEncoder passwordEncoder, JwtUtils jwtUtils, MailService mailService, UserRepository userRepository, StringRedisTemplate redisTemplate, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository, UserManager userManager, ConfigService configService, SecurityConfig securityConfig, BusinessConfig businessConfig) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.userManager = userManager;
        this.configService = configService;
        this.securityConfig = securityConfig;
        this.businessConfig = businessConfig;
    }

    @Override
    public JsonResult<Token> login(LoginVO login) {
        UserPO po = userRepository.findByUsername(login.getUsername());
        if (Objects.isNull(po) || !passwordEncoder.matches(login.getPassword(), po.getPassword())) {
            return JsonResult.of(10001, "用户名或密码错误");
        }

        po.setIsAccountNonLocked(true);
        userRepository.save(po);

        Integer roleId = po.getRoleId();
        List<String> viewRoles = null != roleId ?
                permissionRepository.findAllById(
                        rolePermissionRepository.findAllByRoleId(roleId).stream().map(RolePermissionPO::getPermissionId
                        ).collect(Collectors.toList())).stream().map(PermissionPO::getMark).collect(Collectors.toList())
                : Lists.newArrayList(CUSTOMER);

        UserInfo userInfo = new UserInfo(po.getUsername(), po.getIntroduction(), po.getAvatar(), viewRoles, configService.findAllByUserId(po.getId()), businessConfig.getAll());
        UserLoginInfo loginInfo = new UserLoginInfo(po.getId(), po.getUsername(), po.getPassword(), po.getIsAccountNonLocked(), po.getIsEnabled(), viewRoles);
        String token = jwtUtils.generateToken(po.getUsername());
        boolean success = userManager.saveUser(token, loginInfo);
        return success ? JsonResult.of(new Token(token, userInfo)) : JsonResult.of(10002, "同步用户登录信息失败");
    }

    @Override
    public JsonResult logout(HttpServletRequest request) {
        String token = request.getHeader(securityConfig.getHeader());
        if (StringUtils.isNotBlank(token)) {
            userManager.invalidate(token);
        }
        return JsonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<UserVO> register(UserCreateAO ao) {
        String encodePassword = passwordEncoder.encode(ao.getPassword());
        UserPO po = new UserPO();
        po.setUsername(ao.getUsername());
        po.setPassword(encodePassword);
        po.setIsAccountNonLocked(true);
        po.setIsAccountNonExpired(true);
        po.setIsEnabled(false);
        po.setIsCredentialsNonExpired(true);
        po.setEmail(ao.getEmail());
        po.setCreateTime(LocalDateTime.now());
        userRepository.save(po);

        int code = VerifyCodeUtils.generateCode();
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.setIfAbsent(RedisKey.READY_VERIFY_CODE.concat(ao.getUsername()), String.valueOf(code), Duration.ofMinutes(30));

        SimpleMailAO mail = new SimpleMailAO();
        mail.setTo(new String[]{ao.getEmail()});
        mail.setSentDate(new Date());
        mail.setSubject("帐号激活");
        // 邮箱发激活连接 用户点击连接跳转激活
        mail.setText("激活连接:" + code);
        JsonResult<SimpleMailAO> sendResult = mailService.send(mail);
        log.info("邮件发送结果:{}", JsonUtils.toJsonString(sendResult));
        return JsonResult.of(UserTransfer.PO_TO_VO.apply(po));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<UserVO> create(UserCreateAO ao) {
        UserPO byUsername = userRepository.findByUsername(ao.getUsername());
        String encodePassword = passwordEncoder.encode(ao.getPassword());
        UserPO po;
        if (byUsername == null) {
            po = UserTransfer.AO_TO_PO.apply(ao);
            po.setPassword(encodePassword);
            po.setCreateTime(LocalDateTime.now());
            po.setIsDelete(false);
        } else {
            byUsername.setEmail(ao.getEmail());
            byUsername.setIntroduction(ao.getIntroduction());
            byUsername.setPassword(encodePassword);
            byUsername.setIsEnabled(ao.getIsEnabled());
            byUsername.setUsername(ao.getUsername());
            byUsername.setIsDelete(false);
            byUsername.setCreateTime(LocalDateTime.now());
            byUsername.setIsAccountNonLocked(true);
            byUsername.setRoleId(null);
            byUsername.setIsCredentialsNonExpired(true);
            byUsername.setIsAccountNonExpired(true);
            po = byUsername;
        }
        userRepository.save(po);
        return JsonResult.of(UserTransfer.PO_TO_VO.apply(po));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<UserVO> delete(Long id) {
        return userRepository.findById(id)
                .map(po -> {
                    po.setIsDelete(true);
                    userRepository.save(po);
                    return JsonResult.of(UserTransfer.PO_TO_VO.apply(po));
                }).orElseGet(() -> JsonResult.of(10005, "该用户不存在"));
    }

    @Override
    public JsonResult<UserVO> findById(Long id) {
        return userRepository.findById(id)
                .map(po -> JsonResult.of(UserTransfer.PO_TO_VO.apply(po)))
                .orElseGet(() -> JsonResult.of(10005, "该用户不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<UserVO> updateById(UserUpdateAO ao) {
        return userRepository.findById(ao.getId())
                .map(po -> {
                    po.setIntroduction(ao.getIntroduction());
                    po.setIsEnabled(ao.getIsEnabled());
                    po.setEmail(ao.getEmail());
                    userRepository.save(po);
                    return JsonResult.of(UserTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(10005, "该用户不存在"));
    }

    @Override
    public boolean verifyCode(String username, Integer code) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String codeStr = value.get(RedisKey.READY_VERIFY_CODE.concat(username));
        return StringUtils.equals(codeStr, code.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<UserVO> activate(String username) {
        UserVO vo = null;
        UserPO po = userRepository.findByUsername(username);
        if (po != null) {
            po.setIsEnabled(true);
            userRepository.save(po);
            vo = UserTransfer.PO_TO_VO.apply(po);
        }
        redisTemplate.delete(RedisKey.READY_VERIFY_CODE.concat(username));
        redisTemplate.delete(RedisKey.READY_REGISTER_USERNAME.concat(username));
        return JsonResult.of(vo);
    }

    @Override
    public boolean checkUsername(String username, boolean inner) {
        long count = userRepository.countByUsernameAndIsDelete(username, false);
        if (count > 0) {
            return false;
        }

        ValueOperations<String, String> value = redisTemplate.opsForValue();
        // 由于redis是单线程的，这样做可以保证相同的用户名只能被set一次，也就是同一个用户名只注册一次
        if (inner) {
            String judge = value.get(username);
            return !StringUtils.isNotBlank(judge);
        } else {
            Boolean ifAbsent = value.setIfAbsent(RedisKey.READY_REGISTER_USERNAME.concat(username), username, Duration.ofMinutes(10));
            return Objects.isNull(ifAbsent) ? false : ifAbsent;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrRefreshSuperAdmin(String role, AppProperties.Admin admin) {
        RolePO rolePo = roleRepository.findByName(role);
        UserPO po = userRepository.findByUsername(admin.getDefaultUsername());
        if (po == null) {
            po = new UserPO();
            po.setUsername(admin.getDefaultUsername());
            po.setCreateTime(LocalDateTime.now());
        }
        po.setPassword(passwordEncoder.encode(admin.getDefaultPassword()));
        po.setEmail(admin.getDefaultEmail());
        po.setIntroduction(admin.getIntroduction());
        po.setAvatar(admin.getAvatar());
        po.setIsEnabled(true);
        po.setIsAccountNonExpired(true);
        po.setIsAccountNonLocked(true);
        po.setIsCredentialsNonExpired(true);
        po.setRoleId(rolePo.getId());
        po.setIsDelete(false);
        userRepository.save(po);
    }

    @Override
    public JsonResult<PageResult<UserVO>> page(IPageRequest<UserQuery> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        Page<UserPO> page = userRepository.findAll(SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("isDelete").as(Boolean.class), false));
            UserQuery customParams = iPageRequest.getCustomParams();
            if (null != customParams) {
                Long userId = customParams.getUserId();
                if (null != userId) {
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), userId));
                }
                String username = customParams.getUsername();
                if (StringUtils.isNotBlank(username)) {
                    predicates.add(criteriaBuilder.like(root.get("username").as(String.class), FindUtils.allMatch(username)));
                }
                String email = customParams.getEmail();
                if (StringUtils.isNotBlank(email)) {
                    predicates.add(criteriaBuilder.like(root.get("email").as(String.class), FindUtils.allMatch(email)));
                }
            }
        }), pageable);
        return JsonResult.of(PageResult.of(page.getTotalElements(), UserTransfer.PO_TO_VO, page.getContent()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Boolean> changeUserStatus(Long id, String token, UserStatusType type, Boolean status) {
        return userRepository.findById(id)
                .map(po -> {
                    switch (type) {
                        case ENABLED:
                            po.setIsEnabled(status);
                            break;
                        case ACCOUNT_NON_LOCKED: {
                            po.setIsAccountNonLocked(status);
                            userManager.changeLockState(token, status);
                            break;
                        }
                        default:
                    }
                    userRepository.save(po);
                    return JsonResult.of(status);
                }).orElseGet(() -> JsonResult.of(10002, "该用户不存在"));
    }
}

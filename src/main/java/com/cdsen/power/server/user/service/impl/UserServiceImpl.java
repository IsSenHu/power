package com.cdsen.power.server.user.service.impl;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.security.model.LoginVO;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.model.Token;
import com.cdsen.power.core.security.model.UserInfo;
import com.cdsen.power.core.security.session.SessionManage;
import com.cdsen.power.core.security.util.JwtUtils;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.core.util.VerifyCodeUtils;
import com.cdsen.power.server.email.model.vo.SimpleMailAO;
import com.cdsen.power.server.email.service.MailService;
import com.cdsen.power.server.user.dao.po.UserPO;
import com.cdsen.power.server.user.dao.repository.UserRepository;
import com.cdsen.power.server.user.model.ao.UserCreateAO;
import com.cdsen.power.server.user.model.cons.RedisKey;
import com.cdsen.power.server.user.model.vo.UserVO;
import com.cdsen.power.server.user.service.UserService;
import com.cdsen.power.server.user.transfer.UserTransfer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author HuSen
 * create on 2019/8/29 10:29
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final SessionManage sessionManage;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public UserServiceImpl(PasswordEncoder passwordEncoder, JwtUtils jwtUtils, @Qualifier("redisSessionManage") SessionManage sessionManage, MailService mailService, UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.sessionManage = sessionManage;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public JsonResult<Token> login(LoginVO login) {
        UserPO po = userRepository.findByUsername(login.getUsername());
        if (Objects.isNull(po) || !passwordEncoder.matches(login.getPassword(), po.getPassword())) {
            return JsonResult.of(10001, "用户名或密码错误");
        }

        // TODO 角色和权限
        UserInfo userInfo = new UserInfo(po.getUsername(), po.getIntroduction(), po.getAvatar(), Lists.newArrayList("admin"));
        Session session = new Session(userInfo, login.getPassword());
        session.setUserId(po.getId());
        String token = jwtUtils.generateToken(session);
        sessionManage.save(login.getUsername(), session);
        return JsonResult.of(new Token(token, userInfo));
    }

    @Override
    public JsonResult logout() {
        String currentUser = SecurityUtils.currentUser();
        if (StringUtils.isNotBlank(currentUser)) {
            sessionManage.invalidate(currentUser);
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
        mailService.send(mail);
        return JsonResult.success();
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
    public boolean checkUsername(String username) {
        long count = userRepository.countByUsername(username);
        if (count > 0) {
            return false;
        }

        ValueOperations<String, String> value = redisTemplate.opsForValue();
        Boolean ifAbsent = value.setIfAbsent(RedisKey.READY_REGISTER_USERNAME.concat(username), username, Duration.ofMinutes(30));
        return Objects.isNull(ifAbsent) ? false : ifAbsent;
    }
}

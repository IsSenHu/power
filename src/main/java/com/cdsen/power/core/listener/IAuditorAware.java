package com.cdsen.power.core.listener;

import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author HuSen
 * create on 2019/9/16 16:12
 */
public class IAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Session session = SecurityUtils.currentSession();
        return Optional.ofNullable(session.getUserId());
    }
}

package com.cdsen.power.core.jpa;

import com.cdsen.power.core.security.model.UserDetailsImpl;
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
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        return Optional.ofNullable(userDetails.getUserId());
    }
}

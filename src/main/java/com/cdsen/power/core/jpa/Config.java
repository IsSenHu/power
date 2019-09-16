package com.cdsen.power.core.jpa;

import com.cdsen.power.core.listener.IAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author HuSen
 * create on 2019/9/16 16:18
 */
@Configuration
@EnableJpaAuditing
public class Config {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new IAuditorAware();
    }
}

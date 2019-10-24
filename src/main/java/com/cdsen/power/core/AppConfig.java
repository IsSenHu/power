package com.cdsen.power.core;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdsen.interfaces.config.service.BusinessSettingApiService;
import com.cdsen.power.core.oss.OssConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuSen
 * create on 2019/10/24 11:49
 */
@Configuration
@EnableApolloConfig
public class AppConfig {

    @Reference(check = false)
    private BusinessSettingApiService businessSettingApiService;

    @Bean
    public OssConfig ossConfig() {
        return new OssConfig();
    }

    @Bean
    public BusinessConfig businessConfig() {
        return new BusinessConfig(businessSettingApiService);
    }
}

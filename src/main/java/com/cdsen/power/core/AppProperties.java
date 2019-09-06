package com.cdsen.power.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author HuSen
 * create on 2019/8/28 17:06
 */
@Data
@ConfigurationProperties(prefix = "com.cdsen")
@Component
public class AppProperties {

    private String secret;

    private Long expiration;

    private Long maxSessionInCache;

    private String header;
}

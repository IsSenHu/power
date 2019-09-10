package com.cdsen.power.server.user.service;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author HuSen
 * create on 2019/9/10 10:35
 */
public interface PermissionService {

    void createOrRefresh(ConfigurableApplicationContext context);
}

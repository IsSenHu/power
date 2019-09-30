package com.cdsen.power.core.security.session;

import com.cdsen.power.core.security.model.Session;

/**
 * @author HuSen
 * create on 2019/9/6 14:46
 */
public interface SessionManage {

    Session getSession(String username);

    void invalidate(String username);

    void save(String username, Session session);

    void changeLockState(String username, boolean isAccountNonLocked);
}

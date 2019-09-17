package com.cdsen.power.server.user.model.cons;

/**
 * @author HuSen
 * create on 2019/9/9 15:51
 */
public class PreAuthorizes {

    public static class Role {
        public static final String CREATE = "hasAuthority('role:create')";
        public static final String UPDATE = "hasAuthority('role:update')";
        public static final String DELETE = "hasAuthority('role:delete')";
        public static final String FIND_BY_ID = "hasAuthority('role:findById')";
        public static final String PAGE = "hasAuthority('role:page')";
    }

    public static class User {
        public static final String PAGE = "hasAuthority('user:page')";
    }

    public static class Permission {
        public static final String PAGE = "hasAuthority('permission:page')";
    }
}

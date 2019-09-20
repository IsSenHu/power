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
        public static final String QUERY = "hasAuthority('role:query')";
    }

    public static class User {
        public static final String CREATE = "hasAuthority('user:create')";
        public static final String QUERY = "hasAuthority('user:query')";
        public static final String CHANGE_USER_STATUS = "hasAuthority('user:changeUserStatus')";
        public static final String DELETE = "hasAuthority('user:delete')";
        public static final String FIND_BY_ID = "hasAuthority('user:findById')";
        public static final String UPDATE_BY_ID = "hasAuthority('user:updateById')";
    }

    public static class Permission {
        public static final String QUERY = "hasAuthority('permission:query')";
        public static final String PERMISSION_TREE_VIEW = "hasAuthority('permission:permissionTreeView')";
    }
}

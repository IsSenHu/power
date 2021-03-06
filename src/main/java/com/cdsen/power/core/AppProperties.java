package com.cdsen.power.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

    private Admin admin;

    private AdminRole adminRole;

    private Redisson redisson;


    @Getter
    @Setter
    public static class Admin {
        private String defaultUsername;
        private String defaultPassword;
        private String defaultEmail;
        private String introduction;
        private String avatar;
    }

    @Getter
    @Setter
    public static class AdminRole {
        private String name;
        private String description;
        private String exclusivePermission;
        private String exclusivePermissionName;
    }

    @Getter
    @Setter
    public static class Redisson {

        private Sentinel sentinel;
        private Single single;

        @Getter
        @Setter
        public static class Sentinel {
            private String address;
            private String masterName;
            private String password;
            private Integer database;
        }

        @Getter
        @Setter
        public static class Single {
            private String address;
            private String password;
            private Integer database;
        }
    }
}

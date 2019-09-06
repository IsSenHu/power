package com.cdsen.power.server.user.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/5 10:56
 */
@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class UserPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_account_non_locked", nullable = false, length = 1)
    private Boolean isAccountNonLocked;

    @Column(name = "is_account_non_expired", nullable = false, length = 1)
    private Boolean isAccountNonExpired;

    @Column(name = "is_enabled", nullable = false, length = 1)
    private Boolean isEnabled;

    @Column(name = "is_credentials_non_expired", nullable = false, length = 1)
    private Boolean isCredentialsNonExpired;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}

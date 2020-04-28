package com.cdsen.power.core.jpa;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/16 16:10
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePO<ID extends Serializable, UID extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @CreatedBy
    @Column(name = "create_user_id")
    private UID createUserId;

    @LastModifiedBy
    @Column(name = "last_modified_user_id")
    private UID lastModifiedUserId;

    @LastModifiedDate
    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;
}

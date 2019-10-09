package com.cdsen.power.server.oss.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/10/9 10:42
 */
@Getter
@Setter
@Entity
@Table(name = "tb_image")
public class ImagePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;
}

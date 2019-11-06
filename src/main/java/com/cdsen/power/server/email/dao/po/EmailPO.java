package com.cdsen.power.server.email.dao.po;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuSen
 * create on 2019/11/4 14:04
 */
@Getter
@Setter
@Entity
@Table(name = "tb_email")
public class EmailPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "subject_", nullable = false)
    private String subject;

    @Column(name = "from_", nullable = false)
    private String from;

    @Column(name = "to_", nullable = false)
    private String to;

    @Column(name = "cc", length = 5000)
    private String cc;

    @Column(name = "bcc")
    private String bcc;

    @Column(name = "sent_date", nullable = false)
    private Date sentDate;

    @Lob
    @Column(name = "content", columnDefinition="Mediumblob")
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;

    @Column(name = "reply_sign", nullable = false, length = 1)
    private Boolean replySign;

    @Column(name = "message_id", nullable = false, unique = true)
    private String messageId;

    @Column(name = "is_new", nullable = false, length = 1)
    private Boolean isNew;

    @Column(name = "attachments")
    private String attachments;

    @Column(name = "host_", nullable = false)
    private String host;

    @Column(name = "protocol", nullable = false)
    private String protocol;
}

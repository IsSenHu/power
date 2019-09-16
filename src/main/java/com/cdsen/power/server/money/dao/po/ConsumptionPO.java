package com.cdsen.power.server.money.dao.po;

import com.cdsen.power.server.money.model.cons.CurrencyType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/2 17:07
 */
@Getter
@Setter
@Entity
@Table(name = "tb_consumption")
@NamedEntityGraph(name = "ConsumptionPO.items", attributeNodes = {
        @NamedAttributeNode("items")
})
public class ConsumptionPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 消费时间
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    /**
     * 货币单位
     */
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    /**
     * 消费条目
     */
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "consumption")
    private List<ConsumptionItemPO> items;
}

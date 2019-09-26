package com.cdsen.power.server.physical.exercise.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/26 10:06
 */
@NamedEntityGraph(name = "PhysicalExercisePO.items", attributeNodes = {
        @NamedAttributeNode("items")
})
@Getter
@Setter
@Entity
@Table(name = "tb_physical_exercise")
public class PhysicalExercisePO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 日期
     */
    @Column(name = "time", nullable = false)
    private LocalDate time;

    /**
     * 这一天的锻炼条目
     */
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "physicalExercise")
    private List<ExerciseItemPO> items;
}

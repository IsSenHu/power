package com.cdsen.power.core;

import com.cdsen.power.core.fuction.ThreeConsumer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/12 9:57
 */
public class SpecificationFactory {

    public static <T> Specification<T> produce(ThreeConsumer<List<Predicate>, Root<T>, CriteriaBuilder> consumer) {
        return (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            consumer.accept(predicates, root, criteriaBuilder);
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}

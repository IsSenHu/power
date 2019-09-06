package com.cdsen.power.core.convert;

import javax.persistence.AttributeConverter;

/**
 * @author HuSen
 * create on 2019/9/5 9:54
 */
public abstract class AbstractEnumConverter<E extends Enum<E> & Convertible> implements AttributeConverter<E, String> {

    protected String toDatabaseColumn(E attr) {
        return (attr == null)
                ? null
                : attr.token();
    }

    protected E toEntityAttribute(Class<E> cls, String dbCol) {
        return (dbCol == null)
                ? null
                : Convertible.forToken(cls, dbCol);
    }
}

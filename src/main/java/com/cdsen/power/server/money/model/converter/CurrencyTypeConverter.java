package com.cdsen.power.server.money.model.converter;

import com.cdsen.power.core.convert.AbstractEnumConverter;
import com.cdsen.power.server.money.model.cons.CurrencyType;

/**
 * @author HuSen
 * create on 2019/9/5 9:58
 */
public class CurrencyTypeConverter extends AbstractEnumConverter<CurrencyType> {

    @Override
    public String convertToDatabaseColumn(CurrencyType currencyType) {
        return this.toDatabaseColumn(currencyType);
    }

    @Override
    public CurrencyType convertToEntityAttribute(String s) {
        return this.toEntityAttribute(CurrencyType.class, s);
    }
}

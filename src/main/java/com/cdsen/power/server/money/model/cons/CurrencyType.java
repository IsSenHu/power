package com.cdsen.power.server.money.model.cons;

import com.cdsen.power.core.convert.Convertible;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @author HuSen
 * create on 2019/9/5 9:42
 */
@Getter
public enum CurrencyType implements Convertible {
    // 人民币
    REN_MIN_BI(Locale.CHINA);

    CurrencyType(Locale locale) {
        this.format = NumberFormat.getCurrencyInstance(locale);
        this.currency = Currency.getInstance(locale);
    }

    private NumberFormat format;
    private Currency currency;

    @Override
    public String token() {
        return this.name();
    }
}

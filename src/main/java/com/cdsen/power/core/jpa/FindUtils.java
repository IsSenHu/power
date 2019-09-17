package com.cdsen.power.core.jpa;

/**
 * @author HuSen
 * create on 2019/9/17 9:59
 */
public final class FindUtils {

    private static final String PERCENT_SIGN = "%";

    public static String leftMatch(String origin) {
        return PERCENT_SIGN + origin;
    }

    public static String rightMatch(String origin) {
        return origin + PERCENT_SIGN;
    }

    public static String allMatch(String origin) {
        return PERCENT_SIGN + origin + PERCENT_SIGN;
    }
}

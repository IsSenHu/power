package com.cdsen.power.core.util;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author HuSen
 * create on 2019/9/5 16:37
 */
public class VerifyCodeUtils {

    private static final int START = 100000;
    private static final int END = 999999;

    public static int generateCode() {
        return RandomUtils.nextInt(START, END);
    }
}

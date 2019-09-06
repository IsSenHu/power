package com.cdsen.power.core.convert;

import java.util.stream.Stream;

/**
 * @author HuSen
 * create on 2019/9/5 9:50
 */
public interface Convertible {

    String token();

    static <E extends Enum<E> & Convertible> E forToken(Class<E> cls, String token) {
        final String t = token.trim().toUpperCase();
        return Stream.of(cls.getEnumConstants())
                .filter(e -> e.token().equals(t))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown token '" + token + "' for enum " + cls.getName()));
    }
}

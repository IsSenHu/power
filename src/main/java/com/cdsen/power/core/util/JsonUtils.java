package com.cdsen.power.core.util;

import com.alibaba.fastjson.JSON;

/**
 * @author HuSen
 * create on 2019/9/6 15:08
 */
public class JsonUtils {

    public static <T> T parseObj(String rawStr, Class<T> tClass) {
        try {
            return JSON.parseObject(rawStr, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJsonString(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }
}

package com.cdsen.power.server.user.model.cons;

/**
 * @author HuSen
 * create on 2019/9/6 10:48
 */
public class RedisKey {
    private static final String SET = ":set";
    private static final String HASH = ":hash";

    public static final String READY_REGISTER_USERNAME = "readyRegisterUsername:";

    public static final String READY_VERIFY_CODE = "readyVerifyCode:";
}

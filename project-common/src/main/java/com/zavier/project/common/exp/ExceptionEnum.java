package com.zavier.project.common.exp;

import lombok.Getter;

public enum ExceptionEnum {
    /**
     * 用户名称已存在
     */
    USER_NAME_EXISTED("100", "用户名称已存在"),

    USER_NAME_PASSWORD_ERROR("101", "用户名或密码错误"),

    USER_NAME_NOT_EXIST("102", "用户名不存在"),

    USER_ACCOUNT_LOCKED("103", "账号已锁定"),

    LOGIN_ERROR("104", "登录失败"),

    UNAUTHORIZED("105", "未认证"),

    TOKEN_NOT_EXIST("106", "token不存在"),

    TOKEN_INVALID("107", "token无效"),

    TOKEN_EXPIRED("108", "token已过期"),

    ;

    @Getter
    private String id;
    @Getter
    private String message;

    ExceptionEnum(String id, String message) {
        this.id = id;
        this.message = message;
    }
}

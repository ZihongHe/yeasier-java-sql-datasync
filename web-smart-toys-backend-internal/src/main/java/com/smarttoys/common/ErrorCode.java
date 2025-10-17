package com.smarttoys.common;

/**
 * 自定义错误码
 *
 
 */
public enum ErrorCode {

    SUCCESS(200, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    USER_ALREADY_EXIST(100001, "用户已存在"),
    USER_NOT_EXIST(100002, "用户不存在"),
    SANDBOX_ALREADY_EXIST(100003, "沙盒已存在"),
    SANDBOX_NOT_EXIST(100004, "沙盒不存在"),
    AGENT_ALREADY_EXIST(100005, "智能体已存在"),
    AGENT_NOT_EXIST(100006, "智能体不存在"),
    ACHIEVEMENT_ALREADY_EXIST(100007, "成就已存在"),
    ACHIEVEMENT_NOT_EXIST(100008, "成就不存在"),

    // 用户沙盒相关错误码 (根据你的描述补充)
    USER_SANDBOX_ALREADY_EXIST(100009, "用户沙盒已存在"),
    USER_SANDBOX_NOT_EXIST(100010, "用户沙盒不存在"),

    // 用户智能体相关错误码 (根据你的描述补充)
    USER_AGENT_ALREADY_EXIST(100011, "用户智能体已存在"),
    USER_AGENT_NOT_EXIST(100012, "用户智能体不存在"),

    // 用户成就相关错误码 (根据你的描述补充)
    USER_ACHIEVEMENT_ALREADY_EXIST(100013, "用户成就已存在"),
    USER_ACHIEVEMENT_NOT_EXIST(100014, "用户成就不存在");


    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

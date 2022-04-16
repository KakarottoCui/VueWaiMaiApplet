package com.kbz1121.CarStore.system.ResponseFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "成功"),
    /**
     * 资源不存在的状态码
     */
    RESOURCES_NOT_EXIST(404, "资源不存在"),
    /**
     * 所有无法识别的异常默认的返回状态码
     */
    SERVICE_ERROR(500, "失败"),

    SERVICE_VALID_ERROR(501, "校验失败"),

    SERVICE_ROLE_ERROR(401, "当前用户无权限访问"),

    SERVICE_LOGIN_ERROR(401, "当前用户未登录"),

    SERVICE_LOGIN_SUCCESS(100, "登录成功");

    private int code;
    private String msg;


}

package com.elyar.myProject.enums;

/**
 * @author elyar
 * @date 2020/11/25 14:24
 * @description
 */
public enum ReturnCodeEnum {
    SUCCESS("00", "成功"),

    FAILURE("01", "失败"),

    ERROR_PARAMS("02", "请求参数不合法"),

    ERROR_SYSTEM("99", "系统异常");


    private String code;
    private String desc;

    private ReturnCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

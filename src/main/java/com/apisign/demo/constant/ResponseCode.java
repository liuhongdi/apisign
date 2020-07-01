package com.apisign.demo.constant;

/**
 * liuhongdi
 *报错提示信息的枚举
*/

 public enum ResponseCode {

    // 系统模块
    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败"),

    //sign error
    SIGN_NO_APPID(10001, "appId不能为空"),
    SIGN_NO_TIMESTAMP(10002, "timestamp不能为空"),
    SIGN_NO_SIGN(10003, "sign不能为空"),
    SIGN_NO_NONCE(10004, "nonce不能为空"),
    SIGN_TIMESTAMP_INVALID(10005, "timestamp无效"),
    SIGN_DUPLICATION(10006, "重复的请求"),
    SIGN_VERIFY_FAIL(10007, "sign签名校验失败"),
    ;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    private String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}

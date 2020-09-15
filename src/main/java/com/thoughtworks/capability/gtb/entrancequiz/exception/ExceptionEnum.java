package com.thoughtworks.capability.gtb.entrancequiz.exception;

public enum  ExceptionEnum {

    USER_NOT_EXIST(10001, "user is not exist"),
    ADD_USER_EXCEPTION(10002, "add user failed");

    private Integer code;
    private String errorMessage;

    ExceptionEnum(Integer code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public Integer getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

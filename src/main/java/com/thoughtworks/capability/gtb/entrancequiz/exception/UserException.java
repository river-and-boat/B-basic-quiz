package com.thoughtworks.capability.gtb.entrancequiz.exception;

public class UserException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public UserException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}

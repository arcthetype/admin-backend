package com.zavier.project.common.exp;

public class CommonException extends RuntimeException {

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
    }
}

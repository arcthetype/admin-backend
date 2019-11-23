package com.zavier.project.web.res;

import com.zavier.project.common.exp.ExceptionEnum;
import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private String code;
    private T data;
    private String msg;

    public static <T> Result<T> wrapSuccessResult(T data) {
        final Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> wrapErrorResult(String msg) {
        final Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> wrapErrorResult(ExceptionEnum exceptionEnum) {
        final Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(exceptionEnum.getId());
        result.setMsg(exceptionEnum.getMessage());
        return result;
    }
}

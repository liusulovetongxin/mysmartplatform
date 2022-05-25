package com.qf.smartplatform.exceptions;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.exceptions
 * @Description:
 * @Date 2022/5/25 17:34
 */

public class BaseException extends RuntimeException {
    private int code;

    public BaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

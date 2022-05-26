package com.qf.smartplatform.exceptions;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.exceptions
 * @Description:
 * @Date 2022/5/26 10:41
 */

public class UpdateException extends BaseException{
    public UpdateException(String message, int code) {
        super(message, code);
    }
}

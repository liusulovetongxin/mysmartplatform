package com.qf.smartplatform.exceptions;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.exceptions
 * @Description:
 * @Date 2022/5/25 17:35
 */

public class DeleteDataException extends BaseException{
    public DeleteDataException(String message, int code) {
        super(message, code);
    }
}

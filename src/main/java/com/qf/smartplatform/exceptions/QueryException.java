package com.qf.smartplatform.exceptions;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.exceptions
 * @Description:
 * @Date 2022/5/26 10:40
 */

public class QueryException extends BaseException{
    public QueryException(String message, int code) {
        super(message, code);
    }
}

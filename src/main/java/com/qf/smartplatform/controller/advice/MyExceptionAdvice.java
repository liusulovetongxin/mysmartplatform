package com.qf.smartplatform.controller.advice;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.exceptions.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller.advice
 * @Description:
 * @Date 2022/5/26 11:12
 */
@RestControllerAdvice
public class MyExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public R processBaseException(BaseException exception){
        return R.setResult(exception.getCode(),exception.getMessage(),null);
    }
    @ExceptionHandler(Exception.class)
    public R processException(Exception exception){
        exception.printStackTrace();
        return R.setError();
    }
}

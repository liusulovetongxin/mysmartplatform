package com.qf.smartplatform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qf.smartplatform.constans.ResultCode;
import lombok.Data;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.dto
 * @Description:
 * @Date 2022/5/25 17:16
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class R {
    private int code;
    private String msg;
    private Object result;

    public static R setOk(){
        return setOk(null);
    }
    public static R setOk(Object result){
        return setResult(ResultCode.SUCCESS, "成功", result);
    }
    public static R setError(){
        return setError(null);
    }
    public static R setError(Object result){
        return setResult(ResultCode.ERROR, "失败", result);
    }


    public static R setResult(int code,String msg,Object result){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setResult(result);
        return r;
    }
}

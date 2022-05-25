package com.qf.smartplatform.dto;

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
public class R {
    private int code;
    private String msg;
    private Object data;

    public R setOk(){
        return setOk(null);
    }
    public R setOk(Object data){
        return setResult(ResultCode.SUCCESS, "成功", data);
    }
    public R setError(){
        return setError(null);
    }
    public R setError(Object data){
        return setResult(ResultCode.ERROR, "失败", data);
    }


    public R setResult(int code,String msg,Object data){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}

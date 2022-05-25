package com.qf.smartplatform.pojo;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.pojo
 * @Description:
 * @Date 2022/5/25 17:27
 */

public interface CheckNull {
    default boolean isEmpty(CheckType type){
        return false;
    }
}

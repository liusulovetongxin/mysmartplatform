package com.qf.smartplatform.utils;

import com.qf.smartplatform.pojo.SysUserInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.utils
 * @Description:
 * @Date 2022/5/27 11:54
 */

public class SecurityUtils {
    public static SysUserInfo getSysUserInfo(boolean test){
        if (test){
            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setUsername("ceshiyonghu");
            sysUserInfo.setUId(-1L);
            return sysUserInfo;
        }
        return (SysUserInfo) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("user");
    }
}

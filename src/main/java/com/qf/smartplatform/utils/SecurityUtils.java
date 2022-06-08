package com.qf.smartplatform.utils;

import com.qf.smartplatform.pojo.MyBaseUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.utils
 * @Description:
 * @Date 2022/5/27 11:54
 */

public class SecurityUtils {
    public static MyBaseUser getSysUserInfo(boolean test){
        if (test){
            MyBaseUser baseUser = new MyBaseUser("ceshizhanghao", "", null);
            baseUser.setUserId(-1L);
            return baseUser;
        }
        MyBaseUser principal = (MyBaseUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
}

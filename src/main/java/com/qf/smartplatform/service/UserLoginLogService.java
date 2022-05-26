package com.qf.smartplatform.service;

import com.qf.smartplatform.pojo.SysLogininfor;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/26 17:13
 */


public interface UserLoginLogService {
    void addLoginLog(SysLogininfor sysLogininfor);
}

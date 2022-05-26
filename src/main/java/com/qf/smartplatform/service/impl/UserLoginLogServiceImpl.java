package com.qf.smartplatform.service.impl;

import com.qf.smartplatform.mapper.UserLoginLogMapper;
import com.qf.smartplatform.pojo.SysLogininfor;
import com.qf.smartplatform.service.UserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/26 17:14
 */
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {
    private UserLoginLogMapper userLoginLogMapper;

    @Autowired
    public void setUserLoginLogMapper(UserLoginLogMapper userLoginLogMapper) {
        this.userLoginLogMapper = userLoginLogMapper;
    }

    @Override
    public void addLoginLog(SysLogininfor sysLogininfor) {
        // 内部生成的数据，所以基本上不存在数据为空或者数据不正确的情况，所以不是很需要加判断
        // 直接调用mapper，没有，就创建
        userLoginLogMapper.addUserLoginLog(sysLogininfor);
    }
}

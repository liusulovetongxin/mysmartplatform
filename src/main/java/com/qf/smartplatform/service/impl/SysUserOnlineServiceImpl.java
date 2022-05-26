package com.qf.smartplatform.service.impl;

import com.qf.smartplatform.mapper.SysUserOnlineMapper;
import com.qf.smartplatform.pojo.SysUserOnline;
import com.qf.smartplatform.service.SysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/26 20:31
 */
@Service
public class SysUserOnlineServiceImpl implements SysUserOnlineService {
    private SysUserOnlineMapper sysUserOnlineMapper;

    @Autowired
    public void setSysUserOnlineMapper(SysUserOnlineMapper sysUserOnlineMapper) {
        this.sysUserOnlineMapper = sysUserOnlineMapper;
    }

    @Override
    public void addOrUpdateOnline(SysUserOnline sysUserOnline) {
        String sessionId = sysUserOnlineMapper.findOnlineDataByUsername(sysUserOnline.getLoginName());
        if (sessionId != null) {
            sysUserOnlineMapper.deleteByUserName(sysUserOnline.getLoginName());
        }
        sysUserOnlineMapper.addOnlineData(sysUserOnline);
    }
}

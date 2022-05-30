package com.qf.smartplatform.service;

import com.qf.smartplatform.dto.SysDeviceDto;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/30 11:38
 */

public interface SysDeviceService {
    void addDevice(SysDeviceDto sysDeviceDto);

    int update2Sell(String deviceId);

    int bindDevice(String deviceId,Long sceneId);
}

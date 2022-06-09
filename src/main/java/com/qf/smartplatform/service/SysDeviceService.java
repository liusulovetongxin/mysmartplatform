package com.qf.smartplatform.service;

import com.qf.smartplatform.dto.SysDeviceDto;
import com.qf.smartplatform.pojo.SysDevice;

import java.util.List;

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

    List<SysDevice> findAllDevices(int page, int limit);

    void sendControl(String deviceId, String command);

    void updateDeviceOnlineOffline(String deviceId, String ip);

    void updateDevice(SysDeviceDto sysDeviceDto);
}

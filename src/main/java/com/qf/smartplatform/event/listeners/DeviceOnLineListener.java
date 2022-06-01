package com.qf.smartplatform.event.listeners;

import com.qf.smartplatform.event.DeviceOnLineEvent;
import com.qf.smartplatform.service.SysDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.event.listeners
 * @Description:
 * @Date 2022/6/1 16:56
 */
@Component
public class DeviceOnLineListener {
    private SysDeviceService sysDeviceService;

    @Autowired
    public void setSysDeviceService(SysDeviceService sysDeviceService) {
        this.sysDeviceService = sysDeviceService;
    }

    @EventListener
    @Async
    public void onEvent(DeviceOnLineEvent event){
        sysDeviceService.updateDeviceOnlineOffline(event.getDeviceId(),event.getIp());
    }
}

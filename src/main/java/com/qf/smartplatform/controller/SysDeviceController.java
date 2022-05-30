package com.qf.smartplatform.controller;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.dto.SysDeviceDto;
import com.qf.smartplatform.service.SysDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/30 11:51
 */
@RestController
@RequestMapping("/devices")
public class SysDeviceController {
    private SysDeviceService sysDeviceService;

    @Autowired
    public void setSysDeviceService(SysDeviceService sysDeviceService) {
        this.sysDeviceService = sysDeviceService;
    }

    @PostMapping("/adddevice")
    public R addDevice(@RequestBody SysDeviceDto sysDeviceDto){
        sysDeviceService.addDevice(sysDeviceDto);
        return R.setOk();
    }
    @PostMapping("/device/sell/{deviceId}")
    public R update2Sell(@PathVariable String deviceId) {
        sysDeviceService.update2Sell(deviceId);
        return R.setOk();
    }
    @PostMapping("/device/bind/{deviceId}/{sceneId}")
    public R bindDevice(@PathVariable String deviceId,@PathVariable  Long sceneId) {
        sysDeviceService.bindDevice(deviceId,sceneId);
        return R.setOk();
    }
}

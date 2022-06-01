package com.qf.smartplatform.controller;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.dto.SysDeviceDto;
import com.qf.smartplatform.pojo.SysDevice;
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

    @GetMapping("/devices")
    public R devices(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int limit){
        PageInfo<SysDevice> pageInfo = new PageInfo<>(sysDeviceService.findAllDevices(page, limit));
        return R.setOk(pageInfo);
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

    @PostMapping("/command/{deviceId}/{command}")
    public R sendControl(@PathVariable String deviceId,@PathVariable String command){
        sysDeviceService.sendControl(deviceId,command);
        return R.setOk();
    }
}

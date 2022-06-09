package com.qf.smartplatform.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.qf.smartplatform.Cache.CategoryCache;
import com.qf.smartplatform.Cache.SysSceneCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.dto.SysDeviceDto;
import com.qf.smartplatform.event.DevicePowerCommandEvent;
import com.qf.smartplatform.event.DeviceRGBCommandEvent;
import com.qf.smartplatform.exceptions.AddException;
import com.qf.smartplatform.exceptions.QueryException;
import com.qf.smartplatform.exceptions.UpdateException;
import com.qf.smartplatform.mapper.SysDeviceMapper;
import com.qf.smartplatform.pojo.*;
import com.qf.smartplatform.service.SysDeviceService;
import com.qf.smartplatform.utils.RequestUtil;
import com.qf.smartplatform.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/30 11:39
 */
@Service
@Transactional
public class SysDeviceServiceImpl implements SysDeviceService {
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private SysSceneCache sysSceneCache;

    @Autowired
    public void setSysSceneCache(SysSceneCache sysSceneCache) {
        this.sysSceneCache = sysSceneCache;
    }

    private SysDeviceMapper sysDeviceMapper;

    @Autowired
    public void setSysDeviceMapper(SysDeviceMapper sysDeviceMapper) {
        this.sysDeviceMapper = sysDeviceMapper;
    }

    private CategoryCache categoryCache;

    @Autowired
    public void setCategoryCache(CategoryCache categoryCache) {
        this.categoryCache = categoryCache;
    }

    @Override
    public void addDevice(SysDeviceDto sysDeviceDto) {
        Assert.isTrue(!sysDeviceDto.isEmpty(CheckType.ADD), ()->{
            throw new AddException("传递的数据不完整", ResultCode.DATA_NULL);
        });
        Long categoryId = sysDeviceDto.getCategoryId();
        SysCategory sysCategory = categoryCache.get(categoryId);
        Assert.isTrue(sysCategory!=null && sysCategory.getStatus()==1, ()->{
            throw new AddException("分类不存在", ResultCode.CATEGORY_NOT_EXIST);
        });
        sysDeviceMapper.addDevice(sysDeviceDto);
        update2Sell(sysDeviceDto.getDeviceId());
        bindDevice(sysDeviceDto.getDeviceId(), 17L);

    }

    @Override
    public int update2Sell(String deviceId) {
        Assert.hasText(deviceId, ()->{
            throw new UpdateException("主键不符合要求", ResultCode.ID_NOTALLOWED);
        });
        SysDevice sysDevice = sysDeviceMapper.findByDeviceId(deviceId);
        Assert.notNull(sysDevice, ()->{
            throw new UpdateException("设备不存在", ResultCode.DEVICE_NOT_EXIST);
        });
        Assert.isTrue(sysDevice.getStatus()==0,()->{
            throw new UpdateException("设备类型不匹配", ResultCode.DEVICE_NOT_EXIST);
        });
        return sysDeviceMapper.update2Sell(deviceId);
    }

    @Override
    public int bindDevice(String deviceId, Long sceneId) {
        MyBaseUser sysUserInfo = SecurityUtils.getSysUserInfo(false);
        if(-1 != sceneId){
            try {
                List<SysScene> sysScenes = sysSceneCache.getSysUserScene().get(sysUserInfo.getUserId());
                long count = sysScenes.stream().filter(sysScene -> sysScene.getSceneId() == sceneId).count();
                Assert.isTrue(count==1, ()->{
                    throw new AddException("场景不存在", ResultCode.SCENE_NOT_EXIST);
                });
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        SysDevice sysDevice = sysDeviceMapper.findByDeviceId(deviceId);
        Assert.notNull(sysDevice,()->{
            throw new AddException("设备不存在", ResultCode.DEVICE_NOT_EXIST);
        });
        Assert.isTrue(sysDevice.getStatus()!=2,()->{
            throw new AddException("设备已经被绑定", ResultCode.DEVICE_ALREADY_BIND);
        });
        Assert.isTrue(sysDevice.getStatus()==1||sysDevice.getStatus()==3,()->{
            throw new AddException("设备状态不匹配", ResultCode.DEVICE_STATUS_NOT_MATCH);
        });
        SysDevice device = new SysDevice();
        device.setDeviceId(sysDevice.getDeviceId());
        device.setBindUserId(sysUserInfo.getUserId());
        device.setBindTime(new Date());
        device.setSceneId(sceneId);
        return sysDeviceMapper.bindDevice(device);
    }

    @Override
    public List<SysDevice> findAllDevices(int page, int limit) {
        PageHelper.startPage(page, limit);
        MyBaseUser sysUserInfo = SecurityUtils.getSysUserInfo(false);
        Long uId = sysUserInfo.getUserId();
        List<SysDevice> list = sysDeviceMapper.findAllDeviceByUserId(uId);
        list.forEach(sysDevice ->
                sysDevice.setCategory(
                        categoryCache.get(sysDevice.getCategoryId())
                )
                );
        return list;
    }

    @Override
    public void sendControl(String deviceId, String command) {
        Long categoryId = sysDeviceMapper.findByDeviceId(deviceId).getCategoryId();
        Assert.notNull(categoryId, ()->{
            throw new QueryException("设备不存在", ResultCode.ID_NOTALLOWED);
        });
        SysCategory sysCategory = categoryCache.get(categoryId);
        String txCommand = sysCategory.getTxCommand();
        Assert.hasText(txCommand, ()->{
            throw new QueryException("当前设备目前不支持该命令", ResultCode.DEVICE_COMMAND_NOT_SUPPORT);
        });

        try {
            Map commandMap = objectMapper.readValue(txCommand, Map.class);
            Map realCommandMap = (Map) commandMap.get(command);
            Assert.notEmpty(realCommandMap, ()->{
                throw new QueryException("当前设备目前不支持该命令", ResultCode.DEVICE_COMMAND_NOT_SUPPORT);
            });
            Integer type = (Integer) realCommandMap.get("type");
            if (type==1){
                context.publishEvent(new DevicePowerCommandEvent(deviceId, (String) realCommandMap.get("command")));
            }else if (type==2){
                context.publishEvent(new DeviceRGBCommandEvent(deviceId, (String) realCommandMap.get("command")));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public void updateDeviceOnlineOffline(String deviceId, String ip) {
        SysDevice sysDevice = new SysDevice();
        sysDevice.setDeviceId(deviceId);
        if (ip!=null){
            sysDevice.setIsOnline(1L);
            sysDevice.setConnectTime(new Date());
            sysDevice.setCurrentConnectIp(ip);
            sysDevice.setConnectLocation(RequestUtil.getLocationByIp(ip));
        }else{
            sysDevice.setIsOnline(0L);
        }
        sysDeviceMapper.updateDevice(sysDevice);
    }

    @Override
    public void updateDevice(SysDeviceDto sysDeviceDto) {
        Assert.isTrue(!sysDeviceDto.isEmpty(CheckType.UPDATE),()->{
            throw new UpdateException("修改设备失败", ResultCode.DATA_NULL);
        });

        SysDevice sysDevice = new SysDevice();
        BeanUtils.copyProperties(sysDeviceDto, sysDevice);
        sysDeviceMapper.updateDevice(sysDevice);

    }
}

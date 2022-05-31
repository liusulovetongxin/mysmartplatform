package com.qf.smartplatform.service.impl;

import com.github.pagehelper.PageHelper;
import com.qf.smartplatform.Cache.CategoryCache;
import com.qf.smartplatform.Cache.SysSceneCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.dto.SysDeviceDto;
import com.qf.smartplatform.exceptions.AddException;
import com.qf.smartplatform.exceptions.UpdateException;
import com.qf.smartplatform.mapper.SysDeviceMapper;
import com.qf.smartplatform.pojo.*;
import com.qf.smartplatform.service.SysDeviceService;
import com.qf.smartplatform.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
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
public class SysDeviceServiceImpl implements SysDeviceService {
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
        SysCategory sysCategory = categoryCache.getById(categoryId);
        Assert.isTrue(sysCategory!=null && sysCategory.getStatus()==1, ()->{
            throw new AddException("分类不存在", ResultCode.CATEGORY_NOT_EXIST);
        });
        sysDeviceMapper.addDevice(sysDeviceDto);

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
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        if(-1 != sceneId){
            try {
                List<SysScene> sysScenes = sysSceneCache.getSysUserScene().get(sysUserInfo.getUId());
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
        device.setBindUserId(sysUserInfo.getUId());
        device.setBindTime(new Date());
        device.setSceneId(sceneId);
        return sysDeviceMapper.bindDevice(device);
    }

    @Override
    public List<SysDevice> findAllDevices(int page, int limit) {
        PageHelper.startPage(page, limit);
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        Long uId = sysUserInfo.getUId();
        List<SysDevice> list = sysDeviceMapper.findAllDeviceByUserId(uId);
        list.forEach(sysDevice ->
                sysDevice.setCategory(
                        categoryCache.getById(sysDevice.getCategoryId())
                )
                );
        return list;
    }
}

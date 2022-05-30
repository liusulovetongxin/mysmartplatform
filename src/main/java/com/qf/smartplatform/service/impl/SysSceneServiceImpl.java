package com.qf.smartplatform.service.impl;

import com.qf.smartplatform.Cache.SysSceneCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.exceptions.AddException;
import com.qf.smartplatform.mapper.SysSceneMapper;
import com.qf.smartplatform.pojo.CheckType;
import com.qf.smartplatform.pojo.SysScene;
import com.qf.smartplatform.pojo.SysUserInfo;
import com.qf.smartplatform.service.SysSceneService;
import com.qf.smartplatform.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/30 14:04
 */
@Service
public class SysSceneServiceImpl implements SysSceneService {
    private SysSceneMapper sysSceneMapper;

    @Autowired
    public void setSysSceneMapper(SysSceneMapper sysSceneMapper) {
        this.sysSceneMapper = sysSceneMapper;
    }

    private SysSceneCache sysSceneCache;

    @Autowired
    public void setSysSceneCache(SysSceneCache sysSceneCache) {
        this.sysSceneCache = sysSceneCache;
    }

    @Override
    public void addScene(SysScene sysScene) {
        Assert.isTrue(!sysScene.isEmpty(CheckType.ADD),()->{
            throw new AddException("传递的数据不完整", ResultCode.DATA_NULL);
        });
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        try {
            List<SysScene> sceneList = sysSceneCache.getSysUserScene().get(sysUserInfo.getUId());
            long count = sceneList.stream().filter(sysScene1 -> sysScene1.getSceneName().equals(sysScene.getSceneName())).count();
            Assert.isTrue(count==0,()->{
                throw new AddException("场景已经存在", ResultCode.DATA_ALREADY_EXIST);
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        sysScene.setCreateBy(sysUserInfo.getUId());
        sysSceneMapper.addScene(sysScene);
    }

    @Override
    public List<SysScene> findAllSceneByUserId() {
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        try {
            return sysSceneCache.getSysUserScene().get(sysUserInfo.getUId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

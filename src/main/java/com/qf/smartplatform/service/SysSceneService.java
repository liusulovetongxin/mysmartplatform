package com.qf.smartplatform.service;

import com.qf.smartplatform.pojo.SysScene;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/30 14:01
 */

public interface SysSceneService {
    void addScene(SysScene sysScene);

    List<SysScene> findAllSceneByUserId();
}

package com.qf.smartplatform.Cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.qf.smartplatform.mapper.SysSceneMapper;
import com.qf.smartplatform.pojo.SysScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.Cache
 * @Description:
 * @Date 2022/5/30 15:38
 */
@Component
public class SysSceneCache {
    private SysSceneMapper sysSceneMapper;

    @Autowired
    public void setSysSceneMapper(SysSceneMapper sysSceneMapper) {
        this.sysSceneMapper = sysSceneMapper;
    }

    public LoadingCache<Long, List<SysScene>> getSysUserScene() {
        return sysUserScene;
    }

    LoadingCache<Long, List<SysScene>> sysUserScene =
            CacheBuilder
                    .newBuilder()
                    .expireAfterAccess(1, TimeUnit.MINUTES)
                    .build(new CacheLoader<Long, List<SysScene>>() {
                        @Override
                        public List<SysScene> load(Long userId) throws Exception {
                            return sysSceneMapper.findByUid(userId);
                        }
                    });
}

package com.qf.smartplatform.service.impl;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.Cache.AreaCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.event.AreaChangeEvent;
import com.qf.smartplatform.exceptions.AddException;
import com.qf.smartplatform.mapper.SysAreaMapper;
import com.qf.smartplatform.pojo.CheckType;
import com.qf.smartplatform.pojo.SysArea;
import com.qf.smartplatform.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/27 20:46
 */
@Service
public class SysAreaServiceImpl implements SysAreaService {
    private SysAreaMapper sysAreaMapper;

    @Autowired
    public void setSysAreaMapper(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    private AreaCache areaCache;

    @Autowired
    public void setAreaCache(AreaCache areaCache) {
        this.areaCache = areaCache;
    }

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void addArea(SysArea sysArea) {
        Assert.isTrue(!sysArea.isEmpty(CheckType.ADD), ()->{
            throw new AddException("没有传递必要数据", ResultCode.DATA_NULL);
        });
        SysArea area = sysAreaMapper.findById(sysArea.getAreaId());
        Assert.isNull(area, ()->{
            throw new AddException("数据已经存在，不能添加重复数据", ResultCode.DATA_EXIST);
        });
        sysAreaMapper.addArea(sysArea);
        context.publishEvent(new AreaChangeEvent());
    }

    @Override
    public List<SysArea> findAll() {
        List<SysArea> areaList = areaCache.getAreaList();
        return areaList;
    }

    @Override
    public PageInfo<SysArea> findByPage(int pageSize, int pageNum) {
        List<SysArea> areaList = areaCache.getAreaList();
        List<SysArea> collect = areaList.stream()
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        PageInfo<SysArea> pageInfo = new PageInfo<>(collect);
        return pageInfo;
    }
}

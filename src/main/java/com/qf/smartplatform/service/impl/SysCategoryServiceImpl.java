package com.qf.smartplatform.service.impl;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.Cache.CategoryCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.event.CategoryChangeEvent;
import com.qf.smartplatform.exceptions.AddException;
import com.qf.smartplatform.exceptions.DeleteDataException;
import com.qf.smartplatform.exceptions.UpdateException;
import com.qf.smartplatform.mapper.SysCategoryMapper;
import com.qf.smartplatform.pojo.CheckType;
import com.qf.smartplatform.pojo.SysCategory;
import com.qf.smartplatform.pojo.SysUserInfo;
import com.qf.smartplatform.service.SysCategoryService;
import com.qf.smartplatform.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/27 11:51
 */
@Service
public class SysCategoryServiceImpl implements SysCategoryService {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private CategoryCache categoryCache;

    @Autowired
    public void setCategoryCache(CategoryCache categoryCache) {
        this.categoryCache = categoryCache;
    }

    private SysCategoryMapper sysCategoryMapper;

    @Autowired
    public void setSysCategoryMapper(SysCategoryMapper sysCategoryMapper) {
        this.sysCategoryMapper = sysCategoryMapper;
    }

    @Override
    public void addCategory(SysCategory sysCategory) {
        Assert.isTrue(!sysCategory.isEmpty(CheckType.ADD), ()->{
            throw new AddException("传递的数据不完整", ResultCode.DATA_NULL);
        });

        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        sysCategory.setCreateBy(sysUserInfo.getUsername());
        sysCategoryMapper.addCategory(sysCategory);
        context.publishEvent(new CategoryChangeEvent());
    }

    @Override
    public PageInfo<SysCategory> findAll(int pageSize, int pageNum) {
        List<SysCategory> categoryList = categoryCache.getCategoryList(true);
        List<SysCategory> result = categoryList.stream()
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        PageInfo<SysCategory> pageInfo = new PageInfo<>(result);
        pageInfo.setTotal(categoryList.size());
        return pageInfo;
    }

    @Override
    public void deleteById(Long cId) {
        Assert.isTrue(cId>0, ()->{
            throw new DeleteDataException("主键不符合要求", ResultCode.ID_NOTALLOWED);
        });
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        sysCategoryMapper.deleteById(cId,new Date(),sysUserInfo.getUsername());
        context.publishEvent(new CategoryChangeEvent());
    }

    @Override
    public void updateCategory(SysCategory sysCategory) {
        Assert.isTrue(!sysCategory.isEmpty(CheckType.UPDATE), ()->{
            throw new UpdateException("传递的数据不完成", ResultCode.DATA_NULL);
        });
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);
        sysCategory.setUpdateTime(new Date());
        sysCategory.setUpdateBy(sysUserInfo.getUsername());
        sysCategoryMapper.updateCategory(sysCategory);
        context.publishEvent(new CategoryChangeEvent());
    }

    @Override
    public void deleteByIds(List<Long> ids, Long status) {
        Assert.notEmpty(ids, ()->{
            throw new DeleteDataException("主键不符合要求", ResultCode.ID_NOTALLOWED);
        });
        SysUserInfo sysUserInfo = SecurityUtils.getSysUserInfo(false);

        sysCategoryMapper.deleteByIds(ids,sysUserInfo.getUsername(),new Date(),status);
        context.publishEvent(new CategoryChangeEvent());
    }
}

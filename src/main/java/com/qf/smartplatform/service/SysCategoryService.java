package com.qf.smartplatform.service;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.pojo.SysCategory;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/27 11:50
 */

public interface SysCategoryService {
    void addCategory(SysCategory sysCategory);
    PageInfo<SysCategory> findAll(int pageSize,int pageNum);

    void deleteById(Long cId);

    void updateCategory(SysCategory sysCategory);

    void deleteByIds(List<Long> ids, Long status);
}

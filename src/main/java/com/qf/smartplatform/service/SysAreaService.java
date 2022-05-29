package com.qf.smartplatform.service;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.pojo.SysArea;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/27 20:44
 */

public interface SysAreaService {
    void addArea(SysArea sysArea);

    List<SysArea> findAll();

    PageInfo<SysArea> findByPage(int pageSize, int pageNum);
}

package com.qf.smartplatform.service;

import com.qf.smartplatform.pojo.SysMenu;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/31 16:41
 */

public interface SysMenuService {
    List<SysMenu> findAllMenus();
}

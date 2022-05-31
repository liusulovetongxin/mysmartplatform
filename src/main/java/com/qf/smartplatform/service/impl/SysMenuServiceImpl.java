package com.qf.smartplatform.service.impl;

import com.qf.smartplatform.mapper.SysMenuMapper;
import com.qf.smartplatform.pojo.SysMenu;
import com.qf.smartplatform.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/31 16:42
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    private SysMenuMapper sysMenuMapper;

    @Autowired
    public void setSysMenuMapper(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public List<SysMenu> findAllMenus() {
        List<SysMenu> list = sysMenuMapper.findAllMenus();
        return list.stream()
                .filter(sysMenu ->
                        0 != sysMenu.getParentId()
                                &&
                                "M".equalsIgnoreCase(sysMenu.getMenuType()))
                .collect(Collectors.toList());
    }
}

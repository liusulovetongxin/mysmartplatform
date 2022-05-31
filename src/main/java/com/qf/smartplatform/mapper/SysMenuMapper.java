package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/31 16:43
 */

public interface SysMenuMapper {
    @Select("select * from sys_menu")
    List<SysMenu> findAllMenus();
}

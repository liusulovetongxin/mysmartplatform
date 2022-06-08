package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/6/5 21:03
 */

public interface SysRoleMapper {

    @Select("select * from sys_role")
    List<SysRole> findAll();

    @Select("select * from sys_role where role_id = #{roleId}")
    SysRole findById(@Param("roleId") Long roleId);

    void addSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);
}

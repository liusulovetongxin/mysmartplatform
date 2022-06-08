package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/6/7 10:33
 */

public interface RoleMapper {
    @Select("select * from sys_role")
    List<SysRole> selectAll();
}

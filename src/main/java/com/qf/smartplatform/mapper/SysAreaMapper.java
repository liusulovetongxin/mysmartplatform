package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysArea;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/27 20:46
 */

public interface SysAreaMapper {
    @Insert("insert into sys_area values (#{areaId},#{areaName},#{parentId},#{level})")
    void addArea(SysArea sysArea);
    @Select("select * from sys_area where area_id= #{areaId}")
    SysArea findById(@Param("areaId") Long areaId);

    @Select("select * from sys_area")
    List<SysArea> findAll();
}

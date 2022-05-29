package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/27 10:51
 */

public interface SysCategoryMapper {
    @Insert("INSERT INTO sys_category (category_name,tx_command,rx_command,command_name,create_by) VALUES (#{categoryName},#{txCommand},#{rxCommand},#{commandName},#{createBy})")
    void addCategory(SysCategory sysCategory);

    @Select("select * from sys_category")
    List<SysCategory> findAll();

    @Update("update sys_category set status=0,update_time=#{updateTime},update_by=#{username} where c_id=#{cId}")
    void deleteById(@Param("cId") Long cId,@Param("updateTime") Date updateTime,@Param("username") String username);

    @Update("update sys_category set category_name=#{categoryName},update_time=#{updateTime},update_by=#{updateBy} where c_id = #{cId}")
    void updateCategory(SysCategory sysCategory);
}

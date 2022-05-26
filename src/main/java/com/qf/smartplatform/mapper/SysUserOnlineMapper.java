package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysUserOnline;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/26 20:31
 */

public interface SysUserOnlineMapper {
    @Select("SELECT  sessionId FROM sys_user_online WHERE login_name= #{username} ")
    String findOnlineDataByUsername(String username);
    @Delete("DELETE FROM sys_user_online WHERE login_name= #{username}")
    void deleteByUserName(String username);
    @Insert("INSERT INTO sys_user_online VALUES(#{sessionId},#{loginName},#{ipaddr},#{loginLocation},#{browser},#{os},#{status},#{startTimestamp},#{lastAccessTime},#{expireTime})")
    void addOnlineData(SysUserOnline online);


}

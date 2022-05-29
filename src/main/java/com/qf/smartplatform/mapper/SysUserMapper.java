package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysUserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/25 17:32
 */
public interface SysUserMapper {
    @Select("select * from sys_user_info where username=#{username }")
    SysUserInfo findByUserName(String username);
    @Select("select * from sys_user_info where email=#{email }")
    SysUserInfo findByEmail(String email);
    @Select("select * from sys_user_info where phone=#{phone }")
    SysUserInfo findByPhone(String phone);
    @Insert("INSERT INTO sys_user_info (username,password,pwd_salt,`name`,phone,email,sex,avator,info,`type`,status,create_time,create_by,remark) VALUES(#{username},#{password},#{pwdSalt},#{name},#{phone},#{email},#{sex},#{avator},#{info},#{type},#{status},#{createTime},#{createBy},#{remark})")
    void addUser(SysUserInfo sysUserInfo);
    @Update("update sys_user_info set current_login =last_login,last_login=#{processTime},current_login_ip=last_login_ip,last_login_ip=#{ip} where username=#{loginName}")
    void updateLogin(@Param("loginName") String loginName,@Param("processTime") Date processTime,@Param("ip") String ip);

    @Update("update sys_user_info set password=#{password},pwd_salt=#{pwdSalt},update_time=#{updateTime},update_by=#{updateBy} where username=#{username}")
    void updatePWD(SysUserInfo sysUserInfo);

    void updateData(SysUserInfo sysUserInfo);
}

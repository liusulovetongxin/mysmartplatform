package com.qf.smartplatform.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/6/7 10:46
 */

public interface UserRoleMapper {
    @Select("select role_id from sys_user_role where user_id = #{userId}")
    List<Long> selectRoleIdByUserId(Long userId);
}

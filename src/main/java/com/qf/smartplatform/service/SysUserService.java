package com.qf.smartplatform.service;

import com.qf.smartplatform.dto.UserDto;
import com.qf.smartplatform.pojo.SysUserInfo;

import java.util.Date;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/5/25 17:30
 */

public interface SysUserService {
    void addUser(UserDto userDto);

    SysUserInfo login(UserDto userDto);

    void updateLogin(String loginName, Date processTime, String ip);
}

package com.qf.test;

import com.qf.smartplatform.dto.UserDto;
import com.qf.smartplatform.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.test
 * @Description:
 * @Date 2022/5/25 17:36
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-dao.xml"})
@WebAppConfiguration
public class TestSysUser {

    private SysUserService userService;
    @Autowired
    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @Test
    public void testAdd(){
        UserDto userDto = new UserDto();
        userDto.setUsername("ceshi");
        userDto.setPassword("mima");
        userDto.setSex(1L);
        userDto.setName("测试");
        userDto.setEmail("ceshi@ceshi.com");
        userDto.setPhone("13344444444");
        userService.addUser(userDto);
    }

    @Test
    public void testLogin(){
        UserDto userDto = new UserDto();
        userDto.setUsername("ceshi");
        userDto.setPassword("mima");
        userDto.setSex(1L);
        userDto.setName("测试");
        userDto.setEmail("ceshi@ceshi.com");
        userDto.setPhone("13344444444");
        userService.login(userDto);
    }
}

package com.qf.smartplatform.controller;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.dto.UserDto;
import com.qf.smartplatform.pojo.SysUserInfo;
import com.qf.smartplatform.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/26 10:42
 */
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    private SysUserService sysUserService;
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping("/register")
    public R register(@RequestBody UserDto userDto){
        sysUserService.addUser(userDto);
        return R.setOk();
    }

    @RequestMapping("/login")
    public R login(@RequestBody UserDto userDto, HttpSession session){
        SysUserInfo userInfo = sysUserService.login(userDto);
        session.setAttribute("user", userInfo);
        return R.setOk();
    }

    @PostMapping("/updatepwd")
    public R updatePWD(String username,String password,String newPassword){
//        System.err.println(username+" "+password+" "+newPassword);
        sysUserService.updatePWD(username, password, newPassword);
        return R.setOk();
    }

    @PostMapping("/updatedata")
    public R updateData(@RequestBody UserDto userDto){
        sysUserService.updateData(userDto);
        return R.setOk();
    }
}

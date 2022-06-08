package com.qf.smartplatform.security.service;

import com.qf.smartplatform.mapper.SysUserMapper;
import com.qf.smartplatform.pojo.MyBaseUser;
import com.qf.smartplatform.pojo.SysUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.security.service
 * @Description:
 * @Date 2022/6/6 11:58
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    private SysUserMapper sysUserMapper;

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserInfo sysUserInfo = sysUserMapper.findByUserName(username);
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().setAttribute("username", sysUserInfo.getUsername());
        Assert.notNull(sysUserInfo, ()->{
            throw new UsernameNotFoundException("用户名或密码错误");
        });
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute("user",sysUserInfo);
        MyBaseUser user = new MyBaseUser(username, sysUserInfo.getPassword(), new ArrayList<>());
        user.setUserId(sysUserInfo.getUId());
        return user;
    }
}

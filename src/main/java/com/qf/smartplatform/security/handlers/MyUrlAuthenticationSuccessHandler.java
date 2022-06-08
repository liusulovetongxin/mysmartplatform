package com.qf.smartplatform.security.handlers;

import com.qf.smartplatform.Cache.RoleCache;
import com.qf.smartplatform.event.LoginEvent;
import com.qf.smartplatform.mapper.UserRoleMapper;
import com.qf.smartplatform.pojo.MyBaseUser;
import com.qf.smartplatform.pojo.SysMenu;
import com.qf.smartplatform.pojo.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.security.handlers
 * @Description:
 * @Date 2022/6/6 11:45
 */
@Component
public class MyUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RoleCache roleCache;

    @Autowired
    public void setRoleCache(RoleCache roleCache) {
        this.roleCache = roleCache;
    }

    private UserRoleMapper userRoleMapper;
    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //        super.onAuthenticationSuccess(request, response, chain, authentication);
        response.sendRedirect("/index.html");
        MyBaseUser principal = (MyBaseUser) authentication.getPrincipal();
        Long userId = principal.getUserId();
        List<Long> roleIds= userRoleMapper.selectRoleIdByUserId(userId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roleIds.forEach(roleId->{
            SysRole sysRole = roleCache.get(roleId);
            List<SysMenu> menuList = sysRole.getMenuList();
            List<SimpleGrantedAuthority> list = menuList.stream()
                    .map(SysMenu::getPerms)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            authorities.addAll(list);
        });
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal,authentication.getCredentials(),authorities));
        context.publishEvent(new LoginEvent(LoginEvent.LoginType.SUCCESS,principal.getUsername()));
    }


}

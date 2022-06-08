package com.qf.smartplatform.config;

import com.qf.smartplatform.Cache.MenuCache;
import com.qf.smartplatform.pojo.SysMenu;
import com.qf.smartplatform.security.handlers.MyUrlAuthenticationFailureHandler;
import com.qf.smartplatform.security.handlers.MyUrlAuthenticationSuccessHandler;
import com.qf.smartplatform.security.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.config
 * @Description:
 * @Date 2022/6/6 11:39
 */
@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private MenuCache menuCache;

    @Autowired
    public void setMenuCache(MenuCache menuCache) {
        this.menuCache = menuCache;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private MyUserDetailService myUserDetailService;

    @Autowired
    public void setMyUserDetailService(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    private MyUrlAuthenticationSuccessHandler myUrlAuthenticationSuccessHandler;

    @Autowired
    public void setMyUrlAuthenticationSuccessHandler(MyUrlAuthenticationSuccessHandler myUrlAuthenticationSuccessHandler) {
        this.myUrlAuthenticationSuccessHandler = myUrlAuthenticationSuccessHandler;
    }
    private MyUrlAuthenticationFailureHandler myUrlAuthenticationFailureHandler;

    @Autowired
    public void setMyUrlAuthenticationFailureHandler(MyUrlAuthenticationFailureHandler myUrlAuthenticationFailureHandler) {
        this.myUrlAuthenticationFailureHandler = myUrlAuthenticationFailureHandler;
    }

    /**
     * 配置需要拦截的地址，什么地址需要什么权限
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable();
        List<SysMenu> allMenus = menuCache.getAllData();
        allMenus.stream().filter(menu-> !"#".equals(menu.getUrl())&&menu.getUrl()!=null&&!menu.getUrl().equalsIgnoreCase("")).forEach(menu->{
                    try {
                        http.authorizeRequests().antMatchers(menu.getUrl()).hasAuthority(menu.getPerms());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        http.authorizeRequests()//所有的请求
                .and().formLogin()
                .loginProcessingUrl("/login")
                .successHandler(myUrlAuthenticationSuccessHandler)
                .failureHandler(myUrlAuthenticationFailureHandler)
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/js/**",
                "/layui/**",
                "/websocket/*",
                "/colorcommand/*",
                "/humiture/*"
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}

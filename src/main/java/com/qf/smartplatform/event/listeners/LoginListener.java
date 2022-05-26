package com.qf.smartplatform.event.listeners;

import com.qf.smartplatform.event.LoginEvent;
import com.qf.smartplatform.pojo.SysLogininfor;
import com.qf.smartplatform.pojo.SysUserOnline;
import com.qf.smartplatform.service.SysUserOnlineService;
import com.qf.smartplatform.service.SysUserService;
import com.qf.smartplatform.service.UserLoginLogService;
import com.qf.smartplatform.utils.RequestUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.event.listeners
 * @Description:
 * @Date 2022/5/26 11:33
 */
@Component
//@EnableAsync 开启异步
public class LoginListener {
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }
    private UserLoginLogService userLoginLogService;

    @Autowired
    public void setUserLoginLogService(UserLoginLogService userLoginLogService) {
        this.userLoginLogService = userLoginLogService;
    }

    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    private SysUserOnlineService sysUserOnlineService;

    @Autowired
    public void setSysUserOnlineService(SysUserOnlineService sysUserOnlineService) {
        this.sysUserOnlineService = sysUserOnlineService;
    }

    //    @Async 开启异步
    @EventListener
    public void onEvent(LoginEvent event){
//        System.err.println("监听到了一个事件"+event);
        // 要清楚 在监听到登录事件后需要干什么？
        // 1.要将登录的信息保存到日志中 logininfor表，确定需要的数据
        String loginName = event.getUsername();
        // 通过工具类获取信息，但是工具类需要一个request，这个在哪里取？通过spring的类获取，然后强转
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String sessionId = request.getSession().getId();
        Map<String, String> osAndBrowserInfo = RequestUtil.getOsAndBrowserInfo(request);
        // 获取ip
        String ip = RequestUtil.getRemoteHost(request);
        // 当前处理的时间
        Date processTime = new Date();
        // 通过ip获取地址，但是想要存日志的功能不影响登录的操作，所以需要异步的执行，但是如果全部异步的话，
        // 因为换了线程，所以request会出现问题，所以就局部的实现异步操作，怎么局部的实现异步操作呢？
        // 通过局部的开启线程,这样又有一个问题，线程池怎么来？手动创建线程池。
        CompletableFuture.runAsync(()->{
            // 异步操作，异步要干什么？
            // 获取ip地址
            String locationByIp = RequestUtil.getLocationByIp(ip);
            //获取完了，干什么? 保存啊
            SysLogininfor sysLogininfor = new SysLogininfor();
            // 加数据
            sysLogininfor.setLoginName(loginName);
            sysLogininfor.setOs(osAndBrowserInfo.get("os"));
            sysLogininfor.setBrowser(osAndBrowserInfo.get("browser"));
            sysLogininfor.setLoginTime(processTime);
            sysLogininfor.setIpaddr(ip);
            sysLogininfor.setLoginLocation(locationByIp);
            // 设置登录状态
            switch (event.getType()){
                case SUCCESS:
                    sysLogininfor.setStatus("1");
                    sysLogininfor.setMsg("登录成功");
                    break;
                case FAIL:
                    sysLogininfor.setStatus("0");
                    sysLogininfor.setMsg("登录失败，用户名或密码错误");
                    break;
            }
            // 先打印测试一下
//            System.err.println(sysLogininfor);
            // 测试可以拿到数据了，添加到数据库中，需要service，创建service
            userLoginLogService.addLoginLog(sysLogininfor);
            // 但是我们看到，并不只是这一张表需要添加数据
            // 需要添加，或者更新在线的用户的表
            SysUserOnline sysUserOnline = new SysUserOnline();
            BeanUtils.copyProperties(sysLogininfor, sysUserOnline);
            sysUserOnline.setSessionId(sessionId);
            sysUserOnline.setStartTimestamp(processTime);
            sysUserOnline.setExpireTime(30L);
            sysUserOnlineService.addOrUpdateOnline(sysUserOnline);
        }, threadPoolExecutor);
        CompletableFuture.runAsync(()->{
            switch (event.getType()){
                case SUCCESS:
                    // 更新用户表的几个数据
                    sysUserService.updateLogin(loginName,processTime,ip);
                    break;
            }
        }, threadPoolExecutor);

    }
}

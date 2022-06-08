package com.qf.smartplatform.operlog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.smartplatform.exceptions.BaseException;
import com.qf.smartplatform.operlog.annotations.LogAnnotation;
import com.qf.smartplatform.pojo.MyBaseUser;
import com.qf.smartplatform.pojo.SysOperLog;
import com.qf.smartplatform.utils.RequestUtil;
import com.qf.smartplatform.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.operlog
 * @Description:
 * @Date 2022/6/8 11:07
 */
@Aspect
@Component
public class LogAdvice {
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(com.qf.smartplatform.operlog.annotations.LogAnnotation)")
    public void pointcut(){

    }
    @AfterReturning(pointcut = "pointcut()",returning = "result")
    public void  after(JoinPoint joinPoint,Object result){
        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setStatus(1L);
        sysOperLog.setErrorMsg("200 OK");
        String resultJson = null;
        try {
            resultJson = objectMapper.writeValueAsString(result);
            sysOperLog.setJsonResult(resultJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        addLog(joinPoint, sysOperLog);

    }
    @AfterThrowing(pointcut = "pointcut()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Throwable e){
        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setStatus(0L);
        if (e instanceof BaseException){
            sysOperLog.setErrorMsg(((BaseException) e).getCode()+e.getMessage());
        }
        else{
            sysOperLog.setErrorMsg(e.getMessage());
        }
        addLog(joinPoint, sysOperLog);
    }

    public void addLog(JoinPoint joinPoint,SysOperLog sysOperLog){
        MyBaseUser baseUser = SecurityUtils.getSysUserInfo(false);
        if (baseUser != null) {
            sysOperLog.setOperName(baseUser.getUsername());
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CompletableFuture.runAsync(()->{
            String requestMethod = request.getMethod();
            sysOperLog.setRequestMethod(requestMethod);
            String requestURI = request.getRequestURI();
            sysOperLog.setOperUrl(requestMethod);
            String ip = RequestUtil.getRemoteHost(request);

            Map<String, String> osAndBrowserInfo = null;
            try {
                osAndBrowserInfo = RequestUtil.getOsAndBrowserInfo(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String os = osAndBrowserInfo.get("os");
            switch (os){
                case "Android":
                case "IPhone":
                    //设置来自于移动端
                    sysOperLog.setOperatorType(2L);
                    break;
                case "Windows":
                case "Mac":
                case "Unix":
                    //设置来自于PC端
                    sysOperLog.setOperatorType(1L);
                    break;
                default:
                    //设置来自于其他端
                    sysOperLog.setOperatorType(0L);
                    break;
            }
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getName();
            sysOperLog.setMethod(methodName);
            Method method = signature.getMethod();
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
            if (logAnnotation != null) {
                String title = logAnnotation.title();
                sysOperLog.setTitle(title);
                long businessType = logAnnotation.businessType();
                sysOperLog.setBusinessType(businessType);
            }
            Object[] args = joinPoint.getArgs();
            try {
                sysOperLog.setOperParam(objectMapper.writeValueAsString(args));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            sysOperLog.setOperIp(ip);
            String location = RequestUtil.getLocationByIp(ip);
            sysOperLog.setOperLocation(location);
            sysOperLog.setOperTime(new Date());
            System.err.println(sysOperLog);
        });

    }
}

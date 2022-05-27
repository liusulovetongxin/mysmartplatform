package com.qf.smartplatform.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.dto.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.interceptors
 * @Description:
 * @Date 2022/5/27 18:05
 */

@Controller
public class MyLoginInterceptor implements HandlerInterceptor {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(R.setResult(ResultCode.NOT_LOGIN,"未登录",null)));
        }
        return user!=null;
    }
}

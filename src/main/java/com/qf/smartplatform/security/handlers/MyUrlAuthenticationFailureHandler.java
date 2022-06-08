package com.qf.smartplatform.security.handlers;

import com.qf.smartplatform.event.LoginEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.security.handlers
 * @Description:
 * @Date 2022/6/6 11:49
 */
@Component
public class MyUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
        Object username = request.getAttribute("username");
        context.publishEvent(new LoginEvent(LoginEvent.LoginType.FAIL, (String) username));
    }
}

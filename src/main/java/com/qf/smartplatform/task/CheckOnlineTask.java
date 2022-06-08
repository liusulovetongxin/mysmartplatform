package com.qf.smartplatform.task;

import com.qf.smartplatform.event.CheckOnlineEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.task
 * @Description:
 * @Date 2022/6/3 20:30
 */

@EnableScheduling
@Component
public class CheckOnlineTask {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void checkOnline(){
        System.err.println("CheckOnlineTask类中的checkOnline方法执行了-->");
        context.publishEvent(new CheckOnlineEvent());
    }
}

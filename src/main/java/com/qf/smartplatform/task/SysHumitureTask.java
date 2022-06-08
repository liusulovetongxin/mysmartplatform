package com.qf.smartplatform.task;

import com.qf.smartplatform.event.SysHumitureTaskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.task
 * @Description:
 * @Date 2022/6/4 10:50
 */

@Component
public class SysHumitureTask {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void checkSysHumiture(){
        context.publishEvent(new SysHumitureTaskEvent());
    }
}

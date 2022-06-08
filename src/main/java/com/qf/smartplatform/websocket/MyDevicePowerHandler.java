package com.qf.smartplatform.websocket;

import com.qf.smartplatform.event.DevicePowerCommandEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.websocket
 * @Description:
 * @Date 2022/5/31 10:44
 */
@Component
public class MyDevicePowerHandler extends MyBaseHandler{
    private static Map<String,Long> heartbeatTime = new ConcurrentHashMap<>();
    private static Map<String,WebSocketSession> allClient = new ConcurrentHashMap<>();
    @Override
    public boolean isNeedUpdateOnline() {
        return true;
    }

    @Override
    protected Map<String, Long> getHeartbeatTime() {
        return heartbeatTime;
    }

    @Override
    protected Map<String, WebSocketSession> getAllClient() {
        return allClient;
    }

    @EventListener
    @Async
    public void onEvent(DevicePowerCommandEvent deviceCommandEvent){
        System.err.println("收到了一个发送命令的事件"+deviceCommandEvent);
        WebSocketSession session = getAllClient().get(deviceCommandEvent.getDeviceId());
        if (session!=null&&session.isOpen()){
            try {
                session.sendMessage(new TextMessage(deviceCommandEvent.getCommand()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

package com.qf.smartplatform.websocket;

import com.qf.smartplatform.event.CheckOnlineEvent;
import com.qf.smartplatform.event.DeviceOnLineEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.websocket
 * @Description:
 * @Date 2022/5/31 10:44
 */
@Component
public abstract class MyBaseHandler extends TextWebSocketHandler {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        String deviceId = uri.substring(uri.lastIndexOf("/") + 1);
        WebSocketSession webSocketSession = getAllClient().get(deviceId);
        if (webSocketSession != null && webSocketSession.isOpen())
            webSocketSession.close();
        session.getAttributes().put("name", deviceId);
        getAllClient().put(deviceId, session);
        getHeartbeatTime().put(deviceId,System.currentTimeMillis());
        System.err.println("MyTextMessageHandler类中的afterConnectionEstablished方法执行了-->" + deviceId + session);
        super.afterConnectionEstablished(session);
        if (isNeedUpdateOnline()) {
            DeviceOnLineEvent deviceOnLineEvent = new DeviceOnLineEvent();
            deviceOnLineEvent.setDeviceId((String) deviceId);
            deviceOnLineEvent.setIp(session.getRemoteAddress().getHostString());
            context.publishEvent(deviceOnLineEvent);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.err.println("MyTextMessageHandler类中的handleTextMessage方法执行了-->");
        Object deviceId = session.getAttributes().get("name");
        getHeartbeatTime().put((String) deviceId, System.currentTimeMillis());
        super.handleTextMessage(session, message);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object deviceId = session.getAttributes().get("name");
        getAllClient().remove(deviceId);
        getHeartbeatTime().remove(deviceId);
        super.afterConnectionClosed(session, status);
        if (isNeedUpdateOnline()) {
            DeviceOnLineEvent deviceOnLineEvent = new DeviceOnLineEvent();
            deviceOnLineEvent.setDeviceId(deviceId.toString());
            context.publishEvent(deviceOnLineEvent);
        }
    }

    @EventListener
    @Async
    public void onEvent(CheckOnlineEvent event){
        getHeartbeatTime().entrySet().forEach(entry->{
            String deviceId = entry.getKey();
            Long lastHeartTime = entry.getValue();
            if (System.currentTimeMillis()-lastHeartTime>60000){
                WebSocketSession webSocketSession = getAllClient().get(deviceId);
                if (webSocketSession!=null&&webSocketSession.isOpen()){
                    try {
                        webSocketSession.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public abstract boolean isNeedUpdateOnline();

    protected abstract Map<String, Long> getHeartbeatTime();

    protected abstract Map<String, WebSocketSession> getAllClient();
}

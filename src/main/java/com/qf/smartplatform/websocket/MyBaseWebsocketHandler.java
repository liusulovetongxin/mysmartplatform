package com.qf.smartplatform.websocket;

import com.qf.smartplatform.event.DeviceOnLineEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.websocket
 * @Description:
 * @Date 2022/6/9 20:03
 */
@Component
public abstract class MyBaseWebsocketHandler extends TextWebSocketHandler {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        System.err.println(uri);
        String deviceId = uri.substring(uri.lastIndexOf("/") + 1);
        WebSocketSession webSocketSession = getAllClient().get(deviceId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        session.getAttributes().put("deviceId", deviceId);
        getAllClient().put(deviceId, session);
        System.err.println("MyBaseWebsocketHandler类中的afterConnectionEstablished方法执行了-->");
        super.afterConnectionEstablished(session);
        if (isNeedUpdateOnline()){
            DeviceOnLineEvent deviceOnLineEvent = new DeviceOnLineEvent();
            deviceOnLineEvent.setDeviceId(deviceId);
            deviceOnLineEvent.setIp(session.getRemoteAddress().getHostString());
            context.publishEvent(deviceOnLineEvent);
        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.err.println("MyBaseWebsocketHandler类中的handleTextMessage方法执行了-->");
        Object deviceId = session.getAttributes().get("deviceId");
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object deviceId = session.getAttributes().get("deviceId");
        getAllClient().remove(deviceId);
        super.afterConnectionClosed(session, status);
        if (isNeedUpdateOnline()) {
            DeviceOnLineEvent deviceOnLineEvent = new DeviceOnLineEvent();
            deviceOnLineEvent.setDeviceId(deviceId.toString());
            context.publishEvent(deviceOnLineEvent);
        }
    }


    public abstract boolean isNeedUpdateOnline();

    protected abstract Map<String,WebSocketSession> getAllClient();
}

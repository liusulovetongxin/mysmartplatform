package com.qf.smartplatform.websocket;

import com.qf.smartplatform.event.DevicePowerCommandEvent;
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
public class MyDevicePowerHandler extends TextWebSocketHandler {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private static Map<String,WebSocketSession> allClient = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        String name = uri.substring(uri.lastIndexOf("/") + 1);
        WebSocketSession webSocketSession = allClient.get(name);
        if (webSocketSession!=null&&webSocketSession.isOpen()){
            webSocketSession.close();
        }
        session.getAttributes().put("name", name);
        allClient.put(name, session);
        System.err.println("MyTextMessageHandler类中的afterConnectionEstablished方法执行了-->"+name+session);
        super.afterConnectionEstablished(session);
        DeviceOnLineEvent deviceOnLineEvent = new DeviceOnLineEvent();
        deviceOnLineEvent.setDeviceId(name);
        deviceOnLineEvent.setIp(session.getRemoteAddress().getHostString());
        context.publishEvent(deviceOnLineEvent);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.err.println("MyTextMessageHandler类中的handleTextMessage方法执行了-->");
        super.handleTextMessage(session, message);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object name = session.getAttributes().get("name");
        allClient.remove(name);
        super.afterConnectionClosed(session, status);
        DeviceOnLineEvent deviceOnLineEvent = new DeviceOnLineEvent();
        deviceOnLineEvent.setDeviceId(name.toString());
        context.publishEvent(deviceOnLineEvent);
    }

    @EventListener
    @Async
    public void onEvent(DevicePowerCommandEvent deviceCommandEvent){
        System.err.println("收到了一个发送命令的事件"+deviceCommandEvent);
        WebSocketSession session = allClient.get(deviceCommandEvent.getDeviceId());
        if (session!=null&&session.isOpen()){
            try {
                session.sendMessage(new TextMessage(deviceCommandEvent.getCommand()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

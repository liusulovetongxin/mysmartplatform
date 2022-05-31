package com.qf.smartplatform.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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
public class MyTextMessageHandler extends TextWebSocketHandler {
    private static Map<String,WebSocketSession> allClient = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        String name = uri.substring(uri.lastIndexOf("/") + 1);
        session.getAttributes().put("name", name);
        allClient.put(name, session);
        System.err.println("MyTextMessageHandler类中的afterConnectionEstablished方法执行了-->"+name+session);
        super.afterConnectionEstablished(session);
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
        allClient.remove(session.getAttributes().get("name"));
        super.afterConnectionClosed(session, status);
    }
}

package com.qf.smartplatform.websocket;

import com.qf.smartplatform.event.DeviceRGBCommandEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
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
 * @Date 2022/6/1 17:24
 */
@Component
public class MyRGBColorHandler extends TextWebSocketHandler {
    private static Map<String, WebSocketSession> allClient = new ConcurrentHashMap<>();

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
        System.err.println("MyRGBColorHandler类中的afterConnectionEstablished方法执行了-->");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object name = session.getAttributes().get("name");
        allClient.remove(name);
        super.afterConnectionClosed(session, status);
    }

    @EventListener
    @Async
    public void onEvent(DeviceRGBCommandEvent event){
        System.err.println("收到了一个rgb命令事件");
        WebSocketSession webSocketSession = allClient.get(event.getDeviceId());
        try {
            webSocketSession.sendMessage(new TextMessage(event.getCommand()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

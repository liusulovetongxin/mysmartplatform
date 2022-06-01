package com.qf.smartplatform.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.smartplatform.pojo.SysHumiture;
import com.qf.smartplatform.service.SysHumitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
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
public class MyDH11Handler extends TextWebSocketHandler {
    private SysHumitureService sysHumitureService;

    @Autowired
    public void setSysHumitureService(SysHumitureService sysHumitureService) {
        this.sysHumitureService = sysHumitureService;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
        System.err.println("MyDH11Handler类中的afterConnectionEstablished方法执行了-->");
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.err.println(message.getPayload());
        String payloadJson = message.getPayload();
        SysHumiture sysHumiture = objectMapper.readValue(payloadJson, SysHumiture.class);
        sysHumiture.setDeviceId((String) session.getAttributes().get("name"));
        sysHumiture.setUploadTime(new Date());
        sysHumitureService.addSysHumiture(sysHumiture);
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object name = session.getAttributes().get("name");
        allClient.remove(name);
        super.afterConnectionClosed(session, status);
    }
}

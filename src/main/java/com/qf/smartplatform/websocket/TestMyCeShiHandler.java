package com.qf.smartplatform.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.websocket
 * @Description:
 * @Date 2022/6/9 20:06
 */
@Component
public class TestMyCeShiHandler extends MyBaseWebsocketHandler {
    private static Map<String, WebSocketSession> allClient = new ConcurrentHashMap<>();

    @Override
    public boolean isNeedUpdateOnline() {
        return false;
    }

    @Override
    protected Map<String, WebSocketSession> getAllClient() {
        return allClient;
    }
}

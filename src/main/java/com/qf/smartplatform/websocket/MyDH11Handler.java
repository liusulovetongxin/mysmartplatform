package com.qf.smartplatform.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.qf.smartplatform.event.DevicePowerCommandEvent;
import com.qf.smartplatform.event.SysHumitureTaskEvent;
import com.qf.smartplatform.pojo.SysHumiture;
import com.qf.smartplatform.service.SysHumitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
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
public class MyDH11Handler extends MyBaseHandler {

    private static HashMultimap<String,SysHumiture> sysHumitureHashMultimap = HashMultimap.create();
    private static Map<String,Long> heartbeatTime = new ConcurrentHashMap<>();
    private static Map<String,WebSocketSession> allClient = new ConcurrentHashMap<>();

    private static Map<String, List<SysHumiture>> allSysHumitrueMap = new HashMap<>();
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


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Object deviceId = session.getAttributes().get("name");
        System.err.println(message.getPayload());
        String payloadJson = message.getPayload();
        SysHumiture sysHumiture = objectMapper.readValue(payloadJson, SysHumiture.class);
        sysHumiture.setDeviceId((String) session.getAttributes().get("name"));
        sysHumiture.setUploadTime(new Date());
        List<SysHumiture> sysHumitures = allSysHumitrueMap.get(deviceId);
        sysHumitureHashMultimap.put((String) deviceId, sysHumiture);

//        sysHumitureService.addSysHumiture(sysHumiture);
        super.handleTextMessage(session, message);
    }
    @EventListener
    @Async
    public void onEvent(SysHumitureTaskEvent event){
        Collection<SysHumiture> values = sysHumitureHashMultimap.values();
        ArrayList<SysHumiture> sysHumitures = new ArrayList<>(values);
        sysHumitureHashMultimap.clear();

        if (sysHumitures != null && sysHumitures.size()!=0) {
            double avg = sysHumitures.stream().mapToDouble(SysHumiture::getTemperature).average().getAsDouble();
            if (avg>=30){
                System.err.println("开空调");
                getContext().publishEvent(new DevicePowerCommandEvent("1234567890", "0"));
            } else if (avg<=25) {
                System.err.println("关空调");
                getContext().publishEvent(new DevicePowerCommandEvent("1234567890", "1"));
            }
        }
        sysHumitureService.mutiAdd(sysHumitures);
//        sysHumitureHashMultimap.forEach((deviceId,data)->{
////            System.err.println(deviceId+" "+data);
//        });
    }

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

}

package com.qf.smartplatform.service.impl;

import com.qf.smartplatform.mapper.SysHumitureMapper;
import com.qf.smartplatform.pojo.SysHumiture;
import com.qf.smartplatform.service.SysHumitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/6/1 17:56
 */
@Service
public class SysHumitureServiceImpl implements SysHumitureService {
    private SysHumitureMapper sysHumitureMapper;

    @Autowired
    public void setSysHumitureMapper(SysHumitureMapper sysHumitureMapper) {
        this.sysHumitureMapper = sysHumitureMapper;
    }

    @Override
    public void addSysHumiture(SysHumiture sysHumiture) {
        sysHumitureMapper.addSysHumiture(sysHumiture);
    }

    @Override
    public Map findByTime(Date start, Date end) {
        if (start == null && end == null){
            Instant now = Instant.now();
            end= Date.from(now);
            Instant instant = now.plusSeconds(-3600);
            start = Date.from(instant);
        }
        Map result = new HashMap();
        List<SysHumiture> list = sysHumitureMapper.findByTime(start,end);
        List<Date> dateList = list.stream().map(SysHumiture::getUploadTime).collect(Collectors.toList());
        List<Double> humidityList = list.stream().map(SysHumiture::getHumidity).collect(Collectors.toList());
        List<Double> temperatureList = list.stream().map(SysHumiture::getTemperature).collect(Collectors.toList());
        result.put("times", dateList);
        result.put("temperatureList", humidityList);
        result.put("humidityList", temperatureList);
        return result;
    }

    @Override
    public void mutiAdd(Collection<SysHumiture> sysHumitures) {
        if (sysHumitures == null||sysHumitures.size()==0){
            return;
        }
        sysHumitureMapper.mutiAdd(sysHumitures);
    }
}

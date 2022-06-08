package com.qf.smartplatform.service;

import com.qf.smartplatform.pojo.SysHumiture;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service
 * @Description:
 * @Date 2022/6/1 17:56
 */

public interface SysHumitureService {
    void addSysHumiture(SysHumiture sysHumiture);

    Map findByTime(Date start, Date end);


    void mutiAdd(Collection<SysHumiture> sysHumitures);
}

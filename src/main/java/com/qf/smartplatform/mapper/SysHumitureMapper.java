package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysHumiture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/6/1 17:57
 */

public interface SysHumitureMapper {
    @Insert("insert into sys_humiture values(null,#{deviceId},#{humidity},#{temperature},#{uploadTime})")
    void addSysHumiture(SysHumiture sysHumiture);

    List<SysHumiture> findByTime(@Param("start") Date start,@Param("end") Date end);

    void mutiAdd(@Param("sysHumitures") Collection<SysHumiture> sysHumitures);
}

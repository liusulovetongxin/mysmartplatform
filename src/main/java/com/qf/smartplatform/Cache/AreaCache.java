package com.qf.smartplatform.Cache;

import com.qf.smartplatform.event.AreaChangeEvent;
import com.qf.smartplatform.mapper.SysAreaMapper;
import com.qf.smartplatform.pojo.SysArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.Cache
 * @Description:
 * @Date 2022/5/29 14:04
 */

@Component
@EnableAsync
public class AreaCache {
    private List<SysArea> areaList = new ArrayList<>();

    public  List<SysArea> getAreaList(){
            return areaList;
    }

    private SysAreaMapper sysAreaMapper;

    @Autowired
    public void setSysAreaMapper(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    @PostConstruct
    public void init(){
        List<SysArea> list = sysAreaMapper.findAll();
        areaList.clear();
        areaList.addAll(list);
    }
    @Async
    @EventListener
    public void onEvent(AreaChangeEvent areaChangeEvent){
        init();
    }
}

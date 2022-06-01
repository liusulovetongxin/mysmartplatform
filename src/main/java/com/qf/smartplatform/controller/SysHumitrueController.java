package com.qf.smartplatform.controller;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.service.SysHumitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/6/1 20:16
 */

@RestController
@RequestMapping("/humitures")
public class SysHumitrueController {
    private SysHumitureService sysHumitureService;

    @Autowired
    public void setSysHumitureService(SysHumitureService sysHumitureService) {
        this.sysHumitureService = sysHumitureService;
    }

    @GetMapping("/humitures")
    public R findByTime(Date start, Date end){
        Map result = sysHumitureService.findByTime(start, end);
        return R.setOk(result);
    }
}

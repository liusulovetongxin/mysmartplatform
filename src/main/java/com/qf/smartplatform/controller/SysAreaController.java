package com.qf.smartplatform.controller;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.pojo.SysArea;
import com.qf.smartplatform.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/27 20:53
 */

//@RestController
@RequestMapping("/area")
public class SysAreaController {
    private SysAreaService sysAreaService;

    @Autowired
    public void setSysAreaService(SysAreaService sysAreaService) {
        this.sysAreaService = sysAreaService;
    }

    @PostMapping("addArea")
    public R addArea(@RequestBody SysArea sysArea){
        sysAreaService.addArea(sysArea);
        return R.setOk();
    }
    @GetMapping("allarea")
    public R allArea(){
        List<SysArea> areaList = sysAreaService.findAll();
        return R.setOk(areaList);
    }

    @GetMapping("/area")
    public R findAreaByPage(int pageSize,int pageNum){
        PageInfo<SysArea> pageInfo = sysAreaService.findByPage(pageSize, pageNum);
        return R.setOk(pageInfo);
    }
}

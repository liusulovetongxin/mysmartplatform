package com.qf.smartplatform.controller;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.pojo.SysScene;
import com.qf.smartplatform.service.SysSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/30 16:43
 */
@RestController
@RequestMapping("/scenes")
public class SysSceneController {
    private SysSceneService sysSceneService;

    @Autowired
    public void setSysSceneService(SysSceneService sysSceneService) {
        this.sysSceneService = sysSceneService;
    }

    @PostMapping("/addscene")
    public R addScene(@RequestBody SysScene sysScene){
        sysSceneService.addScene(sysScene);
        return R.setOk();
    }
    @GetMapping("/allscenes")
    public R findAllSceneById(){
        List<SysScene> sceneList = sysSceneService.findAllSceneByUserId();
        return R.setOk(sceneList);
    }
}

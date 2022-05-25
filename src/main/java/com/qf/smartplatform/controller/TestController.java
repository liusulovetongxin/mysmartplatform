package com.qf.smartplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/25 16:07
 */

@RestController
public class TestController {

    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }
}

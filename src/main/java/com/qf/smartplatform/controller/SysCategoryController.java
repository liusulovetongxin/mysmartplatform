package com.qf.smartplatform.controller;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.pojo.SysCategory;
import com.qf.smartplatform.service.SysCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/27 12:03
 */
@RestController
@RequestMapping("/category")
public class SysCategoryController {

    private SysCategoryService sysCategoryService;

    @Autowired
    public void setSysCategoryService(SysCategoryService sysCategoryService) {
        this.sysCategoryService = sysCategoryService;
    }

    @RequestMapping("/addCategory")
    public R addCategory(@RequestBody SysCategory sysCategory){
        sysCategoryService.addCategory(sysCategory);
        return R.setOk();
    }
    @GetMapping("/categories")
    public R findByPage(int pageSize,int pageNum){
        return R.setOk(sysCategoryService.findAll(pageSize, pageNum));
    }
    @DeleteMapping("/category/{cId}")
    public R deleteById(@PathVariable Long cId){
        sysCategoryService.deleteById(cId);
        return R.setOk();
    }
}

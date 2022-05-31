package com.qf.smartplatform.controller;

import com.qf.smartplatform.dto.R;
import com.qf.smartplatform.pojo.SysCategory;
import com.qf.smartplatform.service.SysCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.controller
 * @Description:
 * @Date 2022/5/27 12:03
 */
@RestController
@RequestMapping("/categories")
public class SysCategoryController {

    private SysCategoryService sysCategoryService;

    @Autowired
    public void setSysCategoryService(SysCategoryService sysCategoryService) {
        this.sysCategoryService = sysCategoryService;
    }

    @RequestMapping("/category")
    public R addCategory(@RequestBody SysCategory sysCategory){
        sysCategoryService.addCategory(sysCategory);
        return R.setOk();
    }
    @GetMapping("/categories")
    public R findByPage(int limit,int page){
        return R.setOk(sysCategoryService.findAll(limit, page));
    }

    @DeleteMapping("/categories")
    public R deleteByIds(@RequestBody List<Long> ids,Long status){
        sysCategoryService.deleteByIds(ids,status);
        return R.setOk();
    }
    @DeleteMapping("/category/{cId}")
    public R deleteById(@PathVariable Long cId){
        sysCategoryService.deleteById(cId);
        return R.setOk();
    }
    @PostMapping("/updatecategory")
    public R updateCategory(@RequestBody SysCategory sysCategory){
        System.err.println(sysCategory);
        sysCategoryService.updateCategory(sysCategory);
        return R.setOk();
    }
}

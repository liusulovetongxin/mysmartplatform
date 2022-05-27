package com.qf.test;

import com.github.pagehelper.PageInfo;
import com.qf.smartplatform.Cache.CategoryCache;
import com.qf.smartplatform.pojo.SysCategory;
import com.qf.smartplatform.service.SysCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.test
 * @Description:
 * @Date 2022/5/27 11:58
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-dao.xml"})
@WebAppConfiguration
public class TestSysCategory {
    private CategoryCache categoryCache;

    @Autowired
    public void setCategoryCache(CategoryCache categoryCache) {
        this.categoryCache = categoryCache;
    }

    private SysCategoryService sysCategoryService;

    @Autowired
    public void setSysCategoryService(SysCategoryService sysCategoryService) {
        this.sysCategoryService = sysCategoryService;
    }

    @Test
    public void  testAdd(){
        SysCategory sysCategory = new SysCategory();
        sysCategory.setCategoryName("测试添加分类");
        sysCategoryService.addCategory(sysCategory);
    }

    @Test
    public void testCache(){
        PageInfo<SysCategory> pageInfo = sysCategoryService.findAll(2,2);
        System.err.println(pageInfo);
    }
}

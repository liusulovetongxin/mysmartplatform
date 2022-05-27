package com.qf.smartplatform.Cache;

import com.qf.smartplatform.event.CategoryChangeEvent;
import com.qf.smartplatform.mapper.SysCategoryMapper;
import com.qf.smartplatform.pojo.SysCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.Cache
 * @Description:
 * @Date 2022/5/27 15:37
 */

@EnableAsync
@Component
public class CategoryCache {
    private List<SysCategory> categoryList = new ArrayList<>();

    private List<SysCategory> allEnableList = new ArrayList<>();


    public List<SysCategory> getCategoryList(boolean isAll){
        if (!isAll){
            return allEnableList;
        }
        return categoryList;
    }

    private SysCategoryMapper sysCategoryMapper;


    @Autowired
    public void setSysCategoryMapper(SysCategoryMapper sysCategoryMapper) {
        this.sysCategoryMapper = sysCategoryMapper;
    }

    @PostConstruct
    public void init(){
        List<SysCategory> list = sysCategoryMapper.findAll();
        categoryList.clear();
        categoryList.addAll(list);
        List<SysCategory> list2 = categoryList.stream().filter(sysCategory -> sysCategory.getStatus() == 1).collect(Collectors.toList());
        allEnableList.clear();
        allEnableList.addAll(list2);
    }

    @EventListener
    @Async
    public void onEvent(CategoryChangeEvent categoryChangeEvent){
        System.err.println(Thread.currentThread().getId());
        init();
    }
}

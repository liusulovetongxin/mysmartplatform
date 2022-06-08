package com.qf.smartplatform.Cache;

import com.qf.smartplatform.event.CategoryChangeEvent;
import com.qf.smartplatform.mapper.SysCategoryMapper;
import com.qf.smartplatform.pojo.SysCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.Cache
 * @Description:
 * @Date 2022/5/27 15:37
 */

@EnableAsync(proxyTargetClass = true)
//@Component
public class CategoryCache2 {
    private List<SysCategory> categoryList = new ArrayList<>();

    private List<SysCategory> allEnableList = new ArrayList<>();

    private Map<Long,SysCategory> key2CategoryMap = new HashMap<>();
    public List<SysCategory> getCategoryList(boolean isAll){
        if (!isAll){
            return allEnableList;
        }
        return categoryList;
    }

    public SysCategory getById(Long cId){
        return key2CategoryMap.get(cId);
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
        key2CategoryMap.clear();
        key2CategoryMap
                .putAll(categoryList
                        .stream()
                        .collect(Collectors
                                        .toMap(SysCategory::getCId,
                                                sysCategory -> sysCategory)));
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

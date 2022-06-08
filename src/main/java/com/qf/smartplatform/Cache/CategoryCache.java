package com.qf.smartplatform.Cache;

import com.qf.smartplatform.event.CategoryChangeEvent;
import com.qf.smartplatform.mapper.SysCategoryMapper;
import com.qf.smartplatform.pojo.SysCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.Cache
 * @Description:
 * @Date 2022/6/7 09:28
 */
@Component
public class CategoryCache extends BaseCache<Long, SysCategory, CategoryChangeEvent>{

    private List<SysCategory> allEnableList =new ArrayList<>();

    public List<SysCategory> getAllEnableList() {
        return allEnableList;
    }
    private SysCategoryMapper sysCategoryMapper;

    @Autowired
    public void setSysCategoryMapper(SysCategoryMapper sysCategoryMapper) {
        this.sysCategoryMapper = sysCategoryMapper;
    }

    @Override
    public void initData() {
        //我就可以查询数据库加载所有的数据,然后保存起来
        List<SysCategory> categoryList1 = sysCategoryMapper.findAll();
        List<SysCategory> categoryList = getList();
        categoryList.clear();
        categoryList.addAll(categoryList1);//保存到集合中
        Map<Long, SysCategory> key2CategoryMap = getValueMap();
        key2CategoryMap.clear();//清空一下,为了防止重新加载的时候导致删除的数据仍然在里面
        key2CategoryMap.putAll(categoryList.stream().collect(Collectors.toMap(SysCategory::getCId, sysCategory -> sysCategory)));
        //只获取有效的数据
        allEnableList.clear();
        allEnableList.addAll(categoryList1.stream().filter(sysCategory -> sysCategory.getStatus() == 1).collect(Collectors.toList()));
    }

    @Override
    public void onEvent(CategoryChangeEvent event) {
        super.onEvent(event);
    }
}

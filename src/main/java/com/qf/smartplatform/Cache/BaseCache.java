package com.qf.smartplatform.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.Cache
 * @Description:
 * @Date 2022/6/6 15:55
 */
@EnableAsync(proxyTargetClass = true)
public abstract class BaseCache<K,V,E> {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }

    private List<V> allData = new ArrayList<>();
    private Map<K,V> valueMap = new HashMap<>();
    private boolean isInit;
    public void initData(){

    }
    protected List<V> getList(){
        return allData;
    }

    public List<V> getAllData() {
        if (allData.size()==0){
            initData();
        }
        return allData;
    }

    protected Map<K, V> getValueMap() {
        return valueMap;
    }

    public V get(K key){
        if (valueMap==null){
            return null;
        }
        if (valueMap.size()==0){
            initData();
        }
        return valueMap.get(key);
    }


    @EventListener
    @Async
    public void onEvent(E event){
        initData();
    }
    @EventListener
    @Async
    public void onEvent(ContextRefreshedEvent event){
        synchronized (allData){
            if (!isInit){
                initData();
                isInit = true;
            }
        }
    }
}

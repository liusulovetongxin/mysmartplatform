package com.qf.smartplatform.Cache;

//
//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖镇楼                  BUG辟易  
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  
//  

import com.qf.smartplatform.event.MenuChangeEvent;
import com.qf.smartplatform.event.RoleChangeEvent;
import com.qf.smartplatform.mapper.MenuMapper;
import com.qf.smartplatform.pojo.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Jackiechan on 2021/12/23/23:48
 *
 * @author Jackiechan
 * @version 1.0
 * @since 1.0
 */
@Component
public class MenuCache extends BaseCache<Long, SysMenu, MenuChangeEvent>{
    private MenuMapper menuMapper;


    private List<SysMenu> enableList = new ArrayList<>();
    private Map<Long, SysMenu> enableMap = new HashMap<>();

    public List<SysMenu> getEnableList() {
        return enableList;
    }

    public  SysMenu getEnableMenu(Long id) {
        return enableMap.get(id);
    }

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public void initData() {
        //menuExample.createCriteria().andEnableEqualTo("1");//只查看正常的菜单
        List<SysMenu> menuList = menuMapper.selectAll();
        List<SysMenu> list = getList();
        list.clear();
        list.addAll(menuList);
        Map<Long, SysMenu> menuMap = list.parallelStream().collect(Collectors.toMap(SysMenu::getMenuId, menu -> menu));
        Map<Long, SysMenu> valueMap = getValueMap();
        valueMap.clear();
        valueMap.putAll(menuMap);
        List<SysMenu> enable = list.parallelStream().filter(menu -> "1".equals(menu.getEnable())).collect(Collectors.toList());
        enableList.clear();
        enableList.addAll(enable);
        Map<Long, SysMenu> longMenuMap= enable.parallelStream().collect(Collectors.toMap(SysMenu::getMenuId, menu -> menu));
        enableMap.clear();
        enableMap.putAll(longMenuMap);
    }

    @Override
    public void onEvent(MenuChangeEvent event) {
        super.onEvent(event);
        getApplicationContext().publishEvent(new RoleChangeEvent());//更新完菜单需要更新角色,来确保数据一致
    }
}

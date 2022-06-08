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


import com.qf.smartplatform.event.RoleChangeEvent;
import com.qf.smartplatform.mapper.RoleMapper;
import com.qf.smartplatform.mapper.RoleMenuMapper;
import com.qf.smartplatform.pojo.SysMenu;
import com.qf.smartplatform.pojo.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * Created by Jackiechan on 2021/12/21/11:22
 *
 * @author Jackiechan
 * @version 1.0
 * @since 1.0
 */
@Component
public class RoleCache extends BaseCache<Long, SysRole, RoleChangeEvent> {
    private ThreadPoolExecutor complateFutureExecutor;

    @Autowired
    public void setComplateFutureExecutor(ThreadPoolExecutor complateFutureExecutor) {
        this.complateFutureExecutor = complateFutureExecutor;
    }
    private RoleMapper roleMapper;

    private RoleMenuMapper roleMenuMapper;

   // private MenuMapper menuMapper;

//    @Autowired
//    public void setMenuMapper(MenuMapper menuMapper) {
//        this.menuMapper = menuMapper;
//    }


    private MenuCache menuCache;

    @Autowired
    public void setMenuCache(MenuCache menuCache) {
        this.menuCache = menuCache;
    }

    @Autowired
    public void setRoleMenuMapper(RoleMenuMapper roleMenuMapper) {
        this.roleMenuMapper = roleMenuMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public void initData() {
        List<SysRole> cacheList = getList();
        List<SysRole> roleList = roleMapper.selectAll();
        //上面只是单表查询了角色,并没有查询权限,循环查询所有的权限,通过异步完成
        roleList.parallelStream().forEach(role->{//遍历每个角色
            CompletableFuture.supplyAsync(()->{//开启异步
//                RoleMenuExample example = new RoleMenuExample();
//                example.createCriteria().andRoleIdEqualTo(role.getRoleId());
//                List<RoleMenuKey> menuKeyList = roleMenuMapper.selectByExample(example);
                //先获取当前角色对应的所有菜单id
                List<Long> menuIds = roleMenuMapper.selectMenuIdByRoleId(role.getRoleId());
                return menuIds;
            },complateFutureExecutor).thenAcceptAsync(ids->{//thenAcceptAsync 在上面的操作执行完成后执行当前操作,会自动将上面的结果作为参数传递过来

                //根据ids查询所有的菜单,然后设置给角色
//                MenuExample menuExample = new MenuExample();
//                menuExample.createCriteria().andMenuIdIn(ids);
//                List<Menu> menuList = menuMapper.selectByExample(menuExample);
//                role.setMenuList(menuList);
                List<SysMenu> allData = menuCache.getAllData();
                //从缓存中获取数据,保存的就是当前角色的所有的菜单
                List<SysMenu> menuList = allData.parallelStream().filter(menu -> ids.contains(menu.getMenuId())).collect(Collectors.toList());
                //设置给当前角色
                role.setMenuList(menuList);
            },complateFutureExecutor);
        });
        //因为上面的操作是异步的,所以在数据初始化完成的时候可能菜单还没设置完,但是不影响后面的数据获取
        cacheList.clear();
        cacheList.addAll(roleList);
        Map<Long, SysRole> valueMap = getValueMap();
        valueMap.clear();
        //将数据转换为map格式,方便查询单个数据
        valueMap.putAll(cacheList.stream().collect(Collectors.toMap(SysRole::getRoleId, role -> role)));
    }


    @Override
    public void onEvent(RoleChangeEvent event) {
        super.onEvent(event);
    }
}

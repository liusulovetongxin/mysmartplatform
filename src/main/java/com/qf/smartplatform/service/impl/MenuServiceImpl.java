package com.qf.smartplatform.service.impl;

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


import com.qf.smartplatform.Cache.MenuCache;
import com.qf.smartplatform.Cache.RoleCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.exceptions.QueryException;
import com.qf.smartplatform.mapper.MenuMapper;
import com.qf.smartplatform.pojo.SysMenu;
import com.qf.smartplatform.pojo.SysRole;
import com.qf.smartplatform.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Jackiechan on 2021/12/26/20:34
 *
 * @author Jackiechan
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }


    private RoleCache roleCache;


    @Autowired
    public void setRoleCache(RoleCache roleCache) {
        this.roleCache = roleCache;
    }

    private MenuMapper menuMapper;

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    private MenuCache menuCache;

    @Autowired
    public void setMenuCache(MenuCache menuCache) {
        this.menuCache = menuCache;
    }

//    @Override
//    public void addMenu(Menu menu) {
//        Assert.isTrue(menu!=null&&!menu.isEmpty(CheckType.ADD), ()->{
//            throw new AddDataException("参数为空", ResultCode.DATA_NULL);
//        });
//        //判断名字或者地址是否已经存在
//        MenuExample menuExample = new MenuExample();
//        menuExample.createCriteria().andMenuNameEqualTo(menu.getMenuName());
//        List<Menu> menuList = menuMapper.selectByExample(menuExample);
//        //根据url查询地址
//        List<Menu> menuListByUrl = null;
//        if (StringUtils.hasText(menu.getUrl())) {
//            menuExample.clear();
//            menuExample.createCriteria().andUrlEqualTo(menu.getUrl());
//            menuListByUrl=menuMapper.selectByExample(menuExample);
//        }
//
//        Assert.isTrue((menuList==null||menuList.size()==0)&&(menuListByUrl==null||menuListByUrl.size()==0),()->{
//            throw new AddDataException("数据已经存在",ResultCode.DATA_ALREADY_EXIST);
//        });
//        MyBaseUser user = SecurityUtils.getUserInfo(false);
//
//        menu.setCreateTime(new Date());
//        menu.setCreateBy(user.getUsername());
//        menuMapper.insertSelective(menu);
//        //发布事件,更新缓存
//        context.publishEvent(new MenuChangeEvent());
//    }
//
//    @Override
//    public void updateMenu(Menu menu) {
//        Assert.isTrue(menu!=null&&!menu.isEmpty(CheckType.UPDATE), ()->{
//            throw new AddDataException("参数为空",ResultCode.DATA_NULL);
//        });
//        MyBaseUser user = SecurityUtils.getUserInfo(false);
//
//        //设置更新操作的用户信息
//        menu.setUpdateBy(user.getUsername());
//        menu.setUpdateTime(new Date());
//        int result = menuMapper.updateByPrimaryKeySelective(menu);
//        //数据不存在
//        Assert.isTrue(result>0,()->{
//            throw new UpdateException("数据不存在",ResultCode.DATA_NOT_EXIST);
//        });
//        //发布事件,更新缓存
//        context.publishEvent(new MenuChangeEvent());
//    }

    @Override
    public List<SysMenu> findAllMenus() {
    //    MenuExample menuExample = new MenuExample();
    //    List<Menu> menuList = menuMapper.selectByExample(menuExample);
        List<SysMenu> menuList = menuCache.getAllData();
        return menuList;
    }

    @Override
    public List<SysMenu> findAllEnableMenus() {
        //从缓存中查询数据
        List<SysMenu> menuList = menuCache.getEnableList();
        return menuList;
    }

    @Override
    public List<SysMenu> findAllMenusByRoleId(Long roleId) {
        Assert.notNull(roleId,()->{
            //id必须传递
            throw new QueryException("主键为空",ResultCode.ID_NOTALLOWED);
        });
        //通过缓存获取到角色信息
        SysRole role = roleCache.get(roleId);
        Assert.notNull(role,()->{
            //数据不存在
            throw new QueryException("数据不存在", ResultCode.DATA_NOT_EXIST);
        });
        //获取到角色的菜单信息
        List<SysMenu> menuList = role.getMenuList();
        return menuList;
    }

//    @Override
//    public void deleteMenuByIds(List<Long> ids) {
//        MyBaseUser user = SecurityUtils.getUserInfo(false);
//
//
//        MenuExample example = new MenuExample();
//        example.createCriteria().andMenuIdIn(ids);
//        Menu menu = new Menu();
//        menu.setEnable("0");//删除是设置为禁用
//        menu.setUpdateTime(new Date());
//        menu.setUpdateBy(user.getUsername());
//        int result = menuMapper.updateByExampleSelective(menu, example);
//        Assert.isTrue(result>0,()->{
//            throw new DeleteDataException("数据不存在",ResultCode.DATA_NOT_EXIST);
//        });
//        context.publishEvent(new MenuChangeEvent());
//    }

//    @Override
//    public Menu getMenuById(Long id) {
////        MenuExample menuExample = new MenuExample();
////        menuExample.createCriteria().andMenuIdEqualTo(id);
////        List<Menu> menus = menuMapper.selectByExample(menuExample);
//        Menu menu = menuCache.get(id);
//        return menu;
//    }
}

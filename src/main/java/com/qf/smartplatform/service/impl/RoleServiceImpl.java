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


import com.qf.smartplatform.Cache.RoleCache;
import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.exceptions.QueryException;
import com.qf.smartplatform.mapper.RoleMenuMapper;
import com.qf.smartplatform.mapper.SysRoleMapper;
import com.qf.smartplatform.mapper.UserRoleMapper;
import com.qf.smartplatform.pojo.SysRole;
import com.qf.smartplatform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jackiechan on 2021/12/23/11:13
 *
 * @author Jackiechan
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private SysRoleMapper roleMapper;

    private RoleCache roleCache;

    private ApplicationContext context;

    private RoleMenuMapper roleMenuMapper;

    private UserRoleMapper userRoleMapper;


    public void setRoleMenuMapper(RoleMenuMapper roleMenuMapper) {
        this.roleMenuMapper = roleMenuMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void setRoleCache(RoleCache roleCache) {
        this.roleCache = roleCache;
    }

    @Autowired
    public void setRoleMapper(SysRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<SysRole> findRolesByUserId(Integer uid) {
        Assert.notNull(uid, () -> {
            throw new QueryException("主键为空", ResultCode.ID_NOTALLOWED);
        });
        //先查询用户的角色
        List<Long> roleIds = userRoleMapper.selectRoleIdByUserId(Long.valueOf(uid));
        //根据角色id查询数据
        List<SysRole> roleList = roleCache.getAllData();
        //筛选在对应id范围的数据
        List<SysRole> result = roleList.stream().filter(role -> roleIds.contains(role.getRoleId())).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<SysRole> findAllRoles() {

        // List<Role> roleList = roleMapper.selectByExample(new RoleExample());
        List<SysRole> roleList = roleCache.getAllData();
        return roleList;
    }

//    @Override
//    public PageInfo findRolesByPage(int pageNum, int pageSize, String roleName, Short status) {
//        //TODO 通过AOP进行用户的判断
//        PageHelper.startPage(pageNum, pageSize);
//        List<SysRole> roleList = roleCache.getAllData();
//        Stream<SysRole> stream = roleList.stream();
//        if (StringUtils.hasText(roleName)) {
//            stream = stream.filter(role -> role.getRoleName().contains(roleName));
//        }
//        if (!ObjectUtils.isEmpty(status)) {
//            stream = stream.filter(role -> role.getStatus().equals(status));
//        }
//
//        List<SysRole> matchList = stream.collect(Collectors.toList());//收集符合条件的数据
//        //分页处理
//        List<SysRole> result = matchList.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());//
//
//        PageInfo<SysRole> pageInfo = new PageInfo<>(result);
//        return pageInfo;
//    }

//    @Override
//    public List<SysRole> findByNameLike(String roleName) {
//        Assert.hasText(roleName, () -> {
//            throw new QueryException("参数为空",ResultCode.DATA_NULL);
//        });
////        RoleExample roleExample = new RoleExample();
////        roleExample.createCriteria().andRoleNameLike(roleName);
////        List<Role> roleList = roleMapper.selectByExample(roleExample);
//        List<SysRole> roleList = roleCache.getAllData();
//        List<SysRole> result = roleList.stream().filter(role -> role.getRoleName().contains(roleName)).collect(Collectors.toList());
//        return result;
//    }

//    @Override
//    public List<SysRole> findByCreater(String creater) {
//        //必须传递创建者的名字过来
//        Assert.hasText(creater, () -> {
//            throw new QueryException("参数为空",ResultCode.DATA_NULL);
//        });
//        RoleExample roleExample = new RoleExample();
//        roleExample.createCriteria().andCreateByEqualTo(creater);
//        List<Role> roleList = roleMapper.selectByExample(roleExample);
//        return roleList;
//    }
//
//    @Override
//    public void addRole(Role role) {
//
//        Assert.isTrue(!role.isEmpty(CheckType.ADD), () -> {
//            throw new AddDataException("参数为空",ResultCode.DATA_NULL);
//        });
//        //根据业务需求决定是否允许在添加角色时候顺便添加菜单,如果允许需要先添加角色,拿到id后再添加菜单
//        RoleExample roleExample = new RoleExample();
//        roleExample.createCriteria().andRoleNameEqualTo(role.getRoleName());
//        List<Role> roleList = roleMapper.selectByExample(roleExample);//先查询有没有当前
//        Assert.isNull(roleList == null || roleList.size() == 0, () -> {
//            throw new AddDataException("数据已经存在",ResultCode.DATA_ALREADY_EXIST);
//        });
//
//        roleMapper.insertSelectiveReturnId(role);//添加数据
//        List<Menu> menuList = role.getMenuList();
//        if (menuList != null && menuList.size() > 0) {
//            List<RoleMenuKey> roleMenuKeyList = menuList.stream().map(menu -> {
//                RoleMenuKey roleMenuKey = new RoleMenuKey();
//                roleMenuKey.setRoleId(role.getRoleId());
//                roleMenuKey.setMenuId(menu.getMenuId());
//                return roleMenuKey;
//            }).collect(Collectors.toList());
//            roleMenuKeyList.parallelStream().forEach(roleMenu -> roleMenuMapper.insert(roleMenu));//添加对应的menu
//        }
//        //刷新缓存
//        context.publishEvent(new RoleChangeEvent());
//    }
//
//    @Override
//    public void updateRole(Role role) {
//        //角色权限只能更新 不能单独新增,所以都是先删除 后添加
//        //刷新缓存
//        Assert.isTrue(!role.isEmpty(CheckType.UPDATE), () -> {
//            throw new AddDataException("参数为空",ResultCode.DATA_NULL);
//        });
//        roleMapper.updateByPrimaryKeySelective(role);//更新role
//
//        RoleMenuExample roleMenuExample = new RoleMenuExample();
//        roleMenuExample.createCriteria().andRoleIdEqualTo(role.getRoleId());
//        roleMenuMapper.deleteByExample(roleMenuExample);//先删除原先的菜单
//        List<Menu> menuList = role.getMenuList();//获取新传递的菜单
//        if (menuList != null && menuList.size() > 0) {//更新新的菜单
//            List<RoleMenuKey> roleMenuKeyList = menuList.stream().map(menu -> {
//                RoleMenuKey roleMenuKey = new RoleMenuKey();
//                roleMenuKey.setRoleId(role.getRoleId());
//                roleMenuKey.setMenuId(menu.getMenuId());
//                return roleMenuKey;
//            }).collect(Collectors.toList());
//            roleMenuKeyList.parallelStream().forEach(roleMenu -> roleMenuMapper.insert(roleMenu));//添加对应的menu
//        }
//        context.publishEvent(new RoleChangeEvent());
//    }
//
//    @Override
//    public void deleteRoleByIds(List<Long> ids) {
//        //删除觉得时候必须保证当前角色下没有用户,否则不可以删除,不过可以先删除没有用户的角色,有用户的不删除
//        List<Long> haveUserIds = ids.stream().filter(id -> {//先查找一下有用户的id数据
//            UserRoleExample userRoleExample = new UserRoleExample();
//            userRoleExample.createCriteria().andRoleIdEqualTo(id);
//            return userRoleMapper.countByExample(userRoleExample) > 0;
//        }).collect(Collectors.toList());
//        ids.removeAll(haveUserIds);//从所有的里面移除有用户的id,剩下的就是没有用户的
//
//
//        RoleExample roleExample = new RoleExample();
//        roleExample.createCriteria().andRoleIdIn(ids);
//        roleMapper.deleteByExample(roleExample);//删除没有用户的数据
//        //刷新缓存
//        context.publishEvent(new RoleChangeEvent());
//        Assert.isNull(haveUserIds == null || haveUserIds.size() == 0, () -> {
//            throw new DeleteDataException("数据不为空",ResultCode.DATA_NOT_NULL);
//        });
//    }
}

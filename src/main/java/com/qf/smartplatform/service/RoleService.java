package com.qf.smartplatform.service;

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


import com.qf.smartplatform.pojo.SysRole;

import java.util.List;

/**
 * Created by Jackiechan on 2021/10/17/18:33
 *
 * @author Jackiechan
 * @version 1.0
 * @since 1.0
 */
public interface RoleService {
    List<SysRole> findRolesByUserId(Integer uid);

    //查询所有
    List<SysRole> findAllRoles();

//    //分页
//    PageInfo findRolesByPage(int pageNum, int pageSize, String roleName, Short status);
//
//    //按照名字搜索
//    List<SysRole> findByNameLike(String roleName);
//
//    //根据添加者搜索
//    List<SysRole> findByCreater(String creater);
//
//    void addRole(SysRole role);
//
//    void updateRole(SysRole role);
//
//    void deleteRoleByIds(List<Long> ids);
}

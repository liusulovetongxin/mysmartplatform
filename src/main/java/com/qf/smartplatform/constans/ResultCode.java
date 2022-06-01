package com.qf.smartplatform.constans;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.constans
 * @Description:
 * @Date 2022/5/25 17:14
 */

public interface ResultCode {
    int SUCCESS = 1;//成功
    int ERROR = 0;//未知失败
    int DATA_NULL = 2;//数据为空
    int ID_NOTALLOWED = 3;//关键数据不合法
    int NOT_LOGIN = 401;//没有登录
    int USERNAME_PASSWORD_NULL =4;//用户名是空的
    int USERNAME_PASSWORD_ERROR =5;//用户名密码错误
    int DATA_EXIST = 6;//数据已经存在

    int CATEGORY_NOT_EXIST = 7;
    int DEVICE_NOT_EXIST = 8;
    int DATA_ALREADY_EXIST = 9;// 场景已经存在
    int SCENE_NOT_EXIST = 10;// 场景不存在
    int DEVICE_ALREADY_BIND = 11;
    int DEVICE_STATUS_NOT_MATCH = 12;
    int DEVICE_COMMAND_NOT_SUPPORT = 13;
}

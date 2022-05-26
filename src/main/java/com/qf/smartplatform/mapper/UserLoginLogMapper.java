package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysLogininfor;
import org.apache.ibatis.annotations.Insert;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/26 17:16
 */

public interface UserLoginLogMapper {
    @Insert("insert into sys_logininfor values(null,#{loginName},#{ipaddr},#{loginLocation},#{browser},#{os},#{status},#{msg},#{loginTime})")
    void addUserLoginLog(SysLogininfor sysLogininfor);
}

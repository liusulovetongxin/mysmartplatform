package com.qf.smartplatform.mapper;

import com.qf.smartplatform.dto.SysDeviceDto;
import com.qf.smartplatform.pojo.SysDevice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/30 11:48
 */

public interface SysDeviceMapper {
    @Insert("insert into sys_device(device_id,device_name,category_id) values(#{deviceId},#{deviceName},#{categoryId})")
    void addDevice(SysDeviceDto sysDeviceDto);
    @Select("select * from sys_device where device_id = #{deviceId}")
    SysDevice findByDeviceId(@Param("deviceId") String deviceId);
    @Update("update sys_device set status =1 where device_id = #{diviceId}")
    int update2Sell(String deviceId);
    @Update("UPDATE sys_device SET status=2, bind_user_id=#{bindUserId} ,scene_id =#{sceneId},bind_time=#{bindTime} WHERE device_id =#{deviceId}")
    int bindDevice(SysDevice device);

    @Select("select * from sys_device where bind_user_id = #{uId}")
    List<SysDevice> findAllDeviceByUserId(@Param("uId") Long uId);
}

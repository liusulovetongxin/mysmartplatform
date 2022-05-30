package com.qf.smartplatform.mapper;

import com.qf.smartplatform.pojo.SysScene;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.mapper
 * @Description:
 * @Date 2022/5/30 14:14
 */

public interface SysSceneMapper {

    @Insert("insert into sys_scene(scene_name,create_by) values(#{sceneName},#{createBy})")
    void addScene(SysScene sysScene);

    @Select("select * from sys_scene where create_by = #{userId}")
    List<SysScene> findByUid(@Param("userId") Long userId);
}

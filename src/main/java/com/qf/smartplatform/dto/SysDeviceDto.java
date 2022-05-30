package com.qf.smartplatform.dto;

import com.qf.smartplatform.pojo.CheckNull;
import com.qf.smartplatform.pojo.CheckType;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.dto
 * @Description:
 * @Date 2022/5/30 11:30
 */
@Data
@ToString
public class SysDeviceDto implements CheckNull {
    private String deviceId;
    private String deviceName;
    private Long categoryId;


    @Override
    public boolean isEmpty(CheckType type) {
        switch (type){
            case ADD:
                return !StringUtils.hasText(deviceId)||!StringUtils.hasText(deviceName)|| ObjectUtils.isEmpty(categoryId);
        }
        return false;
    }
}


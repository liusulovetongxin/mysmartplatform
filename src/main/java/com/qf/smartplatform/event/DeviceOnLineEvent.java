package com.qf.smartplatform.event;

import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.event
 * @Description:
 * @Date 2022/6/1 16:54
 */

@Data
@ToString
public class DeviceOnLineEvent {
    private String deviceId;
    private String ip;
}

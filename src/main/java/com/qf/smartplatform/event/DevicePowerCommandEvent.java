package com.qf.smartplatform.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.event
 * @Description:
 * @Date 2022/6/1 10:58
 */

@Data
@ToString
@AllArgsConstructor
public class DevicePowerCommandEvent {
    private String deviceId;
    private String  command;
}

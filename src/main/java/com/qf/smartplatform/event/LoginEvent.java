package com.qf.smartplatform.event;

import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.event
 * @Description:
 * @Date 2022/5/26 11:30
 */
@Data
@ToString
public class LoginEvent {

    private LoginType type;

    private String username;

    public LoginEvent(LoginType type, String username) {
        this.type = type;
        this.username = username;
    }

    public enum LoginType{
        SUCCESS,FAIL
    }

}

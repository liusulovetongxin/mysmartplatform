package com.qf.smartplatform.dto;

import com.qf.smartplatform.pojo.CheckNull;
import com.qf.smartplatform.pojo.CheckType;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.dto
 * @Description:
 * @Date 2022/5/25 17:26
 */
@Data
public class UserDto implements CheckNull {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Long sex;
    private String avator;
    private String info;

    @Override
    public boolean isEmpty(CheckType type) {
        switch (type){
            case ADD:
                 return !StringUtils.hasText(username)
                    || !StringUtils.hasText(password)
                    || !StringUtils.hasText(name)
                    || !StringUtils.hasText(phone)
                    || !StringUtils.hasText(email)
                    || (ObjectUtils.isEmpty(sex)||(!sex.equals(1L)&&!sex.equals(2L)));
        }
        return false;
    }
}

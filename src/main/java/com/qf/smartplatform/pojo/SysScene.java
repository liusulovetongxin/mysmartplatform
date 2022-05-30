package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@ToString
public class SysScene implements CheckNull {

  private Long sceneId;
  private String sceneName;
  private Long createBy;
  private java.util.Date createTime;
  private Long status;

  @Override
  public boolean isEmpty(CheckType type) {
    switch (type){
      case ADD:
        return !StringUtils.hasText(sceneName);
    }
    return CheckNull.super.isEmpty(type);
  }
}

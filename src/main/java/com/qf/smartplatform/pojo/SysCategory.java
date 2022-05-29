package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Data
@ToString
public class SysCategory implements CheckNull {

  private Long cId;
  private String categoryName;
  private String txCommand;
  private String rxCommand;
  private String commandName;
  private Long status;
  private java.util.Date createTime;
  private String createBy;
  private java.util.Date updateTime;
  private String updateBy;


  @Override
  public boolean isEmpty(CheckType type) {
    switch (type){
      case ADD:
        return !StringUtils.hasText(categoryName);
      case UPDATE:
        return ObjectUtils.isEmpty(cId)||!StringUtils.hasText(categoryName);
    }
    return false;
  }
}

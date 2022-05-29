package com.qf.smartplatform.pojo;

import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Data
public class SysArea implements CheckNull {

  private Long areaId;
  private String areaName;
  private Long parentId;
  private Long level;


  @Override
  public boolean isEmpty(CheckType type) {
    switch (type){
      case ADD:
        return ObjectUtils.isEmpty(areaId)||!StringUtils.hasText(areaName)|| ObjectUtils.isEmpty(parentId)||ObjectUtils.isEmpty(level);
    }
    return false;
  }
}

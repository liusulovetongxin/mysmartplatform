package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysMenu {

  private Long menuId;
  private String menuName;
  private Long parentId;
  private Long orderNum;
  private String url;
  private String target;
  private String menuType;
  private String visible;
  private String enable;
  private String isRefresh;
  private String perms;
  private String icon;
  private String createBy;
  private java.util.Date createTime;
  private String updateBy;
  private java.util.Date updateTime;
  private String remark;

}

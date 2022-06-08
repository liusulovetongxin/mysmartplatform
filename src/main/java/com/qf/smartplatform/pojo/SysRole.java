package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SysRole {

  private Long roleId;
  private String roleName;
  private String roleKey;
  private Long roleSort;
  private String dataScope;
  private String status;
  private String createBy;
  private java.util.Date createTime;
  private String updateBy;
  private java.util.Date updateTime;
  private String remark;
  private List<SysMenu> menuList;


}

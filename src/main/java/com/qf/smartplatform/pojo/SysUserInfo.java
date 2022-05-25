package com.qf.smartplatform.pojo;

import lombok.Data;

@Data
public class SysUserInfo {

  private Long uId;
  private String username;
  private String password;
  private String pwdSalt;
  private String name;
  private String phone;
  private String email;
  private Long sex;
  private String avator;
  private String info;
  private Long type;
  private Long status;
  private java.util.Date currentLogin;
  private java.util.Date lastLogin;
  private String currentLoginIp;
  private String lastLoginIp;
  private java.util.Date createTime;
  private String createBy;
  private java.util.Date updateTime;
  private String updateBy;
  private String remark;




}

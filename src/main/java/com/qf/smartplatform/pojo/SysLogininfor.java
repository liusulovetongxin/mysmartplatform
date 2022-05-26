package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysLogininfor {

  private Long infoId;
  private String loginName;
  private String ipaddr;
  private String loginLocation;
  private String browser;
  private String os;
  private String status;
  private String msg;
  private java.util.Date loginTime;



}

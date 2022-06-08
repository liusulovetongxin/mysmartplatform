package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysOperLog {

  private Long operId;
  private String title;
  private Long businessType;
  private String method;
  private String requestMethod;
  private Long operatorType;
  private String operName;
  private String operUrl;
  private String operIp;
  private String operLocation;
  private String operParam;
  private String jsonResult;
  private Long status;
  private String errorMsg;
  private java.util.Date operTime;




}

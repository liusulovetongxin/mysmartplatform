package com.qf.smartplatform.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysDevice {

  private String deviceId;
  private String deviceName;
  private Long categoryId;
  private Long bindUserId;
  private Long sceneId;
  private java.util.Date bindTime;
  private Long isOnline;
  private java.util.Date connectTime;
  private java.util.Date lostConnectTime;
  private java.util.Date lastControlTime;
  private String currentConnectIp;
  private String connectLocation;
  private Long status;


}

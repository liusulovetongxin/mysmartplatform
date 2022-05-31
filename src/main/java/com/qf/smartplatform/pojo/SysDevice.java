package com.qf.smartplatform.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private java.util.Date bindTime;
  private Long isOnline;
  private java.util.Date connectTime;
  private java.util.Date lostConnectTime;
  private java.util.Date lastControlTime;
  private String currentConnectIp;
  private String connectLocation;
  private Long status;

  private SysCategory category;


}

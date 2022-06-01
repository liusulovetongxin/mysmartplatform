package com.qf.smartplatform.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysHumiture {

  private Long id;
  private String deviceId;
  private Double humidity;
  private Double temperature;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
  private java.util.Date uploadTime;

}

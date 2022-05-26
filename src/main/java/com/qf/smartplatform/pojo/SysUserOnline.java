package com.qf.smartplatform.pojo;


public class SysUserOnline {

  private String sessionId;
  private String loginName;
  private String ipaddr;
  private String loginLocation;
  private String browser;
  private String os;
  private String status;
  private java.util.Date startTimestamp;
  private java.util.Date lastAccessTime;
  private Long expireTime;


  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }


  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }


  public String getIpaddr() {
    return ipaddr;
  }

  public void setIpaddr(String ipaddr) {
    this.ipaddr = ipaddr;
  }


  public String getLoginLocation() {
    return loginLocation;
  }

  public void setLoginLocation(String loginLocation) {
    this.loginLocation = loginLocation;
  }


  public String getBrowser() {
    return browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }


  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public java.util.Date getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(java.util.Date startTimestamp) {
    this.startTimestamp = startTimestamp;
  }


  public java.util.Date getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(java.util.Date lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }


  public Long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

}

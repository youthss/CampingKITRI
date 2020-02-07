package com.heun.trip.domain;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LoginLog {
  int no;
  String url;
  String startTime;
  String endTime;
  String sumTime;
  String ip;
  String email;
  String state;
  
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
  private Date createdDate;
  
  @Override
  public String toString() {
    return "LoginLog [no=" + no + ", URL=" + url + ", startTime=" + startTime + ", endTime="
        + endTime + ", sumTime=" + sumTime + ", ip=" + ip + ", email=" + email + ", state=" + state
        + ", createdDate=" + createdDate + "]";
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getSumTime() {
    return sumTime;
  }

  public void setSumTime(String sumTime) {
    this.sumTime = sumTime;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  
}

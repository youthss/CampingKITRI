package com.camping.app.domain;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Riw { // 일반회원이나 호스트가 관리자에게 문의하는 게시판 도메인
  private int no;
  private int userNo;
  private String  contents;
  private String grd;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date createdDate;
  private String reply;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date replyDate;
  private String name;
  private String userPhoto;
  private String hostphoto;
  private String hostname;
  private int roomNo;
  private String roomPhoto;
  private String roomName;


  public String getRoomPhoto() {
    return roomPhoto;
  }
  public void setRoomPhoto(String roomPhoto) {
    this.roomPhoto = roomPhoto;
  }
  public String getRoomName() {
    return roomName;
  }
  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
 
  public String getHostphoto() {
    return hostphoto;
  }
  public void setHostphoto(String hostphoto) {
    this.hostphoto = hostphoto;
  }
  public String getHostname() {
    return hostname;
  }
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  public String getGrd() {
    return grd;
  }
  public void setGrd(String grd) {
    this.grd = grd;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public String getReply() {
    return reply;
  }
  public void setReply(String reply) {
    this.reply = reply;
  }
  public Date getReplyDate() {
    return replyDate;
  }
  public void setReplyDate(Date replyDate) {
    this.replyDate = replyDate;
  }
  public int getRoomNo() {
    return roomNo;
  }
  public void setRoomNo(int roomNo) {
    this.roomNo = roomNo;
  }
  

  public String getUserPhoto() {
    return userPhoto;
  }
  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }
  @Override
  public String toString() {
    return "Riw [no=" + no + ", userNo=" + userNo + ", userPhoto=" + userPhoto + ", contents="
        + contents + ", grd=" + grd + ", createdDate=" + createdDate + ", reply=" + reply
        + ", replyDate=" + replyDate + ", name=" + name + ", hostphoto=" + hostphoto + ", hostname="
        + hostname + ", roomNo=" + roomNo + ", roomPhoto=" + roomPhoto + ", roomName=" + roomName
        + "]";
  }

}

package com.camping.kitri.domain;
import java.io.Serializable;
import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HostQna implements Cloneable, Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private int userNo;
  private int revNo;
  private String content;
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
  private Date createdDate;
  private String photo;
  private String name;
  private int authNo;
  private String auth;
  
  private String roomAddr;
  private String roomName;
  private String roomPhoto;
  private int roomNo;
  
  private String userPhoto;
  private String userName;
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd", timezone="Asia/Seoul")
  private Date checkIn;
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd", timezone="Asia/Seoul")
  private Date checkOut;
  private int Charge;
  
  @Override
  public String toString() {
    return "HostQna [no=" + no + ", userNo=" + userNo + ", revNo=" + revNo + ", content=" + content
        + ", createdDate=" + createdDate + ", photo=" + photo + ", name=" + name + ", authNo="
        + authNo + ", auth=" + auth + ", roomAddr=" + roomAddr + ", roomName=" + roomName
        + ", roomPhoto=" + roomPhoto + ", roomNo=" + roomNo + ", userPhoto=" + userPhoto
        + ", userName=" + userName + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", Charge="
        + Charge + "]";
  }
  
  public String getUserPhoto() {
    return userPhoto;
  }
  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public Date getCheckIn() {
    return checkIn;
  }
  public void setCheckIn(Date checkIn) {
    this.checkIn = checkIn;
  }
  public Date getCheckOut() {
    return checkOut;
  }
  public void setCheckOut(Date checkOut) {
    this.checkOut = checkOut;
  }
  public int getCharge() {
    return Charge;
  }
  public void setCharge(int charge) {
    Charge = charge;
  }
  public int getRoomNo() {
    return roomNo;
  }
  public void setRoomNo(int roomNo) {
    this.roomNo = roomNo;
  }
  public String getRoomAddr() {
    return roomAddr;
  }
  public void setRoomAddr(String roomAddr) {
    this.roomAddr = roomAddr;
  }
  public String getRoomName() {
    return roomName;
  }
  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }
  public String getRoomPhoto() {
    return roomPhoto;
  }
  public void setRoomPhoto(String roomPhoto) {
    this.roomPhoto = roomPhoto;
  }
  public int getRevNo() {
    return revNo;
  }
  public void setRevNo(int revNo) {
    this.revNo = revNo;
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
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public String getPhoto() {
    return photo;
  }
  public void setPhoto(String photo) {
    this.photo = photo;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getAuthNo() {
    return authNo;
  }
  public void setAuthNo(int authNo) {
    this.authNo = authNo;
  }
  public String getAuth() {
    return auth;
  }
  public void setAuth(String auth) {
    this.auth = auth;
  }
  
  
}

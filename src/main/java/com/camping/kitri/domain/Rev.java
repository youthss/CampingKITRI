package com.camping.kitri.domain;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Rev {
  private int no;
  private int userNo;
  private int stusNo;
  private int rmsNo;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date checkIn;

  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date checkOut;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date createdDate;
  private String revStus;
  private int revPerson;
  private int revUpdate;
  private String revReason;
  private int revCharge;
  private int revDelete;
  
  private String guestName;
  
  private String rmsName;
  private String address;
  private String thumbnail;
  private String status;
  private String merchantUid;
  private String impUid;
  
  private boolean count;
  
  
  
  public String getMerchantUid() {
    return merchantUid;
  }
  public void setMerchantUid(String merchantUid) {
    this.merchantUid = merchantUid;
  }
  public String getImpUid() {
    return impUid;
  }
  public void setImpUid(String impUid) {
    this.impUid = impUid;
  }
  public String getGuestName() {
    return guestName;
  }
  public void setGuestName(String guestName) {
    this.guestName = guestName;
  }
  public int getRevDelete() {
    return revDelete;
  }
  public void setRevDelete(int revDelete) {
    this.revDelete = revDelete;
  }
  public int getRevCharge() {
    return revCharge;
  }
  public void setRevCharge(int revCharge) {
    this.revCharge = revCharge;
  }
  public int getRevUpdate() {
    return revUpdate;
  }
  public void setRevUpdate(int revUpdate) {
    this.revUpdate = revUpdate;
  }
  public String getRevReason() {
    return revReason;
  }
  public void setRevReason(String revReason) {
    this.revReason = revReason;
  }
  public boolean isCount() {
    return count;
  }
  public void setCount(boolean count) {
    this.count = count;
  }
  public String getRmsName() {
    return rmsName;
  }
  public void setRmsName(String rmsName) {
    this.rmsName = rmsName;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getThumbnail() {
    return thumbnail;
  }
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
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
  public int getStusNo() {
    return stusNo;
  }
  public void setStusNo(int stusNo) {
    this.stusNo = stusNo;
  }
  public int getRmsNo() {
    return rmsNo;
  }
  public void setRmsNo(int rmsNo) {
    this.rmsNo = rmsNo;
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
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public String getRevStus() {
    return revStus;
  }
  public void setRevStus(String revStus) {
    this.revStus = revStus;
  }
  public int getRevPerson() {
    return revPerson;
  }
  public void setRevPerson(int revPerson) {
    this.revPerson = revPerson;
  }
  @Override
  public String toString() {
    return "Rev [no=" + no + ", userNo=" + userNo + ", stusNo=" + stusNo + ", rmsNo=" + rmsNo
        + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", createdDate=" + createdDate
        + ", revStus=" + revStus + ", revPerson=" + revPerson + ", revUpdate=" + revUpdate
        + ", revReason=" + revReason + ", revCharge=" + revCharge + ", revDelete=" + revDelete
        + ", guestName=" + guestName + ", rmsName=" + rmsName + ", address=" + address
        + ", thumbnail=" + thumbnail + ", status=" + status + ", merchantUid=" + merchantUid
        + ", impUid=" + impUid + ", count=" + count + "]";
  }

}

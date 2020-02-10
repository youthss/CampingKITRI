package com.camping.app.domain;
 
public class RoomFile {
  private int no;
  private int RoomNo;
  private String name;
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public int getRoomNo() {
    return RoomNo;
  }
  public void setRoomNo(int roomNo) {
    RoomNo = roomNo;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  @Override
  public String toString() {
    return "RoomFile [no=" + no + ", RoomNo=" + RoomNo + ", path=" + name + "]";
  }
}

package com.heun.trip.domain;

public class Convenience {

  private int no;
  private String name;
  private int roomNo;
  
  public int getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(int roomNo) {
    this.roomNo = roomNo;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Convenience [no=" + no + ", name=" + name + ", roomNo=" + roomNo + "]";
  }

}

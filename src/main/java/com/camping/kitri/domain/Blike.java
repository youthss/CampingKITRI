package com.camping.kitri.domain;
 
public class Blike {
  private int blikeNo;
  private int blikeCheck;
  private int boardNo;
  private int userNo;
  
  public int getBlikeNo() {
    return blikeNo;
  }
  public void setBlikeNo(int blikeNo) {
    this.blikeNo = blikeNo;
  }
  public int getBlikeCheck() {
    return blikeCheck;
  }
  public void setBlikeCheck(int blikeCheck) {
    this.blikeCheck = blikeCheck;
  }
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  
  @Override
  public String toString() {
    return "Blike [blikeNo=" + blikeNo + ", blikeCheck=" + blikeCheck + ", boardNo=" + boardNo
        + ", userNo=" + userNo + "]";
  }
  
}

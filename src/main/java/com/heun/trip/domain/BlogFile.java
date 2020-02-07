package com.heun.trip.domain;

public class BlogFile {
  
  private int no;
  private int blogNo;
  private String file;
  
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public int getBlogNo() {
    return blogNo;
  }
  public void setBlogNo(int blogNo) {
    this.blogNo = blogNo;
  }
  public String getFile() {
    return file;
  }
  public void setFile(String file) {
    this.file = file;
  }
  @Override
  public String toString() {
    return "BlogFile [no=" + no + ", blogNo=" + blogNo + ", file=" + file + "]";
  }
  

}

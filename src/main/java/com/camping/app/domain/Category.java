package com.camping.app.domain;

public class Category {
  private int no;
  private String category;
  
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  @Override
  public String toString() {
    return "Category [no=" + no + ", category=" + category + "]";
  }
}

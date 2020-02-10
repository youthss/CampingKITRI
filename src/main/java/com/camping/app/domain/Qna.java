package com.camping.app.domain;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Qna { // 일반회원이나 호스트가 관리자에게 문의하는 게시판 도메인
  private int qnaNo;
  private String auth;
  
  private int userNo;
  private String name;
  
  private int categoryNo;
  private String category;
  private int parent;
  private int order;
  private int step;
  private String title;
  private String content;
  private String password;
  
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date createdDate;
  
  private int viewCount;
  
  
  public int getQnaNo() {
    return qnaNo;
  }
  public void setQnaNo(int qnaNo) {
    this.qnaNo = qnaNo;
  }
  public String getAuth() {
    return auth;
  }
  public void setAuth(String auth) {
    this.auth = auth;
  }
  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public int getParent() {
    return parent;
  }
  public void setParent(int parent) {
    this.parent = parent;
  }
  public int getOrder() {
    return order;
  }
  public void setOrder(int order) {
    this.order = order;
  }
  public int getStep() {
    return step;
  }
  public void setStep(int step) {
    this.step = step;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public int getCategoryNo() {
    return categoryNo;
  }
  public void setCategoryNo(int categoryNo) {
    this.categoryNo = categoryNo;
  }
  @Override
  public String toString() {
    return "Qna [qnaNo=" + qnaNo + ", auth=" + auth + ", userNo=" + userNo + ", name=" + name
        + ", categoryNo=" + categoryNo + ", category=" + category + ", parent=" + parent
        + ", order=" + order + ", step=" + step + ", title=" + title + ", content=" + content
        + ", password=" + password + ", createdDate=" + createdDate + ", viewCount=" + viewCount
        + "]";
  }
  

}

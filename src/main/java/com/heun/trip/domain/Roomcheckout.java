package com.heun.trip.domain;

public class Roomcheckout {
  private int rmsNo;
  private String rmsName;
  
  @Override
  public String toString() {
    return "Roomcheckout [rmsNo=" + rmsNo + ", rmsName=" + rmsName + "]";
  }
  
  public int getRmsNo() {
    return rmsNo;
  }
  public void setRmsNo(int rmsNo) {
    this.rmsNo = rmsNo;
  }
  public String getRmsName() {
    return rmsName;
  }
  public void setRmsName(String rmsName) {
    this.rmsName = rmsName;
  }
  

}

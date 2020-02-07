package com.heun.trip.domain;
 
import java.sql.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Room {
  
  private int no;
  private String name;
  private String price;
  private int bed;
  private int bath;
  private int maxPerson;
  private String postcode;
  private String area;
  private String address;
  private String detailAddress;
  private String latitude;
  private String longitude;
  private String content;
  private String type;
  private String details;
  private String reservation;
  private String welcome;
  private String traffic;
  private String grade;
  private String state;
  private String rjmo;
  
  private String thumbnail;
  
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
  private Date createdDate;
  
  private int hostNo;
  
  private List<Convenience> conveniences;
  private List<Safety> safeties;
  private List<RoomFile> photos;
  private List<String> reservationHistory;
  
  public String getPrice() {
    return price;
  }
  public void setPrice(String price) {
    this.price = price;
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
  public int getBed() {
    return bed;
  }
  public void setBed(int bed) {
    this.bed = bed;
  }
  public int getBath() {
    return bath;
  }
  public void setBath(int bath) {
    this.bath = bath;
  }
  public int getMaxPerson() {
    return maxPerson;
  }
  public void setMaxPerson(int maxPerson) {
    this.maxPerson = maxPerson;
  }
  public String getPostcode() {
    return postcode;
  }
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }
  public String getArea() {
    return area;
  }
  public void setArea(String area) {
    this.area = area;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getDetailAddress() {
    return detailAddress;
  }
  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }
  public String getLatitude() {
    return latitude;
  }
  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }
  public String getLongitude() {
    return longitude;
  }
  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getDetails() {
    return details;
  }
  public void setDetails(String details) {
    this.details = details;
  }
  public String getReservation() {
    return reservation;
  }
  public void setReservation(String reservation) {
    this.reservation = reservation;
  }
  public String getWelcome() {
    return welcome;
  }
  public void setWelcome(String welcome) {
    this.welcome = welcome;
  }
  public String getTraffic() {
    return traffic;
  }
  public void setTraffic(String traffic) {
    this.traffic = traffic;
  }
  public String getThumbnail() {
    return thumbnail;
  }
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public int getHostNo() {
    return hostNo;
  }
  public void setHostNo(int hostNo) {
    this.hostNo = hostNo;
  }
  public List<Convenience> getConveniences() {
    return conveniences;
  }
  public void setConveniences(List<Convenience> conveniences) {
    this.conveniences = conveniences;
  }
  public List<Safety> getSafeties() {
    return safeties;
  }
  public void setSafeties(List<Safety> safeties) {
    this.safeties = safeties;
  }
  public List<RoomFile> getPhotos() {
    return photos;
  }
  public void setPhotos(List<RoomFile> photos) {
    this.photos = photos;
  }
  public String getGrade() {
    return grade;
  }
  public void setGrade(String grade) {
    this.grade = grade;
  }
  public List<String> getReservationHistory() {
    return reservationHistory;
  }
  public void setReservationHistory(List<String> reservationHistory) {
    this.reservationHistory = reservationHistory;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getRjmo() {
    return rjmo;
  }
  public void setRjmo(String rjmo) {
    this.rjmo = rjmo;
  }
  @Override
  public String toString() {
    return "Room [no=" + no + ", name=" + name + ", price=" + price + ", bed=" + bed + ", bath="
        + bath + ", maxPerson=" + maxPerson + ", postcode=" + postcode + ", area=" + area
        + ", address=" + address + ", detailAddress=" + detailAddress + ", latitude=" + latitude
        + ", longitude=" + longitude + ", content=" + content + ", type=" + type + ", details="
        + details + ", reservation=" + reservation + ", welcome=" + welcome + ", traffic=" + traffic
        + ", grade=" + grade + ", state=" + state + ", rjmo=" + rjmo + ", thumbnail=" + thumbnail
        + ", createdDate=" + createdDate + ", hostNo=" + hostNo + ", conveniences=" + conveniences
        + ", safeties=" + safeties + ", photos=" + photos + ", reservationHistory="
        + reservationHistory + "]";
  }
 
  
} 

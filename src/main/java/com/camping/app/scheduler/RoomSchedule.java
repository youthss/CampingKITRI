package com.camping.app.scheduler;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.camping.app.dao.RevDao;

@Component
public class RoomSchedule {

  @Autowired
  RevDao revDao;

  @Scheduled(cron="0 0 12 * * *")
  public void checkinSync() {
    System.out.println("체크인으로 변경한 예약건 : " + revDao.checkin(getNow()));
  }
  
  @Scheduled(cron="0 0 10 * * *")
  public void checkoutSync() {
    System.out.println("체크아웃으로 변경한 예약건 : " + revDao.checkout(getNow()));
  }
  
  public String getNow() {
    return new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
  }

}

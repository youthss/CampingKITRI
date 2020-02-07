package com.heun.trip.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.heun.trip.dao.HostQnaDao;
import com.heun.trip.dao.RevDao;
import com.heun.trip.domain.Rev;
import com.heun.trip.service.RevService;

@Service 
public class RevServiceImpl implements RevService {

  RevDao revDao;
  HostQnaDao hostQnaDao;

  public RevServiceImpl(RevDao revDao, HostQnaDao hostQnaDao) {
    this.revDao = revDao;
    this.hostQnaDao = hostQnaDao;
  }

  @Override
  public int inupdate(Rev rev) {
    return revDao.inupdate(rev);
  }

  @Override
  public List<Rev> list(int pageNo, int pageSize, int userNo) {

    HashMap<String,Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);

    return revDao.findAll(params);
  }

  @Override
  public Rev detail(int no) {

    return revDao.findByNo(no);
  }

  @Override
  public int count(Map<String, Object> params) {
    return revDao.count(params);
  }

  @Override
  public int size(int no) {
    return revDao.countAll(no);
  }

  @Override
  public List<Rev> getupdtData(int pageNo, int pageSize, int userNo) {

    HashMap<String,Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);

    return revDao.getupdtData(params);
  }

  @Override
  public int cancel(int no) {
    return revDao.requestDelete(no);
  }

  @Override
  public List<Rev> listInHostPage(int pageNo, int pageSize, int userNo) {

    HashMap<String,Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);

    return revDao.listInHostPage(params);
  }

  @Override
  public int countInHostPage(int no) {
    return revDao.countInHostPage(no);
  }

  @Override
  public int deleteInHostpage(int no) {
    
    hostQnaDao.delete(no);
    
    return revDao.deleteInHostpage(no);
  }

  @Override
  public int change(int no) {
    return revDao.changeRev(no);
  }

  @Override
  public List<String> reservationHistory(int no) {
    List<Rev> history = revDao.findByRoomReservationHistory(no);
    final String DATE_PATTERN = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    Date startDate = null;
    Date endDate = null;
    ArrayList<String> dates = new ArrayList<String>();
    Calendar c = Calendar.getInstance();
    for (Rev r : history) {
      startDate = r.getCheckIn();
      // 체크아웃 날짜는 예약날짜에 포함이 되지 않으므로 -1일
      c.setTime(r.getCheckOut());
      c.add(Calendar.DAY_OF_MONTH, -1);
      endDate = c.getTime();
      Date currentDate = startDate;
      while (currentDate.compareTo(endDate) <= 0) {
        dates.add(sdf.format(currentDate));
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        currentDate = c.getTime();
      }
    }
    return dates;
  }
  
  @Override
  synchronized public boolean add(Rev rev) {
    List<String> history = reservationHistory(rev.getRmsNo());
    for (String h : history) {
      if (rev.getCheckIn().toString().equals(h)) {
        return false;
      }
    }
    revDao.add(rev);
    return true;
  }

}



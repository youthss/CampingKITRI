package com.heun.trip.service.impl;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.heun.trip.dao.RiwDao;
import com.heun.trip.domain.Riw;
import com.heun.trip.service.RiwService;

@Service
public class RiwServiceImpl implements RiwService {

  RiwDao riwDao;

  public RiwServiceImpl(RiwDao riwDao ) {
    this.riwDao = riwDao;
  }
 
  @Override
  public List<Riw> roomreview(int no, int pageNo, int pageSize) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("no", no);
   return riwDao.findroomreview(params);
  }
  
  @Override
  public List<Riw> list(int pageNo, int pageSize) {

    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);

    return riwDao.findAll(params);
  }
  
  
  
  @Override
  public List<Riw> listMypage(int pageNo, int pageSize, int userNo) {
    
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("userNo", userNo);
    
    return riwDao.findAllMypage(params);
  }
  
  @Override
  public List<Riw> hostlistMypage(int pageNo, int pageSize, int userNo) {
    
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("userNo", userNo);
    
    return riwDao.findAllhostMypage(params);
  }

  @Override
  public int size() {
    return riwDao.countAll();
  }
  
  @Override
  public int size(int userNo) {
    return riwDao.countReview(userNo);
  }

  @Override
  public int add(Riw riw) {
    int count = riwDao.insert(riw);
    return count;
  }
  
  @Override
  public int addriw(Riw riw) {
    int count = riwDao.riwinsert(riw);
    return count;
  }
  
  @Override
  public int sumroomgrd(int roomNo) {
    int count = riwDao.sumroomgrd(roomNo);
    return count;
  }
  
  @Override
  public int grdpeople(Riw riw) {
    int count = riwDao.grdpeople(riw);
    return count;
  }
  
  @Override
  public Riw get(int no) {
    return riwDao.findByNo(no);
  }

  @Override
  public int delete(int no) {
    return riwDao.delete(no);
  }
  
  @Override
  public int replydelete(int no) {
    return riwDao.replydelete(no);
  }

  @Override
  public int update(Riw riw) {
    return riwDao.update(riw);
  }
  
  @Override
  public int replyupdate(Riw riw) {
    return riwDao.replyupdate(riw);
  }
  
  @Override
  public int count(Map<String, Object> params) {
    return riwDao.count(params);
  }
  
  @Override
  public int reviewsize(int no) {
    return riwDao.roomreviewcount(no);
  }

  @Override
  public int countAllHost(int no) {
    return riwDao.countAllHost(no);
  }

}








package com.heun.trip.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.heun.trip.dao.BookmarkDao;
import com.heun.trip.domain.Bookmark;
import com.heun.trip.service.BookmarkService;

@Service
public class BookmarkServiceImpl implements BookmarkService {

  BookmarkDao bookmarkDao;

  public BookmarkServiceImpl(BookmarkDao bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }

  @Override
  public List<Bookmark> list(int pageNo, int pageSize, int userNo) {

    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("userNo", userNo);

    return bookmarkDao.findAll(params);
  }
 
  @Override
  public int add(Bookmark bookmark) {
    int count = bookmarkDao.insert(bookmark);
    
    return count;
  }
  
  @Override
  public int delete(int userNo, int roomNo) {
    
    HashMap<String,Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("roomNo", roomNo);
 
    return bookmarkDao.delete(params);
  }
  
  @Override
  public int update(String memo, int userNo, int roomNo) {
    
    HashMap<String,Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("roomNo", roomNo);
    params.put("memo", memo);
    
    return bookmarkDao.update(params);
  }
  
  @Override
  public int size(int no) {
    
    return bookmarkDao.countAll(no);
  }
  
  @Override
  public int count(int userNo, int roomNo) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("roomNo", roomNo);
    
    return bookmarkDao.countBookmark(params);
  }

}








package com.camping.kitri.dao;

import java.util.List;
import java.util.Map;
import main.java.com.heun.trip.domain.Bookmark;

public interface BookmarkDao {
  
  List<Bookmark> findAll(Map<String, Object> params);
  int insert(Bookmark bookmark);
  int countAll(int no);
  int countBookmark(Map<String, Object> params);
  int delete(Map<String, Object> params);
  int roomdelete(int no);
  int update(Map<String, Object> params);
  
} 






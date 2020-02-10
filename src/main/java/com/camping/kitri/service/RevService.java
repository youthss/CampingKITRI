package com.camping.kitri.service;

import java.util.List;
import java.util.Map;

import com.camping.kitri.domain.Rev;
 
public interface RevService {
  List<Rev> list(int pageNo, int pageSize, int userNo);
  int count(Map<String, Object> params);
  int size(int no);
  Rev detail(int no);
  int inupdate(Rev rev);
  List<Rev> getupdtData(int pageNo, int pageSize, int userNo);
  int cancel(int no);
  List<Rev> listInHostPage(int pageNo, int pageSize, int userNo);
  int countInHostPage(int no);
  int deleteInHostpage(int no);
  int change(int no);
  List<String> reservationHistory(int no);
  boolean add(Rev rev);
} 

package com.camping.app.dao;

import java.util.List;

import com.camping.app.domain.HostQna;

public interface HostQnaDao {
  int insert(HostQna qna);
  int delete(int no);  
  int countAll();
  List<HostQna> findByHostqnaList(int no); 
  List<HostQna> findNewHostqna(int no); 
  List<HostQna> findNewGuestqna(int no); 
}








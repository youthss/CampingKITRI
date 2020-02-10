package com.camping.kitri.dao;

import java.util.List;

import com.camping.kitri.domain.HostQna;

public interface HostQnaDao {
  int insert(HostQna qna);
  int delete(int no);  
  int countAll();
  List<HostQna> findByHostqnaList(int no); 
  List<HostQna> findNewHostqna(int no); 
  List<HostQna> findNewGuestqna(int no); 
}








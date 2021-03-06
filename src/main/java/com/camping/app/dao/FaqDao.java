package com.camping.app.dao;

import java.util.List;
import java.util.Map;
import com.camping.app.domain.*;

public interface FaqDao {
  int insert(Faq faq);
  List<Faq> findAll(Map<String,Object> params);
  Faq findByNo(int no);
//  int increaseCount(int no);  
  int update(Faq faq); 
  int delete(int no);  
  int countAll(); 
 // List<Qna> findByReList(Map<String,Object> params);
}
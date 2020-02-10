package com.camping.app.service;
 
import java.util.List;
import java.util.Map;

import com.camping.app.domain.Riw;

public interface RiwService {
  List<Riw> list(int pageNo, int pageSize);
  List<Riw> listMypage(int pageNo, int pageSize, int userNo);
  List<Riw> hostlistMypage(int pageNo, int pageSize, int userNo);
  int add(Riw riw);
  int sumroomgrd(int roomNo);
  int grdpeople(Riw riw);
  int addriw(Riw riw);
  Riw get(int no);
  int update(Riw riw);   
  int replyupdate(Riw riw);   
  int delete(int no);
  int replydelete(int no);
  int size();
  // 유저 개인의 review 수를 구함.
  int size(int userNo);
  int reviewsize(int no);
  int count(Map<String, Object> params);
  int countAllHost(int no);
  List<Riw> roomreview(int no, int pageNo, int pageSize) ;
}

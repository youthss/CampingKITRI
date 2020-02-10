package com.camping.app.dao;
 
import java.util.List;

import com.camping.app.domain.RoomFile;

public interface RoomFileDao {
  int insert(List<RoomFile> files);
  
//  List<Room> findAll(Map<String,Object> paramMap);
//  Room findByNo(int no);
//  Member findByEmailPassword(Map<String,Object> paramMap);
//  int update(Member member);
  int delete(int no);
  int countAll();
}
 
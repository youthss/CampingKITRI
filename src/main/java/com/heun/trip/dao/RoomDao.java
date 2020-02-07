package main.java.com.heun.trip.dao;

import java.util.List;
import java.util.Map;
import com.heun.trip.domain.Convenience;
import com.heun.trip.domain.Room;
import com.heun.trip.domain.Safety;

public interface RoomDao {
  int insert(Room room);
  int update(Room room);
  int cecoUpdate(Map<String,Object> paramMap);
  int insertConvenience(List<Convenience> Conveniences);
  int insertSafety(List<Safety> safeties);
  int delete(int no);
  int amnDelete(int no);
  int safetyDelete(int no);
  List<Room> findAll(Map<String,Object> paramMap);
  List<Room> findhpriceAll(Map<String,Object> paramMap);
  List<Room> findrpriceAll(Map<String,Object> paramMap);
  List<Room> findhotAll(Map<String,Object> paramMap);
  Room findByNo(int no);
  Room findByRoom(int no);
  List<Room> findByHostRoomList(Map<String,Object> paramMap);
  List<Room> findByCecoRoomList(Map<String,Object> paramMap);
//  Member findByEmailPassword(Map<String,Object> paramMap);
//  int update(Member member);
//  int delete(int no);
  int countAll(Map<String,Object> paramMap); 
  int hostcountAll(int no); 
  int cecoRoomCountAll(int no);
  String getRoomName(int no);
  int grdinsert(Map<String,Object> paramMap);
}  

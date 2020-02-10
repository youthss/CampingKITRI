package com.camping.kitri.service;
  
import java.util.List;

import com.camping.kitri.domain.Room;

public interface RoomService {
  List<Room> list(int pageNo, int pageSize, String lati, String longi);
  List<Room> hpricelist(int pageNo, int pageSize, String lati, String longi);
  List<Room> rpricelist(int pageNo, int pageSize, String lati, String longi);
  List<Room> hotlist(int pageNo, int pageSize, String lati, String longi);
  int size(String lati, String longi);
  int delete(int no); 
  int hostsize(int no);
  int cecoRoomsize(int no);
  Room get(int no);
  Room roomGet(int no);
  List<Room> hostroomlist(int pageNo, int pageSize,int hostNo);
  List<Room> cecoRoomlist(int pageNo, int pageSize,int no);
  int add(Room room);
  int update(Room room);
  int cecoRoomUpdate(int no, String msg);
  String getRoom(int no);
  int grdupdate(int roomgrd, int roomNo);  
}
 
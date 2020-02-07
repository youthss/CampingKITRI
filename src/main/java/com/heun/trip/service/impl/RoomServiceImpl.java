package com.heun.trip.service.impl;
   
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.heun.trip.dao.BookmarkDao;
import com.heun.trip.dao.RevDao;
import com.heun.trip.dao.RoomDao;
import com.heun.trip.dao.RoomFileDao;
import com.heun.trip.domain.Convenience;
import com.heun.trip.domain.Room;
import com.heun.trip.domain.RoomFile;
import com.heun.trip.domain.Safety;
import com.heun.trip.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
  
  RoomDao roomDao;
  RoomFileDao roomFileDao;
  BookmarkDao bookmarkDao;
  RevDao revDao;
  
  public RoomServiceImpl(RoomDao roomDao, RoomFileDao roomFileDao, BookmarkDao bookmarkDao,RevDao revDao) {
    this.roomDao = roomDao;
    this.roomFileDao = roomFileDao;
    this.bookmarkDao = bookmarkDao;
    this.revDao=revDao;
  }
  
  @Override
  public int add(Room room) {
 
    roomDao.insert(room);
    
    List<Convenience> cons = room.getConveniences();
    for (Convenience c : cons) {
      c.setRoomNo(room.getNo());
    }
    roomDao.insertConvenience(cons);
    
    List<Safety> safes = room.getSafeties();
    for (Safety s : safes) {
      s.setRoomNo(room.getNo());
    }
    roomDao.insertSafety(safes);
    
    List<RoomFile> files = room.getPhotos();
    for (RoomFile f : files) {
      f.setRoomNo(room.getNo());
    }
    roomFileDao.insert(files);
    
    return 1;
  }
  
  @Override
  public List<Room> list(int pageNo, int pageSize, String lati, String longi) {
    System.out.println("최신순 실행");
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("lati", lati);
    params.put("longi", longi);
    List<Room> rooms = roomDao.findAll(params);
    return rooms;
  }
  @Override
  public List<Room> hpricelist(int pageNo, int pageSize, String lati, String longi) {
    System.out.println("높은가격순 실행");
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("lati", lati);
    params.put("longi", longi);
    List<Room> rooms = roomDao.findhpriceAll(params);
    return rooms;
  }
  @Override
  public List<Room> rpricelist(int pageNo, int pageSize, String lati, String longi) {
    System.out.println("낮은가격순 실행");
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("lati", lati);
    params.put("longi", longi);
    List<Room> rooms = roomDao.findrpriceAll(params);
    return rooms;
  }
  @Override
  public List<Room> hotlist(int pageNo, int pageSize, String lati, String longi) {
    System.out.println("높은가격순 실행");
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("lati", lati);
    params.put("longi", longi);
    List<Room> rooms = roomDao.findhotAll(params);
    return rooms;
  }
  
  @Override
  public int size(String lati, String longi) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("lati", lati);
    params.put("longi", longi);
    return roomDao.countAll(params);
  }
  
  @Override
  public Room get(int no) {
    return roomDao.findByNo(no);
  }
  
  @Override
  public Room roomGet(int no) {
    return roomDao.findByRoom(no);
  }
  
  @Override
  public List<Room> hostroomlist(int pageNo, int pageSize,int hostNo) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("no", hostNo);
    params.put("size",pageSize );
    params.put("rowNo",(pageNo -1) * pageSize );
    return roomDao.findByHostRoomList(params);
  }
  @Override
  public int delete(int no) {
    roomFileDao.delete(no);
    roomDao.amnDelete(no);
    roomDao.safetyDelete(no);
    bookmarkDao.roomdelete(no);
    revDao.delete(no);
    
     return roomDao.delete(no);
  } 
  
  @Override
  public int hostsize(int no) {
    return roomDao.hostcountAll(no);
  }
  
  @Override
  public String getRoom(int no) {
    return roomDao.getRoomName(no);
  }
  @Override
  public int update(Room room) {
    int acti = 0;
    HashMap<String,Object> params = new HashMap<>();
    params.put("no", room.getNo());
    params.put("acti", acti);
    params.put("msg", "");
    roomDao.cecoUpdate(params);
    
    roomDao.amnDelete(room.getNo());
    roomDao.safetyDelete(room.getNo());
    roomFileDao.delete(room.getNo());
    roomDao.update(room);
    
    List<Convenience> cons = room.getConveniences();
    for (Convenience c : cons) {
      c.setRoomNo(room.getNo());
    }
    roomDao.insertConvenience(cons);
    
    List<Safety> safes = room.getSafeties();
    for (Safety s : safes) {
      s.setRoomNo(room.getNo());
    }
    roomDao.insertSafety(safes);
    
    List<RoomFile> files = room.getPhotos();
    for (RoomFile f : files) {
      f.setRoomNo(room.getNo());
    }
    roomFileDao.insert(files);
    
    return 1;
  }

  @Override
  public int cecoRoomsize(int no) {
    return roomDao.cecoRoomCountAll(no);
  }

  @Override
  public List<Room> cecoRoomlist(int pageNo, int pageSize,int no) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("no", no);
    params.put("size",pageSize );
    params.put("rowNo",(pageNo -1) * pageSize );
    return roomDao.findByCecoRoomList(params);
  }

  @Override
  public int cecoRoomUpdate(int no, String msg) {
    int acti = 0;
    HashMap<String,Object> params = new HashMap<>();
    if (msg.equals("")) {
      acti = 1;
    } else {
      acti = 2;
    }
    params.put("no", no);
    params.put("msg", msg);
    params.put("acti", acti);
    return roomDao.cecoUpdate(params);
  }
  
  @Override
  public int grdupdate(int roomgrd, int roomNo) {
    
    HashMap<String,Object> params = new HashMap<>();
    params.put("grade", roomgrd);
    params.put("roomNo", roomNo );
    
    return roomDao.grdinsert(params);
  }
  
  
}

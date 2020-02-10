package com.camping.app.service.impl;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.camping.app.dao.HostQnaDao;
import com.camping.app.dao.MemberDao;
import com.camping.app.dao.RevDao;
import com.camping.app.dao.RoomDao;
import com.camping.app.domain.HostQna;
import com.camping.app.domain.Member;
import com.camping.app.domain.Rev;
import com.camping.app.domain.Room;
import com.camping.app.service.HostQnaService;

@Service
public class HostQnaServiceImpl implements HostQnaService {

  HostQnaDao hostqnaDao;
  RevDao revDao;
  RoomDao roomDao;
  MemberDao memberDao;

  public HostQnaServiceImpl( HostQnaDao hostqnaDao, RevDao revDao, RoomDao roomDao, MemberDao memberDao) {
    this.memberDao = memberDao;
    this.hostqnaDao = hostqnaDao;
    this.revDao = revDao;
    this.roomDao = roomDao;
  }

  @Override
  public int size() {
    return hostqnaDao.countAll();
  }

  @Override
  public int delete(int no) {
    return hostqnaDao.delete(no);
  }

  @Override
  public int add(HostQna hostqna) {
    return hostqnaDao.insert(hostqna);
  }
  
  // 게스트일때 호스트 사진을 구한다.
  @Override
  public String getHostPhoto(int no) {
    
    Rev rev = revDao.findByNo(no);
    int rmsNo = rev.getRmsNo();
    Room room = roomDao.findByNo(rmsNo);
    int hostNo = room.getHostNo();
    Member member = memberDao.findByNo(hostNo);
    String hostPhoto = member.getPhoto();
    
    return hostPhoto;
  }
  
  // 호스트일때 게스트 사진을 구한다.
  @Override
  public String getUserPhoto(int no) {
    
    Rev rev = revDao.findByNo(no);
    int userNo = rev.getUserNo();
    Member member = memberDao.findByNo(userNo);
    String userPhoto = member.getPhoto();
    
    return userPhoto;
  }

  @Override
  public List<HostQna> HostList(int no) {
    return hostqnaDao.findByHostqnaList(no);
  }

  @Override
  public List<HostQna> NewGuestList(int no) {

    List<HostQna> qnas = hostqnaDao.findNewGuestqna(no);

    for(HostQna q : qnas) {
      int revNo = q.getRevNo();

      Rev rev = revDao.findByNo(revNo);
      Date checkIn = rev.getCheckIn();
      Date checkOut = rev.getCheckOut();
      int charge = rev.getRevCharge();
      int rmsNo = rev.getRmsNo();
      Room room = roomDao.findByNo(rmsNo);
      String addr = room.getAddress();
      String name = room.getName();
      String thumb = room.getThumbnail();
      int roomNo = room.getNo();

      q.setCheckIn(checkIn);
      q.setCheckOut(checkOut);
      q.setCharge(charge);
      q.setRoomAddr(addr);
      q.setRoomName(name);
      q.setRoomPhoto(thumb);
      q.setRoomNo(roomNo);

    }

    return qnas;
  }

  @Override
  public List<HostQna> NewHostList(int no) {
    List<HostQna> qnas = hostqnaDao.findNewHostqna(no);

    for(HostQna q : qnas) {
      int revNo = q.getRevNo();

      Rev rev = revDao.findByNo(revNo);
      Date checkIn = rev.getCheckIn();
      Date checkOut = rev.getCheckOut();
      int charge = rev.getRevCharge();
      int rmsNo = rev.getRmsNo();
      int userNo = rev.getUserNo();
      
      Member member = memberDao.findByNo(userNo);
      String userName = member.getName();
      String userPhoto = member.getPhoto();
      
      Room room = roomDao.findByNo(rmsNo);
      String addr = room.getAddress();
      String name = room.getName();
      String thumb = room.getThumbnail();
      int roomNo = room.getNo();

      q.setCheckIn(checkIn);
      q.setCheckOut(checkOut);
      q.setCharge(charge);
      q.setRoomAddr(addr);
      q.setRoomName(name);
      q.setRoomPhoto(thumb);
      q.setRoomNo(roomNo);
      q.setUserName(userName);
      q.setUserPhoto(userPhoto);

    }

    return qnas;
  }

}








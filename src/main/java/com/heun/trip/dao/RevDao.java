package main.java.com.heun.trip.dao;
 
import java.util.List;
import java.util.Map;
import com.heun.trip.domain.Rev;

public interface RevDao {
  List<Rev> findAll(Map<String, Object> params);
  int count(Map<String, Object> params);
  int countAll(int no);
  Rev findByNo(int no);
  int delete(int no);
  int inupdate(Rev rev);
  List<Rev> getupdtData(Map<String, Object> params);
  int requestDelete(int no);
  List<Rev> listInHostPage(Map<String, Object> params);
  int countInHostPage(int no);
  int deleteInHostpage(int no); 
  int changeRev(int no); 
  List<Rev> findByRoomReservationHistory(int no);
  int add(Rev rev);
  int checkin(String date);
  int checkout(String date);
}
 





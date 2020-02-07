package main.java.com.heun.trip.dao;
 
import java.util.List;
import java.util.Map;
import com.heun.trip.domain.Riw;

public interface RiwDao {
  int insert(Riw riw);
  int sumroomgrd(int roomNo);
  int grdpeople(Riw riw);
  int riwinsert(Riw riw);
  List<Riw> findAll(Map<String,Object> params);
  List<Riw> findAllMypage(Map<String,Object> params);
  List<Riw> findAllhostMypage(Map<String,Object> params);
  Riw findByNo(int no);
  int delete(int no);   
  int replydelete(int no);
  int update(Riw riw); 
  int replyupdate(Riw riw);
  int countAll();
  int countReview(int userNo);
  int count(Map<String, Object> params);
  int roomreviewcount(int no);
  List<Riw> findroomreview(Map<String,Object> params);
  int countAllHost(int no);
}






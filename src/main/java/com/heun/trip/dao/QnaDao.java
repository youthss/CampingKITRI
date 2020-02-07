package main.java.com.heun.trip.dao;

import java.util.List;
import java.util.Map;
import com.heun.trip.domain.Category;
import com.heun.trip.domain.Qna;

public interface QnaDao {
  int insert(Qna qna);
  List<Qna> findAll(Map<String,Object> params);
  List<Qna> findbyname(Map<String,Object> params);
  List<Qna> findbytitle(Map<String,Object> params);
  List<Qna> findbytitlename(Map<String,Object> params);
  List<Qna> findByKeyword(Map<String, Object> params);
  Qna findByNo(int no);
  int increaseCount(int no);  
  int delete(int no);   
  int update(Qna qna);   
  int namecountAll(Map<String, Object> params);
  int titlecountAll(Map<String, Object> params); 
  int titlenamecountAll(Map<String, Object> params); 
  int countAll(); 
  List<Qna> findByReList(Map<String,Object> params);
  List<Category> getCategory();
  int maxParent();
  int maxOrder(int parent);
  int sorting(Map<String,Object> params);
  List<Qna> deleteList(Map<String,Object> params); 
  int password(Map<String, Object> params);
  int passwordCheck(int qnaNo);
}






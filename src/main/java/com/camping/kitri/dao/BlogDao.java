package com.camping.kitri.dao;
 
import java.util.List;
import java.util.Map;

import com.camping.kitri.domain.Blike;
import com.camping.kitri.domain.Blog;
import com.camping.kitri.domain.Roomcheckout;

public interface BlogDao {
  List<Blog> findAll(Map<String,Object> params);
  List<Blog> findAllIndex();
  int countAll(); 
  Blog findByNo(int no);
  int checkRev(int no);
  int insert(Blog blog);
  List<Roomcheckout> roomCheckOut(int no);
  int delete(int no);
  int deletelike(int no);
  int update(Blog blog);
  List<Blog> orderbylist(Map<String,Object> params);
  List<Blog> deorderbylist(Map<String,Object> params);
  List<Blog> likebylist(Map<String,Object> params); 
  int increaseLike(Blike blike);
  int decreaseLike(Blike blike);
  int checkLike(Blike blike);
  int checkView(Blike blike);
  int createLike(Blike blike);
  int countLike(int no);
}

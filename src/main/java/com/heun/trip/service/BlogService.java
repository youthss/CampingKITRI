package com.heun.trip.service;
 
import java.util.List;
import com.heun.trip.domain.Blike;
import com.heun.trip.domain.Blog;
import com.heun.trip.domain.BlogFile;
import com.heun.trip.domain.Roomcheckout;

public interface BlogService {
  List<Blog> list(int pageNo, int pageSize);
  List<Blog> listIndex();
  int size();
  Blog get(int no);
  int checkRev(int no);
  int add(Blog blog);
  List<Roomcheckout> roomCheckOut(int no);
  int delete(int no);
  int update(Blog blog);
  List<Blog> order(int pageNo, int pageSize);
  List<Blog> deorder(int pageNo, int pageSize);
  List<Blog> likebylist(int pageNo, int pageSize);
  int increaseLike(Blike blike);
  int decreaseLike(Blike blike);
  int checkLike(Blike blike);
  int checkView(Blike blike);
  int createLike(Blike blike);
  int countLike(int no);
  List<BlogFile> filelist(int no);
}

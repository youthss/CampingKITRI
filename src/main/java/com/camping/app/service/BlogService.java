package com.camping.app.service;
 
import java.util.List;

import com.camping.app.domain.Blike;
import com.camping.app.domain.Blog;
import com.camping.app.domain.BlogFile;
import com.camping.app.domain.Roomcheckout;
//ㄹㅇㄴㅁㄹㅇㄴㅁㄹ
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

package com.camping.kitri.service;
 
import java.util.List;

import com.camping.kitri.domain.Category;
import com.camping.kitri.domain.Qna;

public interface QnaService {
  List<Qna> list(int pageNo, int pageSize, int selector, String val);
  List<Qna> namelist(int pageNo, int pageSize, int selector, String val);
  List<Qna> titlelist(int pageNo, int pageSize, int selector, String val);
  List<Qna> titlenamelist(int pageNo, int pageSize, int selector, String val);
  int add(Qna qna);
  Qna get(int no);
  List<Qna> reList(int parent, int step);
  int update(Qna qna);   
  int delete(int no, int parent, int order);
  int namesize(String val);
  int titlesize(String val);
  int titlenamesize(String val);
  int size();
  List<Category> getCategory();
  int maxParent();
  int maxOrder(int parent);
  int sorting(int parent, int order);
  List<Qna> search(int pageNo, int pageSize, String name, String title, String titlename);
  int password(int qnaNo, String pwd);
  int pwdCheck(int qnaNo);
}

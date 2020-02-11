package com.camping.app.service;

import java.util.List;

import com.camping.app.domain.Bookmark;

public interface BookmarkService {
  List<Bookmark> list(int pageNo, int pageSize, int userNo);
  int add(Bookmark bookmark);
//  Riw get(int no);
  int update(String memo, int userNo, int roomNo);   
  int delete(int userNo, int roomNo);
  int size(int no);
  int count(int userNo, int roomNo);
}

package com.camping.app.dao;

import java.util.List;

import com.camping.app.domain.BlogFile;

public interface BlogFileDao {
  int insert(List<BlogFile> blogFiles);
  int delete(int no);
  List<BlogFile> findByNo(int no);
}
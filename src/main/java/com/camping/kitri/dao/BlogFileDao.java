package com.camping.kitri.dao;

import java.util.List;

import com.camping.kitri.domain.BlogFile;

public interface BlogFileDao {
  int insert(List<BlogFile> blogFiles);
  int delete(int no);
  List<BlogFile> findByNo(int no);
}
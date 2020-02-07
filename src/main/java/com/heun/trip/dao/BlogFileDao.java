package main.java.com.heun.trip.dao;

import java.util.List;
import com.heun.trip.domain.BlogFile;

public interface BlogFileDao {
  int insert(List<BlogFile> blogFiles);
  int delete(int no);
  List<BlogFile> findByNo(int no);
}
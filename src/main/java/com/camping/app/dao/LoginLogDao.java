package com.camping.app.dao;

import java.util.List;
import java.util.Map;

import com.camping.app.domain.LoginLog;
 
public interface LoginLogDao {
  int insert(LoginLog loginLog);
  List<LoginLog> findAll(Map<String,Object> params);
  int countAll();
}








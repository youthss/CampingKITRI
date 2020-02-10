package com.camping.kitri.dao;

import java.util.List;
import java.util.Map;

import com.camping.kitri.domain.LoginLog;
 
public interface LoginLogDao {
  int insert(LoginLog loginLog);
  List<LoginLog> findAll(Map<String,Object> params);
  int countAll();
}








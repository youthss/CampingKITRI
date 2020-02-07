package main.java.com.heun.trip.dao;

import java.util.List;
import java.util.Map;
import com.heun.trip.domain.LoginLog;
 
public interface LoginLogDao {
  int insert(LoginLog loginLog);
  List<LoginLog> findAll(Map<String,Object> params);
  int countAll();
}








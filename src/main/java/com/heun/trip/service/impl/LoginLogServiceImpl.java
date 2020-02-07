package com.heun.trip.service.impl;
 
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heun.trip.dao.LoginLogDao;
import com.heun.trip.domain.LoginLog;
import com.heun.trip.service.LoginLogService;

@Service
public class LoginLogServiceImpl implements LoginLogService {
  
  @Autowired
  LoginLogDao loginLogDao;
  
  @Override
  public List<LoginLog> list(int pageNo, int pageSize) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    List<LoginLog> LoginLogs = loginLogDao.findAll(params);
    return LoginLogs;
  }
  
  @Override
  public int size() {
    return loginLogDao.countAll();
  }
}








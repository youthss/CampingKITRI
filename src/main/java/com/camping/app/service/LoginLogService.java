package com.camping.app.service;
 
import java.util.List;

import com.camping.app.domain.LoginLog;

public interface LoginLogService { 
  List<LoginLog> list(int pageNo, int pageSize);
  int size();
}
  
package com.camping.kitri.service;
 
import java.util.List;

import com.camping.kitri.domain.LoginLog;

public interface LoginLogService { 
  List<LoginLog> list(int pageNo, int pageSize);
  int size();
}
  
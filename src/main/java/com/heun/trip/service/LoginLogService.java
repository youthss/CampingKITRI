package com.heun.trip.service;
 
import java.util.List;
import com.heun.trip.domain.LoginLog;

public interface LoginLogService { 
  List<LoginLog> list(int pageNo, int pageSize);
  int size();
}
  
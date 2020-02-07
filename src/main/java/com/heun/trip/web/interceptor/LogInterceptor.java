package com.heun.trip.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.heun.trip.dao.LoginLogDao;
import com.heun.trip.domain.LoginLog;

@Service
public class LogInterceptor implements HandlerInterceptor {

  @Autowired
  LoginLogDao loginLogDao;
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    request.setAttribute("time1", new Date());
    
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    Date now = new Date();
    
    Date reqDate = (Date) request.getAttribute("time1");
    long reqDateTime = reqDate.getTime();
    long curDateTime = now.getTime();
    
    long sec = (curDateTime - reqDateTime);
    
    LoginLog log = new LoginLog();
    log.setUrl(request.getPathInfo());
    log.setStartTime(sdf.format(request.getAttribute("time1")));
    log.setEndTime(sdf.format(now));
    log.setSumTime(String.valueOf(sec));
    log.setIp(request.getRemoteAddr());
    log.setState((String) request.getAttribute("status"));
    String email = (String) request.getAttribute("email");
    log.setEmail(email);
    
    if (email != null)
      loginLogDao.insert(log);
    
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }
}

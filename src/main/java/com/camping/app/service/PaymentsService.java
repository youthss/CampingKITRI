package com.camping.app.service;

import java.util.Map;

public interface PaymentsService { 
  String getAccessToken();
  boolean buyCheck(Map<String,Object> params);
  boolean buyCancel(Map<String,Object> params);
}
  
package com.camping.kitri.service;

import java.util.Map;

public interface FacebookService {
  Map<String,Object> getLoginUser(String accessToken);
}

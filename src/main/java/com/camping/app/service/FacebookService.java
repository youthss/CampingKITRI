package com.camping.app.service;

import java.util.Map;

public interface FacebookService {
  Map<String,Object> getLoginUser(String accessToken);
}

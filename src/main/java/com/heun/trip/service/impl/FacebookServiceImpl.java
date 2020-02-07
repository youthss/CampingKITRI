package com.heun.trip.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.heun.trip.service.FacebookService;

// 스프링 IoC 컨테이너가 관리하는 객체 중에서 
// 비즈니스 로직을 담당하는 객체는 
// 특별히 그 역할을 표시하기 위해 @Component 대신에 @Service 애노테이션을 붙인다.
// 이렇게 애노테이션으로 구분해두면 나중에 애노테이션으로 객체를 찾을 수 있다.
@Service
public class FacebookServiceImpl implements FacebookService {

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Map getLoginUser(String accessToken) {
        // Facebook의 Graph API 실행하기
        // => HTTP 요청을 할 때 스프링에서 제공하는 RestTemplate을 사용하라! 
        // 
        RestTemplate restTemplate = new RestTemplate();
        
        HashMap<String,String> values = new HashMap<>();
        values.put("v1", accessToken);
        values.put("v2", "id,name,email");
        
        Map response = restTemplate.getForObject(
            "https://graph.facebook.com/v3.3/me?access_token={v1}&fields={v2}", 
            Map.class,
            //accessToken,
//            "id,name,email");
            values); // 값을 개별적으로 넘기지 않고 맵에 담아 넘길 수도 있다.
    return response;
  }


}








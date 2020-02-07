package com.heun.trip.service.impl;
 
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.heun.trip.service.PaymentsService;

@Service
@PropertySource("classpath:/com/heun/trip/conf/sec.properties")
public class PaymentsServiceImpl implements PaymentsService {
  
  @Autowired 
  Environment env;
  
  @SuppressWarnings("rawtypes")
  @Override
  public String getAccessToken() {
    RestTemplate restTemplate = new RestTemplate();
    MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
    values.add("imp_key", env.getProperty("imp_key"));
    values.add("imp_secret", env.getProperty("imp_secret"));
    Map response = restTemplate.postForObject(
        "https://api.iamport.kr/users/getToken", 
        values,
        Map.class);
    Map res = (Map) response.get("response");
    return (String) res.get("access_token");
  }
  
  @SuppressWarnings({"rawtypes"})
  @Override
  public boolean buyCheck(Map<String, Object> params) {
    String impUid = "imp_uid";
    String merchantUid = "merchant_uid";
    RestTemplate restTemplate = new RestTemplate();
    HashMap<String,String> values = new HashMap<>();
    values.put("v1", (String) params.get("imp_uid"));
    values.put("v2", getAccessToken());
    Map rest = restTemplate.getForObject(
        "https://api.iamport.kr/payments/{v1}?_token={v2}", 
        Map.class,
        values);
    Map response = (Map) rest.get("response");
    if (params.get(impUid).equals(response.get(impUid)) &&
        params.get(merchantUid).equals(response.get(merchantUid))) {
      return true;
    }
    return false;
  }
  
  @SuppressWarnings({"rawtypes", "unused"})
  @Override
  public boolean buyCancel(Map<String, Object> params) {
    RestTemplate restTemplate = new RestTemplate();
    MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
    values.add("imp_uid", (String) params.get("imp_uid"));
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", getAccessToken());
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(values, headers);
    Map response = restTemplate.postForObject(
        "https://api.iamport.kr/payments/cancel", 
        request,
        Map.class);
    return true;
  }
 
}

package com.camping.kitri.conf;

import java.util.HashMap;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Configuration
@PropertySource("classpath:/com/heun/trip/conf/sec.properties")
public class Sms {
  
  @Autowired 
  Environment env;

  public void smsSend(String phoneNumber, String messageText) throws Exception {
    
    String api_key = env.getProperty("sms.apikey");
    String api_secret = env.getProperty("sms.apisecret");
    Message coolsms = new Message(api_key, api_secret);

    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phoneNumber);
    params.put("from", "01094559808");
    params.put("type", "SMS");
    
    String title = "[HeunheunTrip]\n";
    
    messageText = title + messageText;
    
    params.put("text", messageText);
    params.put("app_version", "test app 1.2"); // application name and version

    try {
      JSONObject obj = (JSONObject) coolsms.send(params);
      System.out.println(obj.toString());
    } catch (CoolsmsException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getCode());
      throw new Exception(e.getMessage());
    }
    
  }  
  
}

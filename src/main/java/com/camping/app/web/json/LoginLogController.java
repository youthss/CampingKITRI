package com.camping.app.web.json;

import java.util.HashMap;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.LoginLog;
import com.camping.app.service.LoginLogService;

@RestController("json/LoginLogController")
@RequestMapping("/json/loginlog")
public class LoginLogController {

  LoginLogService loginLogService;

  public LoginLogController(LoginLogService loginLogService) {
    this.loginLogService = loginLogService;
  }

  @GetMapping("list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="30") int pageSize
      ) { // localhost:8080/heunheuntrip/app/json/qna/list

    HashMap<String,Object> content = new HashMap<>();

    if (pageSize < 1 || pageSize > 30) 
      pageSize = 30;

    int rowCount = loginLogService.size();
    
    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;
    if (pageNo < 1) 
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;

    List<LoginLog> list = loginLogService.list(pageNo, pageSize);
    
    content.put("list", list);
    content.put("totalPage", totalPage);
    content.put("pageNo", pageNo);
    content.put("pageSize", pageSize);

    return content;
  }


}


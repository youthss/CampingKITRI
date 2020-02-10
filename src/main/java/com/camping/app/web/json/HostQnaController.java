
package com.camping.app.web.json;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.HostQna;
import com.camping.app.domain.Member;
import com.camping.app.service.HostQnaService;

@RestController("json/HostQnaController")
@RequestMapping("/json/hostqna")
public class HostQnaController {

  HostQnaService hostQnaService;

  public HostQnaController(HostQnaService hostQnaService) {
    this.hostQnaService = hostQnaService;
  }

  @PostMapping("add")
  public Object add(HostQna hostQna, HttpSession session) {

    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");
    
    System.out.println(hostQna);

    try {
      
      int userNo = loginUser.getNo();
      hostQna.setUserNo(userNo);
      hostQnaService.add(hostQna);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("hostqnalist")
  public Object hostQnaList(int no, HttpSession session) { // localhost:8080/heunheuntrip/app/json/qna/list

    List<HostQna> hostQnaList = hostQnaService.HostList(no);
    Member loginUser = (Member) session.getAttribute("loginUser");
    HashMap<String, Object> content = new HashMap<>();
    
    if (loginUser != null) {
      content.put("loginUserNo", loginUser.getNo());
      String auth = loginUser.getAuth();
      
      if (auth.equals("일반회원")) {
        
        // 일반회원일 때 상대방(호스트) 사진을 구한다.
        String hostPhoto = hostQnaService.getHostPhoto(no);
        
        content.put("hostPhoto", hostPhoto);
        
      } else if (auth.equals("호스트")) {
        
        // 호스트일 때 상대방(게스트) 사진을 구한다.
        String userPhoto = hostQnaService.getUserPhoto(no);
        
        content.put("userPhoto", userPhoto);
      }
    }
    
    content.put("list", hostQnaList);
    
    return content;
  }
  
  @GetMapping("indexlist")
  public Object indexList(HttpSession session) { 

    Member loginUser = (Member) session.getAttribute("loginUser");
    HashMap<String, Object> content = new HashMap<>();
    
    if (loginUser != null) {
      content.put("loginUserNo", loginUser.getNo());
      String auth = loginUser.getAuth();
      
      if (auth.equals("일반회원")) {
        
        List<HostQna> indexList = hostQnaService.NewGuestList(loginUser.getNo());
        content.put("userAuth", loginUser.getAuth());
        content.put("list", indexList);
        
      } else if (auth.equals("호스트")) {
        
        List<HostQna> indexList = hostQnaService.NewHostList(loginUser.getNo());
        
        content.put("userAuth", loginUser.getAuth());
        content.put("list", indexList);
      }
    }
    return content;
  }

  @GetMapping("delete")
  public Object delete(int no) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      hostQnaService.delete(no);
      content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

}

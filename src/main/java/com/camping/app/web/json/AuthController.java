package com.camping.app.web.json;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.Member;
import com.camping.app.service.FacebookService;
import com.camping.app.service.MemberService;

@RestController("json/AuthController")
@RequestMapping("/json/auth")
public class AuthController {

  MemberService memberService;
  ServletContext servletContext;
  FacebookService facebookService;

  public AuthController(MemberService memberService, ServletContext servletContext,FacebookService facebookService) {
    this.memberService = memberService;
    this.servletContext = servletContext;
    this.facebookService = facebookService;
  }

  final static Logger logger = LogManager.getLogger(AuthController.class);

  @PostMapping("login")
  public Object login(
      String email,
      String password,
      String name,
      HttpSession session,
      int sns_no,
      HttpServletRequest request) throws Exception {
    HashMap<String,Object> content = new HashMap<>();
    Member member = memberService.get(email, password);
    request.setAttribute("email", email);
    if (member == null) {
      content.put("status", "fail");
      request.setAttribute("status", "fail");
      content.put("message", "이메일 없거나 암호가 맞지 않습니다.");
    } else {
      session.setAttribute("loginUser", member);
      
      content.put("status", "success");
      request.setAttribute("status", "success");
    }
    return content;
  }

  @GetMapping("logout")
  public Object logout(HttpSession session,
      HttpServletRequest request) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      request.setAttribute("email", loginUser.getEmail());
    }
    logger.debug("세션 무효화시킴!");
    logger.debug("loginUser: " + loginUser);
    
    session.invalidate();

    HashMap<String,Object> content = new HashMap<>();
    content.put("status", "success");
    request.setAttribute("status", "success");
    return content;
  }

  @GetMapping("user")
  public Object user(HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    HashMap<String,Object> content = new HashMap<>();

    if (loginUser != null) {
      content.put("status", "success");
      content.put("user", loginUser);
    } else {
      content.put("status", "fail");
    }

    return content;
  }

  @RequestMapping(value="login", method=RequestMethod.GET)
  public String loginGET() {
    return "user/login";
  }

  @RequestMapping(value="loginPostNaver", method=RequestMethod.GET)
  public String loginPOSTNaver(HttpSession session) {
    return "user/loginPostNaver";
  }

  @PostMapping("snslogin")
  public Object snsLogin(
      String email,
      int sns_no,
      String token,
      HttpSession session,
      HttpServletRequest request) {
    HashMap<String,Object> content = new HashMap<>();
   
    if (accessToken(token) == false && sns_no == 1) {
      request.setAttribute("status", "fail");
      content.put("status", "accessTokenFail");
      content.put("message", "올바르지 않는 토큰입니다.");
      return content;
    } 

    Member member = memberService.snsget(email, sns_no);
    request.setAttribute("email", email);
    
    if (member == null) {
      request.setAttribute("status", "fail");
      content.put("status", "fail");
      content.put("message", "이메일이 없거나 암호가 맞지 않습니다.");
    } else {
      request.setAttribute("status", "success");
      session.setAttribute("loginUser", member);
      content.put("status", "success");
    }

    return content;
  }
  
  @SuppressWarnings("rawtypes")
  @GetMapping("fblogin")
  public Object fblogin(
      String accessToken,
      HttpSession session,
      HttpServletRequest request) throws Exception {
    // accessToken을 가지고 페이스북 서버에 로그인 사용자의 정보를 요청한다.
    Map fbLoginUser = facebookService.getLoginUser(accessToken);

    // 페이스북에서 받은 사용자 정보  중에서 이메일을 가지고 회원 정보를 찾는다.
    Member member = memberService.get((String)fbLoginUser.get("email"));

    // 만약 소셜 사용자가 현재 사이트에 가입된 상태가 아니라면 자동으로 가입시킨다.
    // 소셜 사용자 정보를 가지고 필수 회원 정보를 준비한다.
    if (member == null) {
      member = new Member();
      member.setEmail((String)fbLoginUser.get("email"));
      member.setName((String)fbLoginUser.get("name"));
      member.setPassword(UUID.randomUUID().toString());
      member.setAuthNo(1);
      member.setSns_no(3);
      String deft ="default.jpeg";
      member.setPhoto(deft);

      // 소셜 사용자 정보를 DBMS에 등록한다.
      memberService.snsadd(member);
    }
    session.setAttribute("loginUser", member);
    request.setAttribute("email", member.getEmail());

    HashMap<String, Object> content = new HashMap<>();
    content.put("status", "success");
    request.setAttribute("status", "success");
    content.put("member", member);
    return content;
  }


  @GetMapping("authCheck")
  public Object authCheck(HttpSession session) {

    HashMap<String,Object> content = new HashMap<>();

    Member loginUser = (Member) session.getAttribute("loginUser");
    if(loginUser != null) {
      content.put("status", "success");
      content.put("auth", loginUser.getAuth());
    } else {
      content.put("status", "fail");

    }
    return content;
  }

  public boolean accessToken(String token) {
    String header = "Bearer " + token; // Bearer 다음에 공백 추가
    try {
      String apiURL = "https://openapi.naver.com/v1/nid/me";
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Authorization", header);
      int responseCode = con.getResponseCode();
      if(responseCode==200) { //토큰 정상 호출
        return true;
      } else {  // 토큰 비정상        
        return false;
      }
    } catch (Exception e) {
      System.out.println(e);// 예외 호출
      return false;
    }
  }
}






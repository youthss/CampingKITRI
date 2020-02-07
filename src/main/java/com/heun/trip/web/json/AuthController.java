package main.java.com.heun.trip.web.json;
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
import com.heun.trip.domain.Member;
import com.heun.trip.service.FacebookService;
import com.heun.trip.service.MemberService;
 
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
      content.put("message", "�씠硫붿씪 �뾾嫄곕굹 �븫�샇媛� 留욎� �븡�뒿�땲�떎.");
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
    logger.debug("�꽭�뀡 臾댄슚�솕�떆�궡!");
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
      content.put("message", "�삱諛붾Ⅴ吏� �븡�뒗 �넗�겙�엯�땲�떎.");
      return content;
    } 

    Member member = memberService.snsget(email, sns_no);
    request.setAttribute("email", email);
    
    if (member == null) {
      request.setAttribute("status", "fail");
      content.put("status", "fail");
      content.put("message", "�씠硫붿씪�씠 �뾾嫄곕굹 �븫�샇媛� 留욎� �븡�뒿�땲�떎.");
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
    // accessToken�쓣 媛�吏�怨� �럹�씠�뒪遺� �꽌踰꾩뿉 濡쒓렇�씤 �궗�슜�옄�쓽 �젙蹂대�� �슂泥��븳�떎.
    Map fbLoginUser = facebookService.getLoginUser(accessToken);

    // �럹�씠�뒪遺곸뿉�꽌 諛쏆� �궗�슜�옄 �젙蹂�  以묒뿉�꽌 �씠硫붿씪�쓣 媛�吏�怨� �쉶�썝 �젙蹂대�� 李얜뒗�떎.
    Member member = memberService.get((String)fbLoginUser.get("email"));

    // 留뚯빟 �냼�뀥 �궗�슜�옄媛� �쁽�옱 �궗�씠�듃�뿉 媛��엯�맂 �긽�깭媛� �븘�땲�씪硫� �옄�룞�쑝濡� 媛��엯�떆�궓�떎.
    // �냼�뀥 �궗�슜�옄 �젙蹂대�� 媛�吏�怨� �븘�닔 �쉶�썝 �젙蹂대�� 以�鍮꾪븳�떎.
    if (member == null) {
      member = new Member();
      member.setEmail((String)fbLoginUser.get("email"));
      member.setName((String)fbLoginUser.get("name"));
      member.setPassword(UUID.randomUUID().toString());
      member.setAuthNo(1);
      member.setSns_no(3);
      String deft ="default.jpeg";
      member.setPhoto(deft);

      // �냼�뀥 �궗�슜�옄 �젙蹂대�� DBMS�뿉 �벑濡앺븳�떎.
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
    String header = "Bearer " + token; // Bearer �떎�쓬�뿉 怨듬갚 異붽�
    try {
      String apiURL = "https://openapi.naver.com/v1/nid/me";
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Authorization", header);
      int responseCode = con.getResponseCode();
      if(responseCode==200) { //�넗�겙 �젙�긽 �샇異�
        return true;
      } else {  // �넗�겙 鍮꾩젙�긽           
        return false;
      }
    } catch (Exception e) {
      System.out.println(e);// �삁�쇅 �샇異�
      return false;
    }
  }
}






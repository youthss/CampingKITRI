package com.heun.trip.web.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.heun.trip.conf.Gmail;
import com.heun.trip.conf.Sms;
import com.heun.trip.domain.Member;
import com.heun.trip.service.FileService;
import com.heun.trip.service.MemberService;
import com.heun.trip.web.EnRanNo;
import com.heun.trip.web.RanNo;

@RestController("json/MemberController")
@RequestMapping("/json/member")
public class MemberController {

  MemberService memberService;
  ServletContext servletContext;
  Sms sms;
  Gmail gmail;
  FileService fileService;

  public MemberController(MemberService memberService, ServletContext servletContext, Sms sms, Gmail gmail, FileService fileService) {
    this.memberService = memberService;
    this.servletContext= servletContext;
    this.sms = sms;
    this.gmail = gmail;
    this.fileService = fileService;
  }

  @GetMapping("profile")
  public Object profile(HttpSession session) {   
    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");

    if (loginUser != null) {
      Member member = memberService.get(loginUser.getNo());
      content.put("status", "success");
      content.put("member", member);
    } else {
      content.put("fail", "유저 정보가 없습니다.");
    }

    return content;
  }

  @GetMapping("list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="8") int pageSize,
      String val,
      int selector) { 

    HashMap<String,Object> content = new HashMap<>();
    
    if (pageSize < 5 || pageSize > 8) 
      pageSize = 8;

    if(selector == 1) {  //이름
      int rowCount = memberService.namesize(val);
     
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Member> list = memberService.namelist(pageNo, pageSize, selector, val);
       System.out.println(list);
      content.put("list", list);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);

    }
    if(selector == 2) {// 이메일  
      int rowCount = memberService.emailsize(val);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Member> list = memberService.emaillist(pageNo, pageSize, selector, val);
      content.put("list", list);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage); 
    }
    if(selector == 3) {// 권한
      System.out.println("실행");
      int rowCount = memberService.authsize(val);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Member> list = memberService.authlist(pageNo, pageSize, selector, val);
      content.put("list", list);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
    } 
    
    if(selector == 4) {
      int rowCount = memberService.size();
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Member> list = memberService.list(pageNo, pageSize, selector, val);
      content.put("list", list);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);

    }
    return content;
  }

  @GetMapping("detail")
  public Object detail(int no) {
    Member member = memberService.get(no);
    return member;
  }



  @PostMapping("snsadd")
  public Object snsadd(Member member) {

    HashMap<String,Object> content = new HashMap<>();
    StringBuffer ranNo = EnRanNo.randomNo();
    String EnranNo = ranNo.toString();
    String deft = "default.jpeg";
    member.setPhoto(deft);

    try {
      if(memberService.get(member.getEmail()) ==null) {
        member.setPassword(EnranNo);
        memberService.snsadd(member);
        content.put("status", "success");

      } else if(memberService.get(member.getEmail()).getEmail().equals(member.getEmail())){
        System.out.println("진입");
        content.put("status", "overlap");
        content.put("message", "이미 일반회원으로 가입했습니다");
        return content;
      }else {
        content.put("status", "fail");
        content.put("message", "오류");
      }

    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("add")
  public Object add(Member member,
      int ranNo,
      HttpSession session,
      MultipartFile photo) {

    HashMap<String,Object> content = new HashMap<>();
    int checkNo = (int)session.getAttribute("ranNo");
    System.out.println(ranNo);
    Member count = memberService.get(member.getEmail());

    try { 
      if (checkNo == ranNo && count == null){
        if (photo != null) {
          String filename = UUID.randomUUID().toString();
          
          try {
            fileService.uploadImage(photo.getInputStream(), photo.getSize(), filename);
            fileService.uploadImageThumbnail(photo.getInputStream(), 30, 30, filename + "_header");
            fileService.uploadImageThumbnail(photo.getInputStream(), 250, 250, filename + "_profile");
            
          } catch (IllegalStateException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
          
          member.setPhoto(filename);
          
        } 
        memberService.add(member);
        content.put("status", "success");
      } else {
        content.put("status", "fail");
      }
    } catch (Exception e) {
      content.put("message", e.getMessage());
    }
    return content;
  }


  @PostMapping("update")
  public Object update(Member member, HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");

    member.setNo(loginUser.getNo());

    HashMap<String,Object> content = new HashMap<>();
    try {
      if (member.getTel().length() > 0 && !member.getTel().equals(loginUser.getTel())) {
        if (!(boolean) session.getAttribute("pass") || session.getAttribute("pass") == null) {
          throw new Exception("인증이 되지 않았습니다.");
        }
      }
      if (memberService.update(member) > 0) {
        member = memberService.get(loginUser.getNo());
        content.put("status", "success");
        session.setAttribute("loginUser", member);
      } else {
        throw new Exception();
      }
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }



  @PostMapping("updateprofile")
  public Object profileupdate(Member member,  MultipartFile photo, HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();
    member.setNo(loginUser.getNo());
    
    if (photo != null) {
      String filename = UUID.randomUUID().toString();
      try {
        fileService.uploadImage(photo.getInputStream(), photo.getSize(), filename);
        fileService.uploadImageThumbnail(photo.getInputStream(), 30, 30, filename + "_header");
        fileService.uploadImageThumbnail(photo.getInputStream(), 250, 250, filename + "_profile");
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      member.setPhoto(filename);
    } 
    try {
      memberService.profileupdate(member);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }


  @PostMapping("updatepwd")
  public Object updatepwd(Member member, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();


    Member loginUser = (Member) session.getAttribute("loginUser");

    member.setNo(loginUser.getNo());

    try {
      if (memberService.pwdupdate(member) == 0) 
        throw new RuntimeException("해당 번호의 게시물이 없습니다.");
      content.put("status", "success");

    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }


  @PostMapping("Emailupdate")
  public Object Emailupdate(Member member) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      memberService.Emailupdate(member);
      content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("delete")
  public Object delete(int no) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      memberService.delete(no);
      content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("email")
  public Object email(String email,
      HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      int ranNo = RanNo.randomNo();
      String title = "이메일 인증";
      String text = "인증번호는 [" + ranNo + "] 입니다.";

      gmail.gmailSend(email, title, text);

      content.put("status", "success");
      session.setAttribute("ranNo", ranNo);
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("resetemail")
  public Object resetemail(String email) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      StringBuffer EnranNo = EnRanNo.randomNo();
      String title = "임시 비밀번호 발급";
      String text = "임시 비밀번호는 [" + EnranNo + "] 입니다.";

      gmail.gmailSend(email, title, text);

      content.put("status", "success");
      content.put("EnranNo", EnranNo);

    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("sms")
  public Object sms(String tel, HttpSession session) {

    int ranNo = RanNo.randomNo();
    String messageText = "인증번호 [" + String.valueOf(ranNo) + "] 입니다.\n";

    HashMap<String,Object> content = new HashMap<>();

    try {

      if (memberService.getTel(tel) > 0) {
        throw new Exception("이미 등록된 전화번호입니다.");
      }

      Long time = System.currentTimeMillis();
      Long smstime = (Long) session.getAttribute("smstime");

      if (smstime == null) {
        session.setAttribute("smstime", System.currentTimeMillis());
      } else {
        if ((time - smstime) / 1000 < 30) {
          throw new Exception("30초 지나서 다시 요청하시길 바랍니다.");
        } else {
          session.removeAttribute("smstime");
        }
      }

      sms.smsSend(tel, messageText);
      session.setAttribute("sms", String.valueOf(ranNo));
      session.setAttribute("time", System.currentTimeMillis());
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }

    return content;
  }

  @GetMapping("smsConfirm")
  public Object smsConfirm(String number, HttpSession session) {

    HashMap<String,Object> content = new HashMap<>();

    if (((long) System.currentTimeMillis() - (long) session.getAttribute("time")) / 1000 > 60 ||
        !session.getAttribute("sms").equals(number)) {

      content.put("status", "fail");
      session.setAttribute("pass", false);

    } else {

      content.put("status", "success");
      session.setAttribute("pass", true);
      
    }

    return content;
  }

  @GetMapping("checkEmail")
  public Object checkEmail(int ranNo,
      HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    int checkNo = (int)session.getAttribute("ranNo");

    try {
      if(checkNo == ranNo) {
        content.put("status", "success");
      } else {
        content.put("status", "fail");
      }

    }catch (Exception e){
      content.put("message", e.getMessage());
    }
    return content;
  }



}

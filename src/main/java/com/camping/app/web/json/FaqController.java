package com.camping.app.web.json;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.Faq;
import com.camping.app.domain.Member;
import com.camping.app.service.FaqService;
@RestController("json/FaqController")
@RequestMapping("/json/faq")
public class FaqController {

  FaqService faqService;
  public FaqController(FaqService faqService) {
    this.faqService = faqService;
  }

   @PostMapping("add")
   public Object add(Faq faq,
       HttpSession session) {
   HashMap<String,Object> content = new HashMap<>();
  // Member loginUser = (Member) session.getAttribute("loginUser");
//   if(loginUser.getAuth().equals("일반회원") || loginUser.getAuth().equals("호스트")) {
     
//   }else {
//   }
   try {
     faqService.add(faq);
     content.put("status", "success");
   } catch (Exception e) {
     content.put("status", "fail");
   content.put("message", e.getMessage());
   }
   return content;
   }

  @GetMapping("list")
  public Object list(@RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize,HttpSession session) { // localhost:8080/heunheuntrip/app/json/qna/list
    Member loginUser = (Member) session.getAttribute("loginUser");
  
    if (pageSize < 7 || pageSize > 8)
      pageSize = 7;

    int rowCount = faqService.size();
    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;

    if (pageNo < 1)
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;

    // 테스트 코드입니다 아래
    pageNo = 1;
    pageSize = 100;

    List<Faq> allqnas = faqService.list(pageNo, pageSize);

    HashMap<String, Object> content = new HashMap<>();
    content.put("member", loginUser);
    content.put("list", allqnas);
    content.put("pageNo", pageNo);
    content.put("pageSize", pageSize);
    content.put("totalPage", totalPage);

    return content;
  }
  
  @GetMapping("delete")
  public Object delete(int no,HttpSession session) {
    
    Member loginUser = (Member) session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();
    try {
      if(loginUser == null || !loginUser.getAuth().equals("관리자")) {
        throw new Exception("로그인이 되지 않았거나 관리자가 아닙니다.");
       }
    
      faqService.delete(no);
     content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

   @GetMapping("detail")
   public Object detail(int no) {
   Faq faq = faqService.get(no);
   HashMap<String,Object> content = new HashMap<>();
   content.put("faq", faq);
   return content;
   }
  //
  // @GetMapping("relist")
  // public Object reList(int parent, int step) {
  // List<Qna> qna = qnaService.reList(parent, step);
  // HashMap<String,Object> content = new HashMap<>();
  // content.put("list", qna);
  // return content;
  // }

  
   @PostMapping("update")
   public Object update(Faq faq) {
   HashMap<String,Object> content = new HashMap<>();
   try {
   if (faqService.update(faq) == 0)
   throw new RuntimeException("해당 번호의 게시물이 없습니다.");
   
   content.put("status", "success");
  
   } catch (Exception e) {
   content.put("status", "fail");
   content.put("message", e.getMessage());
   }
   return content;
   }
  //


}

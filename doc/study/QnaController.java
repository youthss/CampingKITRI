package com.heun.trip.web.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.heun.trip.domain.Category;
import com.heun.trip.domain.Member;
import com.heun.trip.domain.Qna;
import com.heun.trip.service.QnaService;


@RestController("json/QnaController")
@RequestMapping("/json/qna")
public class QnaController {
  
 QnaService qnaService;
  
  public QnaController(QnaService qnaService) {
    this.qnaService = qnaService;
  }
  
  @PostMapping("add")
  public Object add(Qna qna, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");
    System.out.println(loginUser);
    
    // 테스트코드 입니다.
    int userNo = 1;
    
    if (loginUser != null) {
      userNo = loginUser.getNo();
    }
    qna.setUserNo(userNo);
    
    if (qna.getParent() == 0) {
      qna.setParent(qnaService.maxParent() + 1);
      qna.setOrder(1);
      qna.setStep(1);
    } else {
      qnaService.sorting(qna.getParent(), qna.getOrder());

      qna.setOrder(qna.getOrder() + 1);
      qna.setStep(qna.getStep() + 1);
    }
    
    System.out.println("---> " + qna);
    try {
      qnaService.add(qna);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }
  
  @GetMapping("list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize
      ) { // localhost:8080/heunheuntrip/app/json/qna/list
    
    // 한 블록에 보여질 페이지 갯수를 정한다.
    int blockPage = 3;
    
    int nowBlock = (int) Math.ceil((double) pageNo/(double) blockPage);
    
    int firstPage = (nowBlock * blockPage) - (blockPage - 1);
    
    if (firstPage <= 1) {
      firstPage = 1;
    }
    
    int lastPage = nowBlock * blockPage;
    
    if (pageSize < 1 || pageSize > 6) 
      pageSize = 5;
    
    int rowCount = qnaService.size();
    
    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;
    
    if (pageNo < 1) 
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;
    
    if (totalPage <= lastPage) {
      lastPage = totalPage;
    }
    
    if (pageNo > lastPage) {
      pageNo = lastPage;
    }
    
    
    List<Qna> hostqnas = qnaService.list(pageNo, pageSize);
    
    HashMap<String,Object> content = new HashMap<>();
    content.put("list", hostqnas);
    content.put("pageNo", pageNo);
    content.put("pageSize", pageSize);
    content.put("totalPage", totalPage);
    content.put("firstPage", firstPage);
    content.put("lastPage", lastPage);

    return content;
  }
  
  @GetMapping("detail")
  public Object detail(int no) {
   Qna qna = qnaService.get(no);
    return qna;
  }
  
  @GetMapping("relist")
  public Object reList(int parent, int step) {
    List<Qna> qna = qnaService.reList(parent, step);
    HashMap<String,Object> content = new HashMap<>();
    content.put("list", qna);
    return content;
  }
  
  @GetMapping("categorylist")
  public Object categoryList() {
    List<Category> categories = qnaService.getCategory();
    Map<String,Object> content = new HashMap<>();
    content.put("category", categories);
    return content;
  }
 
  

 @PostMapping("update")
  public Object update(Qna qna) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      if (qnaService.update(qna) == 0) 
        throw new RuntimeException("해당 번호의 게시물이 없습니다.");
      content.put("status", "success");
      
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }
 
 
 @GetMapping("delete")
 public Object delete(int no, int parent, int order) {
 
   HashMap<String,Object> content = new HashMap<>();
   
   try {
     if (qnaService.delete(no, parent, order)  == 0) 
       throw new RuntimeException("해당 번호의 게시물이 없습니다.");
     content.put("status", "success");
     
   } catch (Exception e) {
     content.put("status", "fail");
     content.put("message", e.getMessage());
   }
   return content;
 }
 
  
  
  
}

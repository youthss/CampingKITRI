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
    System.out.println(qna);

    // 서버 재시작되면 로그인된 사용자가 계속 널됨 그래서 디폴트 홍길동입니다.
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
      @RequestParam(defaultValue="10") int pageSize,
      int selector, String val
    
      ) { // localhost:8080/heunheuntrip/app/json/qna/list

    HashMap<String,Object> content = new HashMap<>();
    if (pageSize < 1 || pageSize > 11) 
      pageSize = 10;

    
    if(selector == 1) {
      int rowCount = qnaService.size();
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Qna> list = qnaService.list(pageNo, pageSize, selector, val);
      content.put("list", list);
 
      content.put("totalPage", totalPage);

    }
    if(selector == 2) {// 작성자
      int rowCount = qnaService.namesize(val);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Qna> list = qnaService.namelist(pageNo, pageSize, selector, val);
      content.put("list", list);
 
      content.put("totalPage", totalPage); 
    }
    if(selector == 3) {// 제목
      int rowCount = qnaService.titlesize(val);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Qna> list = qnaService.titlelist(pageNo, pageSize, selector, val);
      content.put("list", list);
 
      content.put("totalPage", totalPage);
    }
    if(selector == 4) {// 작성자 + 제목
      int rowCount = qnaService.titlenamesize(val);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Qna> list = qnaService.titlenamelist(pageNo, pageSize, selector, val);
      content.put("list", list);
      content.put("totalPage", totalPage);
    }
    
    content.put("pageNo", pageNo);
    content.put("pageSize", pageSize);
    

    return content;
  }

  @GetMapping("detail")
  public Object detail(int no, HttpSession session) {
    Qna qna = qnaService.get(no);
    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");
    if(loginUser != null) {
      content.put("userNo", loginUser.getNo());
      content.put("auth", loginUser.getAuth());
    }
    content.put("qna", qna);

    return content;
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


 

  @PostMapping("password")
  public Object password(int qnaNo, String pwd) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      if(qnaService.password(qnaNo, pwd) == 1) {
        content.put("status", "success");
      } else {
        content.put("status", "fail");
      }
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("pwdCheck")
  public Object password(int qnaNo) {
    HashMap<String,Object> content = new HashMap<>();
    System.out.println(qnaNo);
    try {
      if(qnaService.pwdCheck(qnaNo) == 1) {
        content.put("status", "success");
      } else {
        content.put("status", "fail");
      }
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


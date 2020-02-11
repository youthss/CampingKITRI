package com.camping.app.web.json;
 
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.Member;
import com.camping.app.domain.Riw;

import com.camping.app.service.RiwService;
import com.camping.app.service.RoomService;

@RestController("json/RiwController")
@RequestMapping("/json/riw")
public class RiwController {

  RiwService riwService;
  RoomService roomSerive;
  
  public RiwController(RiwService riwService, RoomService roomSerive) {
    this.riwService = riwService;
    this.roomSerive = roomSerive;
  }

  @PostMapping("add")
  public Object add(Riw riw, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    Member member = (Member)session.getAttribute("loginUser");

    System.out.println(riw);

    int no = member.getNo();
    riw.setUserNo(no);
    
    

    try {
      riwService.add(riw);  //원글을 먼저 입력한다. 
      int sum = riwService.sumroomgrd(riw.getRoomNo()); //원글을 포함하여 그숙소에 관한 평점의 합계를 구한다.
      int usergrd = riwService.grdpeople(riw);         //그숙소의 평점을 날린 사람 수를 구한다.
      
      int roomgrd = (sum / usergrd);
      System.out.println(roomgrd);
      
      roomSerive.grdupdate(roomgrd, riw.getRoomNo());
      
      
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
      @RequestParam(defaultValue="15") int pageSize
      ) { // localhost:8080/heunheuntrip/app/json/qna/list

    HashMap<String,Object> content = new HashMap<>();

    try {

      if (pageSize < 1 || pageSize > 16) 
        pageSize = 15;

      int rowCount = riwService.size();
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;

      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;

      List<Riw> hostqnas = riwService.list(pageNo, pageSize);

      content.put("list", hostqnas);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");

    } catch (Exception e) {
      content.put("status", "fail");
    }

    return content;
  }

  @GetMapping("listMypage")
  public Object listMypage(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize,
      HttpSession session
      ) { // localhost:8080/heunheuntrip/app/json/qna/listMypage

    Member member = (Member)session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();

    try {
      int userNo = member.getNo();

      if (pageSize < 1 || pageSize > 6) 
        pageSize = 5;

      int rowCount = riwService.size(userNo);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;

      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;

      List<Riw> list = riwService.listMypage(pageNo, pageSize, userNo);

      content.put("list", list);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");

    } catch (Exception e) {
      content.put("status", "fail");
    }

    return content;
  }

  @GetMapping("listhostMypage")
  public Object hostlistMypage(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize,
      HttpSession session
      ) { // localhost:8080/heunheuntrip/app/json/riw/listhostMypage

    Member member = (Member)session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();

    try {
      int userNo = member.getNo();
      String uname = member.getName();

      // 유저 번호로 방번호를 알아온다.     

      if (pageSize < 1 || pageSize > 6) 
        pageSize = 5;

      int rowCount = riwService.countAllHost(userNo);
      System.out.println(rowCount + "--------------------------------------------------------------------------------");
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;

      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;

      List<Riw> list = riwService.hostlistMypage(pageNo, pageSize, userNo);

      content.put("list", list);
      content.put("uname", uname);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");

    } catch (Exception e) {
      content.put("status", "fail");
    }

    return content;
  }


  @PostMapping("delete")
  public Object delete(int no) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      riwService.delete(no);
      content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }


  @PostMapping("replydelete")
  public Object replydelete(int no) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      riwService.replydelete(no);
      content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }


  @GetMapping("detail")
  public Object detail(int no) {
    Riw riw = riwService.get(no);
    HashMap<String,Object> content = new HashMap<>();
    content.put("riw", riw);
    return content;
  }

  @PostMapping("update")
  public Object update(Riw riw) {
    HashMap<String,Object> content = new HashMap<>();

    if(Float.parseFloat(riw.getGrd()) == 0) {
      Riw riw2 = riwService.get(riw.getNo());
      riw.setGrd(riw2.getGrd());
    }

    try {
      if (riwService.update(riw) == 0) 
        throw new RuntimeException("해당 번호의 게시물이 없습니다.");
      content.put("status", "success");

    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }


  @PostMapping("reply")
  public Object reply(Riw riw, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();

    System.out.println(riw);

    try {
      riwService.replyupdate(riw);   // reply부분만 추

      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }


  @PostMapping("countAllHost")
  public Object countAllHost(int no) {
    HashMap<String,Object> content = new HashMap<>();

    try {

      int riwNo = riwService.countAllHost(no);

      System.out.println("리뷰수=----->" + riwNo);

      content.put("riwNo", riwNo);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }
}

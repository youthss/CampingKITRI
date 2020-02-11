package com.camping.app.web.json;
 
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.Bookmark;
import com.camping.app.domain.Member;
import com.camping.app.service.BookmarkService;

@RestController("json/bookMarkController")
@RequestMapping("/json/bookmark")
public class bookmarkController {

  BookmarkService bookmarkService;

  public bookmarkController(BookmarkService bookmarkService) {
    this.bookmarkService = bookmarkService;
  }

  @GetMapping("list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize, 
      HttpSession session
      ) { // localhost:8080/heunheuntrip/app/json/qna/list

    Member member = (Member)session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();

    System.out.println(member);
    
    try {
      int userNo = member.getNo();
      
      if (pageSize < 1 || pageSize > 6) 
        pageSize = 5;

      int rowCount = bookmarkService.size(userNo);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;

      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;

      List<Bookmark> list = bookmarkService.list(pageNo, pageSize, userNo);

      content.put("list", list);
      content.put("status", "success");
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
    } catch (Exception e) {
      content.put("status", "fail");
    }
    return content;
  }

  @PostMapping("add")
  public Object add(Bookmark bookmark, HttpSession session) { 
    HashMap<String,Object> content = new HashMap<>();
    Member member = (Member)session.getAttribute("loginUser");
    try {
      if(bookmark.getMemo() == null) {
        bookmark.setMemo("");
      }

      int no = member.getNo();
      bookmark.setUserNo(no);

      bookmarkService.add(bookmark);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("update")
  public Object update(String contents, int no, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    Member member = (Member)session.getAttribute("loginUser");

    int userNo = member.getNo();

    try {
      bookmarkService.update(contents, userNo, no);
      content.put("status", "success");

    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("delete")
  public Object delete(int roomNo, HttpSession session) { 
    HashMap<String,Object> content = new HashMap<>();
    Member member = (Member)session.getAttribute("loginUser");

    int userNo = member.getNo();

    try {
      bookmarkService.delete(userNo, roomNo);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("count")
  public Object count(int roomNo, HttpSession session) { 
    HashMap<String,Object> content = new HashMap<>();
    Member member = (Member)session.getAttribute("loginUser");

    try {
      int userNo = member.getNo();
      int countNo = bookmarkService.count(userNo, roomNo);
      content.put("status", "success");
      content.put("countNo", countNo);
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

}

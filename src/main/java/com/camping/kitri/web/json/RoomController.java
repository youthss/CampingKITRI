package com.camping.kitri.web.json;
   
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camping.kitri.domain.Convenience;
import com.camping.kitri.domain.Member;
import com.camping.kitri.domain.Riw;
import com.camping.kitri.domain.Room;
import com.camping.kitri.domain.RoomFile;
import com.camping.kitri.domain.Safety;
import com.camping.kitri.service.FileService;
import com.camping.kitri.service.RevService;
import com.camping.kitri.service.RiwService;
import com.camping.kitri.service.RoomService;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
@RestController("json/RoomController")
@RequestMapping("/json/room")
public class RoomController {

  RoomService roomSerive;
  ServletContext servletContext;
  RiwService riwService;
  FileService fileService;
  RevService revService;
  
  public RoomController(RoomService roomSerive, ServletContext servletContext, RiwService riwService, 
      FileService fileService, RevService revService) {
    this.roomSerive = roomSerive;
    this.servletContext = servletContext;
    this.riwService = riwService;
    this.fileService = fileService;
    this.revService = revService;
  }

  @SuppressWarnings("unused")
  @PostMapping("add")
  public Object add(
      Room room, BindingResult result,
      @RequestParam("convenience[]") int[] convenience,
      @RequestParam("safety[]") int[] safety,
      @RequestParam("files[]") String[] files,
      HttpSession session) {

    List<Convenience> cons = new ArrayList<>();
    List<Safety> safes = new ArrayList<>();
    List<RoomFile> roomFiles = new ArrayList<>();

    Map<String,Object> content = new HashMap<>();
    
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");
      System.out.println(loginUser);
      System.out.println(loginUser.getAuthNo());
      
      if (loginUser != null) {
        room.setHostNo(loginUser.getNo());
      } else {
        throw new Exception("로그인 해주세요!");
      }
      
      if (loginUser.getAuthNo() != 2) {
        throw new Exception("호스트가 아닙니다!");
      }

      for (int c : convenience) {
        Convenience con = new Convenience();
        con.setNo(c);
        cons.add(con);
      }
      for (int c : safety) {
        Safety safe = new Safety(); 
        safe.setNo(c);
        safes.add(safe);
      }
      for (String f : files) {
        RoomFile file = new RoomFile();
        file.setName(f);
        roomFiles.add(file);
      }

      room.setConveniences(cons);
      room.setSafeties(safes);
      room.setPhotos(roomFiles);

      roomSerive.add(room);
      content.put("status", "success");
      
    } catch (Exception e) {
      e.printStackTrace();
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }

    return content;
  }

  @PostMapping("update")
  public Object update(
      Room room, BindingResult result,
      @RequestParam("convenience[]") int[] convenience,
      @RequestParam("safety[]") int[] safety,
      @RequestParam("files[]") String[] files,
      HttpSession session) {
   
    List<Convenience> cons = new ArrayList<>();
    List<Safety> safes = new ArrayList<>();
    List<RoomFile> roomFiles = new ArrayList<>();

    Map<String,Object> content = new HashMap<>();
    
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");

      if (loginUser != null) {
        room.setHostNo(loginUser.getNo());
      } else {
        throw new Exception("로그인 해주세요!");
      }
      
      if (loginUser.getAuthNo() != 2) {
        throw new Exception("호스트가 아닙니다!");
      }

      for (int c : convenience) {
        Convenience con = new Convenience();
        con.setNo(c);
        cons.add(con);
      }
      for (int c : safety) {
        Safety safe = new Safety();
        safe.setNo(c);
        safes.add(safe);
      }
      for (String f : files) {
        RoomFile file = new RoomFile();
        file.setName(f);
        roomFiles.add(file);
      }

      room.setConveniences(cons);
      room.setSafeties(safes);
      room.setPhotos(roomFiles);
      roomSerive.update(room);
      content.put("status", "success");
      
    } catch (Exception e) {
      e.printStackTrace();
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }

    return content;
  }
  
  
  
  @PostMapping("fileAdd")
  public Object fileAdd(@RequestParam(value="files[]", required=false) MultipartFile[] files, boolean isMain) {
    Map<String, Object> content = new HashMap<>();

    String filename = UUID.randomUUID().toString();
    
    for (MultipartFile f : files) {
      if (!f.isEmpty()) {
        // 메인사진이 아니면 그냥 저장한다.
        try {
          fileService.uploadImage(f.getInputStream(), f.getSize(), filename);
          fileService.uploadImageThumbnail(f.getInputStream(), 580, 386, filename + "_veiwthum");
        } catch(Exception e) {
          e.printStackTrace();
        }
        // 메인사진이면 섬네일로 만든 후 맵에 담는다.
        if (isMain) {
          try {
            fileService.uploadImageThumbnail(f.getInputStream(), 530, 375, filename + "_thum");
            fileService.uploadImageThumbnail(f.getInputStream(), 580, 386, filename + "_veiwthum");
            content.put("thumbnail", filename + "_thum");
          } catch(Exception e) {
            e.printStackTrace();
          }
        } else {
          // 메인사진이 아니면 키값을 다르게 하고 담는다.
          content.put("photo", filename);
        }
      }
    }
    return content;
  } 
 
  @GetMapping("list")
  public Object list(
      @RequestParam(defaultValue="0") int pageNo,
      @RequestParam(defaultValue="12") int pageSize,
      int a,
      String lati,
      HttpSession session,
      String longi
      ) { // localhost:8080/heunheuntrip/app/json/room/review

    Member loginUser = (Member)session.getAttribute("loginUser");
   
    if (pageSize < 1 || pageSize > 12) 
      pageSize = 12;

    int rowCount = roomSerive.size(lati, longi);

    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;
    
    if (pageNo < 1) 
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;

    HashMap<String,Object> content = new HashMap<>();
    
    if(loginUser != null) {
      content.put("loginNo", loginUser.getNo());
    }
    
    if(a == 2) {
      List<Room> rooms = roomSerive.hpricelist(pageNo, pageSize, lati, longi);
      content.put("list", rooms);
      
    } if (a == 3) {
      List<Room> rooms = roomSerive.rpricelist(pageNo, pageSize, lati, longi);
      content.put("list", rooms);

    } if (a == 4) {
      List<Room> rooms = roomSerive.hotlist(pageNo, pageSize, lati, longi);
      content.put("list", rooms);

    } if (a == 1 || a == 0) {
      List<Room> rooms = roomSerive.list(pageNo, pageSize, lati, longi);
      content.put("list", rooms);

    } 
    
    content.put("pageNo", pageNo);
    content.put("pageSize", pageSize);
    content.put("totalPage", totalPage);
   
    return content;
  }

  @GetMapping("detail")
  public Object detail(int no) {
    Room room = roomSerive.get(no);
    room.setReservationHistory(revService.reservationHistory(no));
    System.out.println(room);
    return room;
  }
  
  @GetMapping("hostroomdetail")
  public Object hostroomdetail(int no) {
    Room room = roomSerive.roomGet(no);
    System.out.println(room);
    return room;
  }
  

  @GetMapping("hostroom")
  public Object hostroom(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="4") int pageSize,
      int no) {
    HashMap<String, Object> contents = new HashMap<>();
    
    if (pageSize < 1 || pageSize > 5) 
      pageSize = 4;

      int rowCount = roomSerive.hostsize(no);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      
      
      List<Room> list = roomSerive.hostroomlist(pageNo, pageSize,no);

      if(list.size() != 0) {
        
        contents.put("list", list);
        contents.put("pageNo", pageNo);
        contents.put("pageSize", pageSize);
        contents.put("totalPage", totalPage);
        contents.put("status", "success");
        
      } else {
        contents.put("status", "fail");
      }
      
    
    return contents;
  }
  
  @GetMapping("managerroom")
  public Object managerRoom(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="4") int pageSize,
      int no) {
    HashMap<String, Object> contents = new HashMap<>();
    if (pageSize < 1 || pageSize > 5) 
      pageSize = 4;

      int rowCount = roomSerive.cecoRoomsize(no);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;
      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;
      List<Room> list = roomSerive.cecoRoomlist(pageNo, pageSize,no);
      if(list.size() != 0) {
      contents.put("list", list);
      contents.put("pageNo", pageNo);
      contents.put("pageSize", pageSize);
      contents.put("totalPage", totalPage);
      contents.put("status", "success");
      } else {
        contents.put("status", "fail");
      }
    return contents;
  }
  
  @PostMapping("cecoupdate")
  public Object cecoUpdate(int no,
      @RequestParam(defaultValue="") String msg,
      HttpSession session) {

    HashMap<String,Object> content = new HashMap<>();
    try {
        roomSerive.cecoRoomUpdate(no, msg);
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
      roomSerive.delete(no);
     content.put("status", "success");
    }catch (Exception e){
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  
  @GetMapping("review")
  public Object hostlistMypage(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="2") int pageSize,
      HttpSession session, int no) {
    
    HashMap<String,Object> content = new HashMap<>();

    try {
    if (pageSize < 1 || pageSize > 3) 
      pageSize = 2;

    int rowCount = riwService.reviewsize(no);
    System.out.println(rowCount);
    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;

    if (pageNo < 1) 
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;
   
    Member member = (Member)session.getAttribute("loginUser");
    String hostname = member.getName(); // 나중에 쓸겁니다. 호스트인지 일반인지 구별할때
   
    List<Riw> list = riwService.roomreview(no, pageNo, pageSize);
    System.out.println(list);
    content.put("list", list);
    content.put("hostname", hostname);
    content.put("pageNo", pageNo);
    content.put("pageSize", pageSize);
    content.put("totalPage", totalPage);
    
    } catch (Exception e) {

    }
  

    return content;
  }
  
  
  @PostMapping("addriw")
  public Object update(Riw riw, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    Member member = (Member)session.getAttribute("loginUser");
    riw.setUserNo(member.getNo());
    try {
      if (riwService.addriw(riw) == 0) 
        throw new RuntimeException("해당 번호의 게시물이 없습니다.");
      content.put("status", "success");
      
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }
}

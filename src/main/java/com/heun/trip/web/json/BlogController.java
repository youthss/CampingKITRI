package com.heun.trip.web.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.heun.trip.domain.Blike;
import com.heun.trip.domain.Blog;
import com.heun.trip.domain.BlogFile;
import com.heun.trip.domain.Member;
import com.heun.trip.domain.Roomcheckout;
import com.heun.trip.service.BlogService;
import com.heun.trip.service.FileService;

@RestController("json/BlogController")
@RequestMapping("/json/blog")
public class BlogController {

  @Autowired BlogService blogService;
  @Autowired ServletContext servletContext;
  @Autowired FileService fileService;


  @PostMapping("add")
  public Object add(Blog blog,@RequestParam(value="filenames[]") String[] filenames, HttpSession session) throws IOException {


    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");

    List<BlogFile> photoFiles = new ArrayList<>();

    for (String s : filenames) {

      if (s.contains("_thum")) { 
        blog.setMainPhoto(s);
        continue;
      }

      if (!blog.getContent().contains(s)) {
        try {
          fileService.deleteImage(s);
        } catch (Exception e) {
        }
        continue;
      }

      BlogFile file = new BlogFile();
      file.setFile(s);
      photoFiles.add(file);
    }

    blog.setPhotoFiles(photoFiles);

    // 로긴 유저 정보 저장
    blog.setUserNo(loginUser.getNo());

    try {
      blogService.add(blog);
      content.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }

    return content;
  }

  @PostMapping("update")
  public Object update(Blog blog,@RequestParam(value="filenames[]") String[] filenames, HttpSession session) throws IOException {

    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");

    List<BlogFile> photoFiles = new ArrayList<>();

    for (String s : filenames) {

      if (s.contains("_thum")) {
        blog.setMainPhoto(s);
        continue;
      }

      if (!blog.getContent().contains(s)) {
        try {
          fileService.deleteImage(s);
        } catch (Exception e) {
        }
        continue;
      }

      BlogFile file = new BlogFile();
      file.setFile(s);
      photoFiles.add(file);
    }

    blog.setPhotoFiles(photoFiles);

    // 로긴 유저 정보 저장
    blog.setUserNo(loginUser.getNo());

    System.out.println(blog);

    try {
      blogService.update(blog);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }

    return content;
  }

  @PostMapping("addfile")
  public Object addFile(MultipartFile[] file, boolean isMain) {

    String filename = UUID.randomUUID().toString();

    for (MultipartFile f : file) {
      if (!f.isEmpty()) {
        try {
          fileService.uploadImage(f.getInputStream(), f.getSize(), filename);
        } catch(Exception e) {
          e.printStackTrace();
        }
        if (isMain) {
          try {
            filename = filename + "_thum";
            fileService.uploadImageThumbnail(f.getInputStream(), 350, 450, filename);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    return filename;
  }

  @GetMapping("list")
  public Object list(@RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="8") int pageSize,
      @RequestParam(value="order") int order,
      @RequestParam(value="blike") int blike,
      @RequestParam(value="deorder") int deorder
      ) { // localhost:8080/heunheuntrip/app/json/blog/list

    HashMap<String,Object> content = new HashMap<>();
    //System.out.println(order);
  try {
    if (pageSize < 1 || pageSize > 8) 
      pageSize = 7;

    int rowCount = blogService.size();

    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;

    if (pageNo < 1) 
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;


    //여기서 리스트를 뿌려주는 형식이 정해짐
    if(order == 1) {
      List<Blog> blogs = blogService.order(pageNo, pageSize);
      content.put("list", blogs);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");
    } else if(blike == 1) {
      List<Blog> blogs = blogService.likebylist(pageNo, pageSize);
      content.put("list", blogs);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");
    } else if (deorder == 1) {
      List<Blog> blogs = blogService.deorder(pageNo, pageSize);
      content.put("list", blogs);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");
    } else {      
      List<Blog> blogs = blogService.list(pageNo, pageSize);
      content.put("list", blogs);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");
    }
  } catch (Exception e) {
    
    content.put("status", "fail");
  }
    return content;
  }

  @GetMapping("indexList")
  public Object indexList(
//      @RequestParam(defaultValue="1") int pageNo,
//      @RequestParam(defaultValue="3") int pageSize
      ) { // localhost:8080/heunheuntrip/app/json/blog/indexList
    
    HashMap<String,Object> content = new HashMap<>();
    
    try {
      
//      if (pageSize < 1 || pageSize > 3) 
//        pageSize = 3;
//
//      int rowCount = blogService.size();
//
//      int totalPage = rowCount / pageSize;
//      if (rowCount % pageSize > 0)
//        totalPage++;
//
//      if (pageNo < 1) 
//        pageNo = 1;
//      else if (pageNo > totalPage)
//        pageNo = totalPage;
//      
      List<Blog> blogs = blogService.listIndex();
      
      int count = 1;
      
      Map<String,Object> lists = new HashMap<>();
      
      ArrayList<Blog> b = new ArrayList<>();
      
      for (int i = 0; i < blogs.size(); i++) {
        b.add(blogs.get(i));
        if ((i + 1) % 3 == 0) {
          lists.put("list" + count, b.toArray());
          b.clear();
          count++;
        }
      }
      content.put("lists", lists);
//      content.put("pageNo", pageNo);
//      content.put("pageSize", pageSize);
//      content.put("totalPage", totalPage);
      
    } catch (Exception e) {
      
      content.put("fail", "데이터가 존재하지 않습니다.");
      e.printStackTrace();
      
    }
    return content;
  }


  @GetMapping("detail")
  public Object detail(int no, HttpSession session) {
    Blog blog = blogService.get(no);
    int countNo = blogService.countLike(no);

    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");

    System.out.println("로긴한사람~ ===> "  +  loginUser);
    if(loginUser != null) {
      content.put("loginNo", loginUser.getNo());
    }
    content.put("blog", blog);
    content.put("count", countNo);

    return content;
  }

  @GetMapping("delete")
  public Object delete(int no) {

    HashMap<String,Object> content = new HashMap<>();

    List<BlogFile> files = blogService.filelist(no);

    for(BlogFile f : files) {
      String filename = f.getFile();
      try {
        fileService.deleteImage(filename);
      } catch (Exception e) {}
    }

    try {
      if (blogService.delete(no) == 0) { 
        throw new RuntimeException("해당 번호의 게시물이 없습니다.");
      }
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("checkBlike")
  public Object checkBlike(Blike blike) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      int no = blogService.checkLike(blike);
      System.out.println(no);
      content.put("blike", no);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("increaseLike")
  public Object increaseLike(Blike blike) {
    HashMap<String,Object> content = new HashMap<>();

    try {
      blogService.increaseLike(blike);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("decreaseLike")
  public Object decreaseLike(Blike blike) {
    HashMap<String,Object> content = new HashMap<>();

    try {
      blogService.decreaseLike(blike);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("countLike")
  public Object countLike(int no) {
    HashMap<String,Object> content = new HashMap<>();

    try {
      int count = blogService.countLike(no);
      content.put("count", count);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("checkUser")
  public Object checkUser(HttpSession session) { // localhost:8080/heunheuntrip/app/json/blog/checkUser
    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");
    System.out.println(loginUser);
    try {
      if (loginUser != null) {
        int userNo = loginUser.getNo();
        String userName = loginUser.getName();
        content.put("userNo", userNo);
        content.put("userName", userName);
        if(blogService.checkRev(userNo) > 0) {
          content.put("status", "success");
        } else {
          content.put("status", "fail");
        }
      }
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @PostMapping("checkView")
  public Object checkView(Blike blike) {
    HashMap<String,Object> content = new HashMap<>();
    System.out.println(blike);

    int no = blogService.checkView(blike);
    System.out.println(no);

    if(no == 0) {
      blogService.createLike(blike);
    }

    try {
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("roomCheckOut")
  public Object roomCheckOut(HttpSession session) {
    Map<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      int userNo = loginUser.getNo();
      String userName = loginUser.getName();
      content.put("userNo", userNo);
      content.put("userName", userName);
      List<Roomcheckout> roomCheckOut = blogService.roomCheckOut(userNo);
      content.put("list", roomCheckOut);
    }
    return content;
  }

}

package com.camping.app.service.impl;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import com.camping.app.dao.BlogDao;
import com.camping.app.dao.BlogFileDao;
import com.camping.app.domain.Blike;
import com.camping.app.domain.Blog;
import com.camping.app.domain.BlogFile;
import com.camping.app.domain.Roomcheckout;
import com.camping.app.service.BlogService;


@Service
public class BlogServiceImpl implements BlogService {

  BlogDao blogDao;
  BlogFileDao blogFileDao;
  

  public BlogServiceImpl(BlogDao blogDao, BlogFileDao blogFileDao) {
    this.blogDao = blogDao;
    this.blogFileDao = blogFileDao;
  }

  @Override
  public List<Blog> list(int pageNo, int pageSize) {
    
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    
    return blogDao.findAll(params);
  }
  
  @Override
  public List<Blog> listIndex() {
    
//    HashMap<String,Object> params = new HashMap<>();
//    params.put("size", pageSize);
//    params.put("rowNo", (pageNo - 1) * pageSize);
    
    return blogDao.findAllIndex();
  }
  
  @Override
  public List<Blog> order(int pageNo, int pageSize) {
  
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    
    return blogDao.orderbylist(params);
  }
  
  @Override
  public List<Blog> deorder(int pageNo, int pageSize) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    return blogDao.deorderbylist(params);

  }

  @Override
  public List<Blog> likebylist(int pageNo, int pageSize) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    return blogDao.likebylist(params);
  }

  
  @Override
  public int add(Blog blog) {
    blogDao.insert(blog);
    
    if(blog.getPhotoFiles().size() > 0) {
      List<BlogFile> files = blog.getPhotoFiles();
      for (BlogFile f : files) {
        f.setBlogNo(blog.getNo());
      }
      blogFileDao.insert(files);
    }
    
    System.out.println("서비스====> " + blog);
    return 1;
  }

  @Override
  public int update(Blog blog) {
    
    // 메인사진 업데이트 안할 시 기존 데이터에서 가져온다.
    if(blog.getMainPhoto() == null) {
      Blog blogmain = blogDao.findByNo(blog.getNo());
      String mainPhoto = blogmain.getMainPhoto();
      blog.setMainPhoto(mainPhoto);
    }
    
    // 기존 사진을 DB에서 제거한다.
    blogFileDao.delete(blog.getNo());
    
    // 내용에 들어간 이미지 파일을 불러와서 photoboard db에 넣어준다.
    List<BlogFile> files = blog.getPhotoFiles();
    List<BlogFile> realfiles = new ArrayList<>();
    
    for (BlogFile f : files) {
      System.out.println(f.getFile());
      if(f.getFile() == "") {
        continue;
      }
      f.setBlogNo(blog.getNo());
      realfiles.add(f);
    }
    
    blogDao.update(blog);
    
    blogFileDao.insert(realfiles);
    
    return 1;
  }

  @Override
  public Blog get(int no) {
    return blogDao.findByNo(no);
  }
  
  @Override
  public List<BlogFile> filelist(int no){
    return blogFileDao.findByNo(no);
  }

  @Override
  public int delete(int no) {
    // file 삭제
    blogFileDao.delete(no);
    
    // 좋아요 컬럼 삭제
    blogDao.deletelike(no);
    
    return blogDao.delete(no);
  }

  @Override
  public int size() {
    return blogDao.countAll();
  }
       
  @Override
  public int checkRev(int no) {
    return blogDao.checkRev(no);
  }

  @Override
  public int checkView(Blike blike) {
    return blogDao.checkView(blike);
  }

  @Override
  public List<Roomcheckout> roomCheckOut(int no) {
    return blogDao.roomCheckOut(no);
  }
  
  @Override
  public int increaseLike(Blike blike) {
    
    return blogDao.increaseLike(blike);
  }

  @Override 
  public int decreaseLike(Blike blike) {
    return blogDao.decreaseLike(blike);
  }

  @Override
  public int checkLike(Blike blike) {
    return blogDao.checkLike(blike);
  }

  @Override
  public int createLike(Blike blike) {
    return blogDao.createLike(blike);
  }
  
  @Override
  public int countLike(int no) {
    return blogDao.countLike(no);
  }
}








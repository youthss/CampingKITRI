package com.camping.app.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import com.camping.app.dao.MemberDao;
import com.camping.app.domain.Member;
import com.camping.app.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

  MemberDao memberDao;

  public MemberServiceImpl(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public List<Member> list(int pageNo, int pageSize,int selector, String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("selector", selector);
    params.put("val", val);
    return memberDao.findAll(params);
  }

  @Override
  public int add(Member member) {

    if(member.getAuth().equals("2")) {
      HashMap<String,Object> params = new HashMap<>();
      try {
        memberDao.insert(member);
        params.put("no", member.getNo());
        params.put("bank", member.getBank());
        params.put("bnkno", member.getBnk_no());
      } catch (Exception e) {
        e.printStackTrace();
      }
      return memberDao.bankinsert(params);     
    }
    return memberDao.insert(member);
  }


  @Override
  public Member get(int no) {
    return memberDao.findByNo(no);
  }

  @Override
  public int update(Member member) {

    HashMap<String,Object> params = new HashMap<>();

    Member loginUser = memberDao.findByNo(member.getNo());

    params.put("no", member.getNo());

    if (member.getName().length() > 0 && !member.getName().equals(loginUser.getName())) {
      params.put("name", member.getName());
      return memberDao.update(params);
    }
    if (member.getTel().length() > 0 && !member.getTel().equals(loginUser.getTel())) {
      params.put("tel", member.getTel());
      return memberDao.update(params);
    } 
    if (member.getName().length() <= 0 && member.getName().equals(loginUser.getName()) &&
        member.getTel().length() <= 0 && member.getTel().equals(loginUser.getTel())) {
      return 0;
    }
    return memberDao.update(params);
  }


  @Override
  public int profileupdate(Member member) {
    HashMap<String,Object> params = new HashMap<>();

    Member loginUser = memberDao.findByNo(member.getNo());

    params.put("no", member.getNo());
    params.put("photo",member.getPhoto());

    if (member.getName().length() > 0 && !member.getName().equals(loginUser.getName())) {
      params.put("name", member.getName());
    }
    if (member.getTel().length() > 0 && !member.getTel().equals(loginUser.getTel())) {
      params.put("tel", member.getTel());
    } 
    if (member.getName().length() <= 0 && member.getName().equals(loginUser.getName()) &&
        member.getTel().length() <= 0 && member.getTel().equals(loginUser.getTel())) {
      return 0;
    }
    return memberDao.profileupdate(params);
  }

  @Override
  public int pwdupdate(Member member) {
    HashMap<String,Object> params = new HashMap<>();


    params.put("no", member.getNo());
    
    if (member.getPassword().length() > 0) {
      params.put("password", member.getPassword());
    } else {
      return 0;
    }
    return memberDao.profileupdate(params);
  }



  @Override
  public int Emailupdate(Member member) {
    return memberDao.Emailupdate(member);
  }

  @Override
  public int delete(int no) {
    return memberDao.delete(no);
  }

  @Override
  public String getTel(int no) {
    return memberDao.extractHostTel(no);
  }

  @Override
  public Member get(String email, String password) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);

    return memberDao.findByEmailPassword(paramMap);
  }
  @Override
  public Member snsget(String email, int sns_no) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("sns_no",sns_no);

    return memberDao.findByEmailSns(paramMap);
  }

  @Override
  public int size() {
    return memberDao.countAll();
  }

  @Override
  public int snsadd(Member member) {
    if (member.getSns_no() == 1 || member.getSns_no() == 2) {
      return memberDao.snsinsert(member);
    } else {
      return memberDao.fbinsert(member);
    }
  }

  @Override
  public Member get(String email) {

    return memberDao.findByEmail(email);
  }

  @Override
  public int getTel(String tel) {
    return memberDao.findByTel(tel);
  }
  @Override
  public int namesize(String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("val", val);
   
    return memberDao.namecountAll(params);
  }
  @Override
  public int emailsize(String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("val", val);
   
    return memberDao.emailcountAll(params);
  }
  @Override
  public int authsize(String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("val", val);
   
    return memberDao.authcountAll(params);
  }

  @Override
  public List<Member> namelist(int pageNo, int pageSize, int selector, String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("selector", selector);
    params.put("val", val);
    return memberDao.namelist(params);
  }

  @Override
  public List<Member> authlist(int pageNo, int pageSize, int selector, String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("selector", selector);
    params.put("val", val);
    return memberDao.authlist(params);
  }

  @Override
  public List<Member> emaillist(int pageNo, int pageSize, int selector, String val) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("size", pageSize);
    params.put("rowNo", (pageNo - 1) * pageSize);
    params.put("selector", selector);
    params.put("val", val);
    return memberDao.emaillist(params);
  }



 
}








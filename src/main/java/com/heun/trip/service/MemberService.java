package com.heun.trip.service;

import java.util.List;
import com.heun.trip.domain.Member;

public interface MemberService {
  int add(Member member);
  int snsadd(Member member);
  Member get(int no);
  Member get(String email, String password);
  Member snsget(String email, int sns_no);
  int update(Member member);
  int Emailupdate(Member member); 
  int delete(int no); 

  Member get(String email);
  int profileupdate(Member member);
  int pwdupdate(Member member);
  String getTel(int no);
  int getTel(String tel);
  List<Member> list(int pageNo, int pageSize, int selector, String val);
  List<Member> namelist(int pageNo, int pageSize, int selector, String val);
  List<Member> authlist(int pageNo, int pageSize, int selector, String val);
  List<Member> emaillist(int pageNo, int pageSize, int selector, String val);
  
  int size();
  int namesize(String val);
  int emailsize(String val);
  int authsize(String val);
}


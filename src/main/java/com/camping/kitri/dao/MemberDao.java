package com.camping.kitri.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.camping.kitri.domain.Member;

public interface MemberDao {
  int insert(Member member);
  int bankinsert(Map<String,Object> paramMap);
  int snsinsert(Member member);
  int fbinsert(Member member);
  List<Member> findAll(Map<String,Object> params);
  List<Member> namelist(Map<String,Object> params);
  List<Member> authlist(Map<String,Object> params);
  List<Member> emaillist(Map<String,Object> params);
  
  Member findByNo(int no);
  Member findByEmailPassword(Map<String,Object> paramMap);
  Member findByEmailName(Map<String,Object> paramMap);
  Member findByEmailSns(HashMap<String, Object> paramMap);
  Member findByEmail(String email);
  int update(Map<String,Object> paramMap);
  int profileupdate(Map<String,Object> paramMap);
  int Emailupdate(Member member); 
  int delete(int no);

  String extractHostTel(int no);
  int findByTel(String tel);
  int namecountAll(Map<String, Object> params);
  int emailcountAll(Map<String, Object> params); 
  int authcountAll(Map<String, Object> params); 
  int countAll(); 
}

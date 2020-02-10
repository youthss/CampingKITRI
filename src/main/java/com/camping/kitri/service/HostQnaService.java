package com.camping.kitri.service;

import java.util.List;

import com.camping.kitri.domain.HostQna;

public interface HostQnaService {
  int add(HostQna hostqna);
  int delete(int no);
  int size();
  List<HostQna> HostList(int no);
  List<HostQna> NewHostList(int no); 
  List<HostQna> NewGuestList(int no); 
  String getHostPhoto(int no);
  String getUserPhoto(int no);
}
package com.camping.app.service;

import java.util.List;

import com.camping.app.domain.Faq;

public interface FaqService {
  List<Faq> list(int pageNo, int pageSize);
  int add(Faq faq);
  Faq get(int no);
  int update(Faq faq);   
  int delete(int no);
  int size();
}

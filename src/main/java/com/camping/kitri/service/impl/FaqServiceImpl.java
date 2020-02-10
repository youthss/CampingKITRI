package com.camping.kitri.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import com.camping.kitri.dao.FaqDao;
import com.camping.kitri.domain.Faq;
import com.camping.kitri.service.FaqService;

@Service
public class FaqServiceImpl implements FaqService {

  FaqDao faqDao;

  public FaqServiceImpl(FaqDao faqDao) {
    this.faqDao = faqDao;
  }

  @Override
  public List<Faq> list(int pageNo, int pageSize) {
    HashMap<String,Object> params = new HashMap<>();
  params.put("size", pageSize);
  params.put("rowNo", (pageNo - 1) * pageSize);
  
  return faqDao.findAll(params);
  }

  @Override
  public int size() {
    return faqDao.countAll();
  }

@Override
public int delete(int no) {
  return faqDao.delete(no);
}

@Override
public int add(Faq faq) {
  return faqDao.insert(faq);
}

@Override
public int update(Faq faq) {
  return faqDao.update(faq);
}

  @Override
  public Faq get(int no) {
    return faqDao.findByNo(no);
  }
//  
//  @Override
//  public List<Qna> reList(int parent,  int step) {
//    HashMap<String,Object> params = new HashMap<>();
//    params.put("parent", parent);
//    params.put("step", step);
//    return FaqDao.findByReList(params);
//  }
}








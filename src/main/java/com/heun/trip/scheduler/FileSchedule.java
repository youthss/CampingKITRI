package com.heun.trip.scheduler;
 
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.heun.trip.dao.FileDao;
import com.heun.trip.service.FileService;

@Component
public class FileSchedule {

  @Autowired
  FileService fileService;
  @Autowired
  FileDao fileDao;

  @Scheduled(cron="0 0 6 * * *")
  public void fileSync() {
    Set<String> dbList = fileDao.findAll();
    Set<String> awsList = fileService.fileList();
    String[] check = {".jpeg", "_thum", "_header", "_profile", "_veiwthum"};
    for (String s : dbList) {
      awsList.remove(s);
      for (String c : check) {
        awsList.remove(s + c);
      }
    }
    int count = 0;
    for (String f : awsList) {
      fileService.deleteImage(f);
      count++;
    }
    System.out.println("삭제된 데이터 : " + count);
  }

}

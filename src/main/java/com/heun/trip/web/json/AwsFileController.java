package com.heun.trip.web.json;

import java.io.OutputStream;
import javax.servlet.annotation.MultipartConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heun.trip.service.FileService;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
@RestController("json/AwsFileController")
@RequestMapping("/json/images")
public class AwsFileController {
  
  FileService fileService;
  
  public AwsFileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("down/{name}")
  public void download(@PathVariable String name, OutputStream out) {
    try {
      fileService.downloadImage(name, out);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  

}

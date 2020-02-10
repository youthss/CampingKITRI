package com.camping.app.service;
 
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public interface FileService { 
  int uploadImage(InputStream in, long size, String filename);
  int downloadImage(String filename, OutputStream out);
  int deleteImage(String filename);
  int uploadImageThumbnail(InputStream in, int width, int height, String filename);
  Set<String> fileList();
}
  
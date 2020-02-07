package com.camping.kitri.service.impl;
  
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.camping.kitri.service.FileService;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

@Service
@EnableAsync
public class FileServiceImpl implements FileService {

  @Override
  public int uploadImage(InputStream in, long size, String filename) {
    Region region = Region.AP_NORTHEAST_2;
    S3Client s3 = S3Client.builder().region(region).build();

    try {
      s3.putObject(PutObjectRequest.builder().bucket("b1.sbj.kr")
          .key(filename).build(), RequestBody.fromInputStream(in, size));
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
    
    System.out.println("버킷에 파일 업로드 완료!");
    return 1;
  }
  
  @Override
  public int downloadImage(String filename, OutputStream out) {
    System.out.printf("다운로드 받을려는 이름 : %s\n", filename);
    Region region = Region.AP_NORTHEAST_2;
    S3Client s3 = S3Client.builder().region(region).build();

    try {
      s3.getObject(GetObjectRequest.builder()
          .bucket("b1.sbj.kr").key(filename).build(),
          ResponseTransformer.toOutputStream(out));
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("파일이 없습니다.");
      return 0;
    }
    return 1;
  }
  
  
  @Override
  public int deleteImage(String filename) {
    Region region = Region.AP_NORTHEAST_2;
    S3Client s3 = S3Client.builder().region(region).build();
    
    try {
      DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
          .bucket("b1.sbj.kr").key(filename).build();
      s3.deleteObject(deleteObjectRequest);
    } catch (Exception e) {
      System.out.println("그런 파일이 없습니다.");
      return 0;
    }
    System.out.println("버킷의 파일 삭제!");
    return 1;
  }
  

  @Override
  public int uploadImageThumbnail(InputStream in, int width, int height, String filename) {
    BufferedImage image = null;
    try {
      image = Thumbnails.of(in).crop(Positions.CENTER).size(width, height).outputFormat("jpeg")
      .asBufferedImage();
    } catch (IOException e) {
      System.out.println("섬네일을 만들지 못했습니다.");
      e.printStackTrace();
      return 0;
    }
    
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "jpeg", os);
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
    byte[] buffer = os.toByteArray();
    InputStream is = new ByteArrayInputStream(buffer);
    
    Region region = Region.AP_NORTHEAST_2;
    S3Client s3 = S3Client.builder().region(region).build();

    try {
      s3.putObject(PutObjectRequest.builder().bucket("b1.sbj.kr")
          .key(filename).build(), RequestBody.fromInputStream(is, buffer.length));
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
    System.out.println("버킷에 파일 섬네일 업로드 완료!");
    
    return 1;
  }
  
  @Override
  public Set<String> fileList() {
    Region region = Region.AP_NORTHEAST_2;
    S3Client s3 = S3Client.builder().region(region).build();
    ListObjectsV2Request listReq = ListObjectsV2Request.builder()
        .bucket("b1.sbj.kr")
        .maxKeys(1)
        .build();
    ListObjectsV2Iterable listRes = s3.listObjectsV2Paginator(listReq);
    Set<String> list = new HashSet<>();
    for (S3Object content : listRes.contents()) {
      list.add(content.key());
    }
    return list;
  }
  
}

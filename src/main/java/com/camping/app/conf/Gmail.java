package com.camping.app.conf;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:/com/camping/app/conf/sec.properties")
public class Gmail {
  
  @Autowired 
  Environment env;
  
  
  public String gmailSend(String email ,String title, String Text) {
    String user = env.getProperty("gmail.id"); // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
    String password = env.getProperty("gmail.password"); // 패스워드

    // SMTP 서버 정보를 설정한다.
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com"); 
    prop.put("mail.smtp.port", 465); 
    prop.put("mail.smtp.auth", "true"); 
    prop.put("mail.smtp.ssl.enable", "true"); 
    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password); // 메일을 발송할 실제 id, password
      }
    });

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(user));

      /// 수신자메일주소
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); 
 
      message.setSubject(title); //메일 제목을 입력

      message.setText(Text);    //메일 내용을 입력

      // send the message
      Transport.send(message); ////전송
      return ("success");

    } catch (AddressException e) {
      e.printStackTrace();
      return ("fail");
    } catch (MessagingException e) {
      e.printStackTrace();
      return ("fail");

    } 
  }

}

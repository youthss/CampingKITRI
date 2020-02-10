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
@PropertySource("classpath:/com/heun/trip/conf/sec.properties")
public class Gmail {
  
  @Autowired 
  Environment env;
  
  
  public String gmailSend(String email ,String title, String Text) {
    String user = env.getProperty("gmail.id"); // �꽕�씠踰꾩씪 寃쎌슦 �꽕�씠踰� 怨꾩젙, gmail寃쎌슦 gmail 怨꾩젙
    String password = env.getProperty("gmail.password"); // �뙣�뒪�썙�뱶

    // SMTP �꽌踰� �젙蹂대�� �꽕�젙�븳�떎.
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com"); 
    prop.put("mail.smtp.port", 465); 
    prop.put("mail.smtp.auth", "true"); 
    prop.put("mail.smtp.ssl.enable", "true"); 
    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password); // 硫붿씪�쓣 諛쒖넚�븷 �떎�젣 id, password
      }
    });

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(user));

      // �닔�떊�옄硫붿씪二쇱냼
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); 
 
      message.setSubject(title); //硫붿씪 �젣紐⑹쓣 �엯�젰

      message.setText(Text);    //硫붿씪 �궡�슜�쓣 �엯�젰

      // send the message
      Transport.send(message); ////�쟾�넚
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
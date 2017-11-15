package com.eastflag;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {
/*    @Autowired
    private DatasourceProfile profile;

    @Test
    public void contextLoads() {
        System.out.println(profile.getUserName());
    }*/

/*    @Autowired
    public JavaMailSender emailSender;

    @Test
    public void sendMail() {
        MimeMessage simpleMessage = emailSender.createMimeMessage();

        try{
            simpleMessage.setFrom(new InternetAddress("eastflag@gmail.com", "이승도"));
            simpleMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("leesott@naver.com"));
            simpleMessage.setSubject("주제");
            simpleMessage.setContent("<p>문의사항</p><p>문의사항입니다</p>",  "text/html; charset=UTF-8");

            emailSender.send(simpleMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }*/

}

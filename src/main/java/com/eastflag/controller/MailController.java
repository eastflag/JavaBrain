package com.eastflag.controller;

import com.eastflag.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class MailController {
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    @Autowired
    public JavaMailSender emailSender;

    @PostMapping(value="/api/sendMail")
    public Result sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text,
                           @RequestParam String from, @RequestParam String name) {
        //executor.submit(() -> {
            MimeMessage simpleMessage = emailSender.createMimeMessage();

            try{
                simpleMessage.setFrom(new InternetAddress(from, name));
                simpleMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
                simpleMessage.setSubject(subject);
                simpleMessage.setContent(text,  "text/html; charset=UTF-8");

                emailSender.send(simpleMessage);

            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        //});

        return new Result(0, "success");
    }
}

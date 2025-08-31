package com.studyjava.employeemanagement.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  private final JavaMailSender mail;

  public MailService(JavaMailSender mail) { this.mail = mail; }

  public void send(String to, String subject, String body) {
    var msg = new SimpleMailMessage();
    msg.setTo(to);
    msg.setSubject(subject);
    msg.setText(body);
    mail.send(msg);
  }
}

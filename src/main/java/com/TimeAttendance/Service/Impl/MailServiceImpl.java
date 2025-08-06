package com.TimeAttendance.Service.Impl;

import com.TimeAttendance.Payload.DTO.DataMailDto;
import com.TimeAttendance.Service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendHtmlMail(DataMailDto dataMail, String templateName) throws MessagingException {
        
        MimeMessage message = mailSender.createMimeMessage();

        // tạo tin nhắn mime,hỗ trợ file hình ảnh, tệp đính kèm và bố cục html
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(dataMail.getProps());

        String html = templateEngine.process(templateName, context);

        helper.setTo(dataMail.getTo());
        helper.setSubject(dataMail.getSubject());
        helper.setText(html, true);

        mailSender.send(message);
    }
    @Override
    public void sendTextMail(String to, String subject, String text) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lamvanromkaka@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }
    @Override
    public void sendFileMail(String to, String subject, String text, String path) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("lamvanromkaka@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
            
        FileSystemResource file = new FileSystemResource(new File(path));
        helper.addAttachment("text", file);

        mailSender.send(message);
    }
}

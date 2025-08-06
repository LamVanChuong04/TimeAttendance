package com.TimeAttendance.Service;



import com.TimeAttendance.Payload.DTO.DataMailDto;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDto dataMail, String templateName) throws MessagingException;
}

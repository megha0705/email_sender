package com.example.email.service;

import com.example.email.model.EmailStatus;
import com.example.email.model.EmailModel;
import com.example.email.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    EmailLogService emailLogService;

    public void sendEmail(List<EmailModel> emails , MessageModel message) throws IOException {

            for (EmailModel email : emails) {
                try{
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("reshmi");
                simpleMailMessage.setTo(email.getEmail());
                simpleMailMessage.setSubject(message.getSubject());
                simpleMailMessage.setText(message.getBody());
                javaMailSender.send(simpleMailMessage);
                emailLogService.emailLog(email.getEmail(), EmailStatus.SUCCESSFUL , null);
            }catch(Exception e){
                    emailLogService.emailLog(email.getEmail() , EmailStatus.FAILED , e.getMessage());
                }
        }
    }

}

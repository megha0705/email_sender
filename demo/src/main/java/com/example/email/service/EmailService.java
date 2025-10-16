package com.example.email.service;

import com.example.email.model.EmailStatus;
import com.example.email.model.EmailModel;
import com.example.email.model.MessageModel;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    EmailLogService emailLogService;

    public void sendEmail(List<String> emails , String body , String subject , Long fileId) throws IOException {

            for (String email : emails) {
                try{
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom("reshmichakraborty2411@gmail.com"); // 🔹 put a real email here
                    helper.setTo(email);
                    helper.setSubject(subject);
                    helper.setText(body, true);

                javaMailSender.send(mimeMessage);
                emailLogService.emailLog(email, EmailStatus.SUCCESSFUL , null , fileId);
            }catch(Exception e){
                    emailLogService.emailLog(email, EmailStatus.FAILED , e.getMessage() , fileId);
                }
        }
    }

}

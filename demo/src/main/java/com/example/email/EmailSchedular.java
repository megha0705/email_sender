package com.example.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class EmailSchedular {
    @Autowired
    EmailService emailService;


    private List<EmailModel> emailList;
    private MessageModel message;
    private int currentIndex = 0;


    public void scheduleEmails(List<EmailModel> emails, MessageModel msg) {
        this.emailList = emails;
        this.message = msg;
        this.currentIndex = 0;
    }

    // Runs every 90 seconds
    @Scheduled(fixedDelay = 90000)
    public void sendNextEmail() throws IOException {
        if (emailList != null && currentIndex < emailList.size()) {
            EmailModel nextEmail = emailList.get(currentIndex);
            emailService.sendEmail(List.of(nextEmail), message);
            System.out.println("email sent");
            currentIndex++;
        }
    }
}

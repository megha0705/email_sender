package com.example.email;

import org.springframework.stereotype.Service;

import java.awt.datatransfer.SystemFlavorMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
@Service
public class EmailLogService {
    private int proccessedEmails = 0;
   private int invalidEmails = 0;
   private int validEmails = 0;
   private  int failedEmail = 0;

    public void emailLog(String email , EmailStatus status  ,String errorMessage) throws IOException {
        proccessedEmails++;
        if(status == EmailStatus.INVALID_EMAIL){
            invalidEmails++;
        }else if(status == EmailStatus.FAILED){
            failedEmail++;
        }else if(status == EmailStatus.SUCCESSFUL){
            validEmails++;
        }
        FileWriter file = new FileWriter("mail_log.txt", true);

        String logEntry = String.format("%s | %-30s | %s | %s",
                LocalDateTime.now(), email, status,
                errorMessage == null? "":errorMessage);

        try (BufferedWriter writer = new BufferedWriter(file)) {
            writer.write(logEntry);
            writer.newLine();
        }



    }
    public void output(){
        System.out.println("total emails proccessed : " + proccessedEmails);
        System.out.println("total failed emails :" + failedEmail);
        System.out.println("total successfully sent emails ; " + validEmails);
        System.out.println("total invalid emails :" + invalidEmails);
    }

}

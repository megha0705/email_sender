package com.example.email.service;

import com.example.email.model.CsvFileLogModel;
import com.example.email.repository.CsvFileRepo;
import com.example.email.repository.EmailLogRepo;
import com.example.email.model.EmailStatus;
import com.example.email.model.EmailLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
@Service
public class EmailLogService {
    private int proccessedEmails = 0;
   private int invalidEmails = 0;
   private int validEmails = 0;
   private  int failedEmail = 0;
    @Autowired
    EmailLogRepo emailLogRepo;
    @Autowired
    CsvFileRepo csvFileRepo;
    public void emailLog(String email , EmailStatus status  , String errorMessage , Long fileId) throws IOException {
        proccessedEmails++;
        if(status == EmailStatus.INVALID_EMAIL){
            invalidEmails++;
        }else if(status == EmailStatus.FAILED){
            failedEmail++;
        }else if(status == EmailStatus.SUCCESSFUL){
            validEmails++;
        }
        CsvFileLogModel csvFileLogModel = csvFileRepo.findById(fileId).orElseThrow(()-> new RuntimeException("file with that id not found") );

        EmailLogModel emailLogModel = new EmailLogModel();
        emailLogModel.setEmailId(email);
        emailLogModel.setStatus(status);
        emailLogModel.setErrorMsg(errorMessage);
        emailLogModel.setFileLog(csvFileLogModel);
        emailLogModel.setLogTime(LocalDateTime.now());
        emailLogRepo.save(emailLogModel);

    }




    public void output(){
        System.out.println("total emails proccessed : " + proccessedEmails);
        System.out.println("total failed emails :" + failedEmail);
        System.out.println("total successfully sent emails ; " + validEmails);
        System.out.println("total invalid emails :" + invalidEmails);
    }

}

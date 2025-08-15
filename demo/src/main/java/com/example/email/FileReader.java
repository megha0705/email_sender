package com.example.email;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FileReader {
    @Autowired
    EmailLogService emailLogService;
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if(email != null && email.matches(emailRegex)){
            return true;
        };
        return  false;
    }
    public List<EmailModel> readFromCsv(){
        try {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/emails.csv"));
            CsvToBean<EmailModel> csvToBean = new CsvToBeanBuilder<EmailModel>(reader)
                    .withType(EmailModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<EmailModel> allEmails=  csvToBean.parse();
            Set<String> uniqueEmails = new HashSet<>();
            List<EmailModel> validEmails = new ArrayList<>();

            for(EmailModel email : allEmails){
               if(email.getEmail() == null || email.getEmail().trim().isEmpty()){


                   continue;
               }
               if(!isValidEmail(email.getEmail())){

                   emailLogService.emailLog(email.getEmail(), EmailStatus.INVALID_EMAIL, null);
                   continue;
               }
               if(!uniqueEmails.add(email.getEmail())){

                   emailLogService.emailLog(email.getEmail() , EmailStatus.DUPLICATE, null);
                   continue;
               }
               validEmails.add(email);

            }
            return validEmails;
        }catch (Exception e){
            throw new RuntimeException("email could nnot be read");
        }
    }

    public MessageModel readFromTxt()throws IOException {

        MessageModel messageModel = new MessageModel();
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/message.txt")));
        String line;
        String subject = null;
        String body = null;

        while ((line = br.readLine()) != null) {
            if (line.startsWith("Subject:")) {
                subject = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
            } else if (line.startsWith("Body:")) {
                body = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
            }
        }
            if(subject != null && body != null){
                messageModel.setSubject(subject);
                messageModel.setBody(body);
            }
return messageModel;
    }
}

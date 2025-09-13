package com.example.email.service;

import com.example.email.model.EmailModel;
import com.example.email.model.EmailStatus;
import com.example.email.model.MessageModel;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReadFile {
    @Autowired
    EmailLogService emailLogService;

    public boolean isValidEmail(String email) {

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();

            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return email.matches(emailRegex);

        } catch (Exception e) {
            return false;
        }
    }

    public List<EmailModel> readFromCsv(Long fileId , String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<EmailModel> csvToBean = new CsvToBeanBuilder<EmailModel>(reader)
                    .withType(EmailModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<EmailModel> allEmails = csvToBean.parse();
            Set<String> uniqueEmails = new HashSet<>();
            List<EmailModel> validEmails = new ArrayList<>();

            for (EmailModel email : allEmails) {
                if (email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                    continue; // skip empty emails
                }
                if (!isValidEmail(email.getEmail())) {
                    emailLogService.emailLog(email.getEmail(), EmailStatus.INVALID_EMAIL, null , fileId);
                    continue;
                }
                if (!uniqueEmails.add(email.getEmail())) {
                    emailLogService.emailLog(email.getEmail(), EmailStatus.DUPLICATE, null , fileId);
                    continue;
                }
                validEmails.add(email);
            }
            return validEmails;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV from: " + filePath, e);
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

package com.example.email.service;

import com.example.email.config.VerifaliaConfig;
import com.example.email.model.EmailModel;
import com.example.email.model.EmailStatus;
import com.example.email.model.MessageModel;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.verifalia.api.emailvalidations.WaitingStrategy;
import com.verifalia.api.emailvalidations.models.FileValidationRequest;
import com.verifalia.api.emailvalidations.models.Validation;
import com.verifalia.api.emailvalidations.models.ValidationEntry;
import com.verifalia.api.emailvalidations.models.ValidationStatus;
import com.verifalia.api.exceptions.VerifaliaException;
import com.verifalia.api.rest.WellKnownMimeTypes;
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
    @Autowired
    VerifaliaConfig verifalia;

    public boolean isValidEmail(FileValidationRequest email) throws VerifaliaException {

        Validation validation = verifalia.verifaliaRestClient().getEmailValidations().submit(email);

        Validation completedJob = verifalia.verifaliaRestClient()
                .getEmailValidations()
                .get(validation.getOverview().getId(), new WaitingStrategy(true));

        ValidationEntry entry = completedJob.getEntries().get(0);

        System.out.println("Email: " + entry.getInputData());
        System.out.println("Status: " + entry.getStatus());
        System.out.println("Classification: " + entry.getClassification());
        System.out.println("----------");
        String status = String.valueOf(entry.getStatus());
        String classification = String.valueOf(entry.getClassification());

       if( "Success".equalsIgnoreCase(status)
                && "Deliverable".equalsIgnoreCase(classification)){
           return true;
       }
       return false;
    }


    public List<String> readFromCsv(Long fileId, String filePath) throws IOException, VerifaliaException {

        FileValidationRequest request = new FileValidationRequest(filePath, WellKnownMimeTypes.TEXT_CSV);
        request.setStartingRow(1);
        request.setColumn(0);


        Validation validation = verifalia.verifaliaRestClient().getEmailValidations().submit(request);


        Validation completedJob = verifalia.verifaliaRestClient().getEmailValidations().get(validation.getOverview().getId(), new WaitingStrategy(true));
        List<String> validEmails = new ArrayList<>();
        Set<String> seenEmails = new HashSet<>();

        for (ValidationEntry entry : completedJob.getEntries()) {

            String email = entry.getInputData();
            String status = String.valueOf(entry.getStatus());
            String classification = String.valueOf(entry.getClassification());

            System.out.println("Email: " + email);
            System.out.println("Status: " + status);
            System.out.println("Classification: " + classification);
            System.out.println("----------");

            if (seenEmails.contains(email)) {
                emailLogService.emailLog(email, EmailStatus.DUPLICATE, null, fileId);
                continue;
            }

            seenEmails.add(email);

            if ("Success".equalsIgnoreCase(status) &&
                    "Deliverable".equalsIgnoreCase(classification)) {
                    validEmails.add(email);
            } else {
                emailLogService.emailLog(email, EmailStatus.INVALID_EMAIL,"Bounced", fileId);
            }
        }
        return validEmails;
    }


    /*

    public List<EmailModel> readFromCsv(Long fileId , String filePath) throws FileNotFoundException, VerifaliaException {



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
                    continue;
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
    }*/

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

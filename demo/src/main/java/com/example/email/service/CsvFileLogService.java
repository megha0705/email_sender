package com.example.email.service;

import com.example.email.model.CsvFileLogModel;
import com.example.email.model.EmailContentModel;
import com.example.email.model.EmailLogModel;
import com.example.email.repository.CsvFileRepo;
import com.example.email.repository.EmailContentRepo;
import com.example.email.repository.EmailLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CsvFileLogService {
    @Autowired
    CsvFileRepo csvFileRepo;
    @Autowired
    EmailLogRepo emailLogRepo;
    @Autowired
    EmailContentRepo emailContentRepo;
    public Long addFile(String originalFilename, LocalDateTime dateTime, String subject , String body , String filePath) {
        CsvFileLogModel csvFileLogModel = new CsvFileLogModel();
        csvFileLogModel.setFileName(originalFilename);
        csvFileLogModel.setScheduledTime(dateTime);
        csvFileLogModel.setStatus("PENDING");
        csvFileLogModel.setFilePath(filePath);
        EmailContentModel emailContentModel = new EmailContentModel();
        emailContentModel.setBody(body);
        emailContentModel.setSubject(subject);
        emailContentModel.setCsvFileLog(csvFileLogModel);

        csvFileLogModel.setEmailContent(emailContentModel);
        CsvFileLogModel savedCsvFile =  csvFileRepo.save(csvFileLogModel);

    return savedCsvFile.getId();
    }


    public void updateStatus(Long id, String status) {
        CsvFileLogModel log = csvFileRepo.findById(id).orElseThrow(() -> new RuntimeException("Log not found: " + id));

        log.setStatus(status);

        csvFileRepo.save(log);
    }

        public List<EmailLogModel> getLogsByFile(Long fileId) {
            return emailLogRepo.findByFileLogId(fileId);
        }

    public String getExistingFilePath(Long fileId) {
        CsvFileLogModel log = csvFileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("Log not found: " + fileId));
        return log.getFilePath();

    }

    public Long updateCsvFileLog(Long fileId, LocalDateTime dateTime, String subject , String body, String filePath){
        CsvFileLogModel log = csvFileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("Log not found: " + fileId));
        log.setFilePath(filePath);
        log.setScheduledTime(dateTime);
        log.setStatus("PENDING");
        log.getEmailContent().setBody(body);
        log.getEmailContent().setSubject(subject);


        CsvFileLogModel savedCsvFile =  csvFileRepo.save(log);

        return savedCsvFile.getId();
    }
}

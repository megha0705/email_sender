package com.example.email.service;

import com.example.email.model.CsvFileLogModel;
import com.example.email.repository.CsvFileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CsvFileLogService {
    @Autowired
    CsvFileRepo csvFileRepo;
    public Long addFile(String originalFilename, LocalDateTime dateTime) {
        CsvFileLogModel csvFileLogModel = new CsvFileLogModel();
        csvFileLogModel.setFileName(originalFilename);
        csvFileLogModel.setScheduledTime(dateTime);
        csvFileLogModel.setStatus("PENDING");
        CsvFileLogModel saved = csvFileRepo.save(csvFileLogModel);
        return saved.getId();
    }


    public void updateStatus(Long id, String status) {
        CsvFileLogModel log = csvFileRepo.findById(id).orElseThrow(() -> new RuntimeException("Log not found: " + id));

        log.setStatus(status);
        csvFileRepo.save(log);
    }
}

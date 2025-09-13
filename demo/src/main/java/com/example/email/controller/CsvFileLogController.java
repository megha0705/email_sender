package com.example.email.controller;

import com.example.email.model.CsvFileLogModel;
import com.example.email.model.EmailLogModel;
import com.example.email.repository.CsvFileRepo;
import com.example.email.service.CsvFileLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CsvFileLogController {
    @Autowired
    CsvFileRepo csvFileRepo;
    @Autowired
    CsvFileLogService csvFileLogService;
    @GetMapping("getAllFileLogs")
    public List<CsvFileLogModel> getAllLogs() {
        return csvFileRepo.findAll();
    }
    @GetMapping("/{fileId}/getLogsById")
    public List<EmailLogModel> getLogsByFile(@PathVariable Long fileId) {
        return csvFileLogService.getLogsByFile(fileId);
    }
}

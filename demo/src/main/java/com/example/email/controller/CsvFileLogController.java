package com.example.email.controller;

import com.example.email.model.CsvFileLogModel;
import com.example.email.repository.CsvFileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CsvFileLogController {
    @Autowired
    CsvFileRepo csvFileRepo;
    @GetMapping("getAllFileLogs")
    public List<CsvFileLogModel> getAllLogs() {
        return csvFileRepo.findAll();
    }
}

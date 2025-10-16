package com.example.email.controller;

import com.example.email.service.CampaignService;
import com.example.email.service.CsvFileLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class CampaignController {
@Autowired
CampaignService campaignService;
@Autowired
CsvFileLogService csvFileLogService;
    @PostMapping("/campaign/create")
    public ResponseEntity<String> createCampaign(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dateTime") String dateTimeStr,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body
            ) throws Exception {

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String filePath = campaignService.saveFile(file);
       Long csvFileId = csvFileLogService.addFile(file.getOriginalFilename() , dateTime, subject , body, filePath);
        campaignService.scheduleCampaign(csvFileId , filePath, dateTime);

        return ResponseEntity.ok("Campaign scheduled for " + dateTime);
    }

    @PostMapping("/campaign/reschedule")
    public ResponseEntity<String> rescheduleCampaign(
            @RequestParam("fileId") Long fileId,
            @RequestParam("dateTime") String dateTimeStr,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body
    ) throws Exception {

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String filePath = csvFileLogService.getExistingFilePath(fileId);
        Long csvFileId = csvFileLogService.updateCsvFileLog(fileId, dateTime, subject , body, filePath);
        campaignService.scheduleCampaign(csvFileId, filePath, dateTime);

        return ResponseEntity.ok("Campaign rescheduled for " + dateTime);
    }

}

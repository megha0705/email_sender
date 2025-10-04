package com.example.email.service;

import com.example.email.model.EmailModel;
import com.example.email.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CampaignService {


    private final String UPLOAD_DIR = "uploads/"; //storing the uploaded files in this directory

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    ReadFile readFile;
    @Autowired
    private EmailService emailService;
    @Autowired
    CsvFileLogService csvFileLogService;
    @Autowired
    CleanCsvFileService cleanCsvFileService;
    public String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }


    public void scheduleCampaign(Long csvFileId, String filePath, LocalDateTime dateTime) {
        taskScheduler.schedule(() -> {
            try {
                String cleanedFile = cleanCsvFileService.cleanCsvFile(filePath);
                List<String> emails = readFile.readFromCsv(csvFileId , cleanedFile);
                System.out.println("Loaded " + emails.size() + " emails from " + filePath);
                for (String e : emails) {
                    System.out.println(e);
                }

                MessageModel message = readFile.readFromTxt();
                emailService.sendEmail(emails, message , csvFileId);
                csvFileLogService.updateStatus(csvFileId , "SENT");
                System.out.println("Emails sent at " + LocalDateTime.now());
            } catch (Exception e) {
                csvFileLogService.updateStatus(csvFileId , "FAILED");
                e.printStackTrace();
            }
        }, Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}

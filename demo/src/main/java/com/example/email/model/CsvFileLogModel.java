package com.example.email.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class CsvFileLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private LocalDateTime scheduledTime;
    private String status;
    private String filePath;
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne(mappedBy = "csvFileLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private EmailContentModel emailContent;
    @OneToMany(mappedBy = "fileLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EmailLogModel> emailLogs = new ArrayList<>();

    public List<EmailLogModel> getEmailLogs() {
        return emailLogs;
    }

    public void setEmailLogs(List<EmailLogModel> emailLogs) {
        this.emailLogs = emailLogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public EmailContentModel getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(EmailContentModel emailContent) {
        this.emailContent = emailContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

package com.example.email.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "mail_logs")
public class EmailLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "log_time", nullable = false, updatable = false)
    private LocalDateTime logTime;

    @Column(name = "email_id")
    private String emailId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmailStatus status;

    @Column(name = "error_msg", columnDefinition = "TEXT")
    private String errorMsg;




    public EmailLogModel() {
    }

    public EmailLogModel(Long id, LocalDateTime logTime, String emailId, EmailStatus status, String errorMsg) {
        this.id = id;
        this.logTime = logTime;
        this.emailId = emailId;
        this.status = status;
        this.errorMsg = errorMsg;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "MailLog{" +
                "id=" + id +
                ", logTime=" + logTime +
                ", emailId='" + emailId + '\'' +
                ", status='" + status + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

}

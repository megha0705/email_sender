package com.example.email.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "email_content")
public class EmailContentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String body;

    @OneToOne
    @JoinColumn(name = "csv_file_log_id")
    @JsonBackReference
    private CsvFileLogModel csvFileLog;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CsvFileLogModel getCsvFileLog() {
        return csvFileLog;
    }

    public void setCsvFileLog(CsvFileLogModel csvFileLog) {
        this.csvFileLog = csvFileLog;
    }
}

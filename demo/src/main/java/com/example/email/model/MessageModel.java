package com.example.email.model;

public class MessageModel {
    private String subject;
    private String body;

    public MessageModel(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
    public MessageModel(){

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
}

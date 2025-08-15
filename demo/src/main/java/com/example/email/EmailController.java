package com.example.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EmailController {
    @Autowired
    EmailService emailService;
    @Autowired
    FileReader fileReader;
    @Autowired
    EmailLogService emailLogService;
    @GetMapping("send_email")
    public String sentEmail() throws IOException {
        List<EmailModel> emails = fileReader.readFromCsv();
        MessageModel message = fileReader.readFromTxt();
        emailService.sendEmail(emails , message);
        emailLogService.output();
        return "email sent";
    }
    @GetMapping("log_entry")
    public String emailLog() throws IOException {
        emailLogService.emailLog("chakrabortyreshmi@gmail.com" ,EmailStatus.SUCCESSFUL , null );

        return "log updated";
    }

}

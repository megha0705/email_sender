package com.example.email.controller;

import com.example.email.model.EmailLogModel;
import com.example.email.repository.EmailLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmailLogController {
@Autowired
EmailLogRepo emailLogRepo;
    @GetMapping("/logs")
    public List<EmailLogModel> getLog(){
       return  emailLogRepo.findAll();
    }

    /*@DeleteMapping("/delLogs")
    public String deleteLog(){
        emailLogRepo.deleteAll();
        return "successfully deleted";
    }*/


}

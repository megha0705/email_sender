package com.example.email.repository;

import com.example.email.model.CsvFileLogModel;
import com.example.email.model.EmailContentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailContentRepo extends JpaRepository<EmailContentModel , Long> {

}

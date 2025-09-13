package com.example.email.repository;

import com.example.email.model.EmailLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailLogRepo extends JpaRepository<EmailLogModel, Long> {
    List<EmailLogModel> findByFileLogId(Long fileLogId);

}

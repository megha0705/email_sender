package com.example.email.repository;

import com.example.email.model.EmailLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepo extends JpaRepository<EmailLogModel, Long> {
}

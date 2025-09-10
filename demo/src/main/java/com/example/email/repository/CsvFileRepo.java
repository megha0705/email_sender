package com.example.email.repository;

import com.example.email.model.CsvFileLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvFileRepo extends JpaRepository<CsvFileLogModel, Long> {
}

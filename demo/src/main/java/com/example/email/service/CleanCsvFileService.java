package com.example.email.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CleanCsvFileService {

    public String cleanCsvFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> cleanedLines = Files.lines(path)
                .filter(line -> line != null && !line.trim().isEmpty())
                .collect(Collectors.toList());


        File tempFile = File.createTempFile("cleaned-", ".csv");
        Files.write(tempFile.toPath(), cleanedLines);

        return tempFile.getAbsolutePath();
    }
}

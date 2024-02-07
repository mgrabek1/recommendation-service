package com.mgrabek.recommendationservice.service;

import com.mgrabek.recommendationservice.repository.CryptoValueRepository;
import com.mgrabek.recommendationservice.model.CryptoValueCsv;
import com.mgrabek.recommendationservice.model.entity.CryptoValueTimestamp;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvDataImportService {

    @Value("${csv.file.suffix-name}")
    private String csvFileNameSuffix;

    @Value("${csv.file.path}")
    private String csvFilePath;

    private final CryptoValueRepository cryptoValueRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void importCryptoValues() {
        List<String> valueFileNames = getValueFileNames();
        for (String fileName : valueFileNames) {
            List<CryptoValueTimestamp> entities = processCsvValueFile(fileName).stream()
                    .map(CryptoValueTimestamp::from)
                    .toList();
            cryptoValueRepository.saveAll(entities);
        }
    }

    private List<CryptoValueCsv> processCsvValueFile(String fileName) {
        try (Reader reader = new FileReader(csvFilePath + fileName);
             CSVReader csvReader = new CSVReader(reader)) {
            CsvToBean<CryptoValueCsv> csvToBean = new CsvToBeanBuilder<CryptoValueCsv>(csvReader)
                    .withType(CryptoValueCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            log.error("An error occurred while importing CSV file with name '{}'", fileName);
            log.error(e.getMessage());
            throw new RuntimeException("Failed to import CSV data. Please check the CSV file: " + fileName, e);
        }
    }

    private List<String> getValueFileNames() {
        List<String> fileNames = new ArrayList<>();
        try {
            Path folderPath = Paths.get(csvFilePath);
            Files.walkFileTree(folderPath, EnumSet.noneOf(FileVisitOption.class), 2, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName().toString().endsWith(csvFileNameSuffix)) {
                        fileNames.add(file.getFileName().toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No files with crypto values found.");
        }
        return fileNames;
    }
}
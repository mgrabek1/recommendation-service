package com.mgrabek.recommendationservice.service;

import com.mgrabek.recommendationservice.repository.CryptoValueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CsvDataImportServiceTest {

    @Autowired
    CsvDataImportService csvDataImportService;

    @Autowired
    CryptoValueRepository cryptoValueRepository;

    @Test
    void importTest() {
        csvDataImportService.importCryptoValues();
        Assertions.assertEquals(450, cryptoValueRepository.findAll().size());
    }

}

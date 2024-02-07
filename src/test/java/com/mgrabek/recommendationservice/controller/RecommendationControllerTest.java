package com.mgrabek.recommendationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgrabek.recommendationservice.model.dto.CryptoNormalizedRangeDto;
import com.mgrabek.recommendationservice.model.dto.CryptoGeneralInformationDto;
import com.mgrabek.recommendationservice.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecommendationService recommendationService;

    @Test
    void calculateNormalizedRanges() throws Exception {
        List<CryptoNormalizedRangeDto> cryptoNormalizedRanges = Collections.singletonList(
                new CryptoNormalizedRangeDto(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(0.9), "BTC")
        );

        Mockito.when(recommendationService.calculateNormalizedRanges()).thenReturn(cryptoNormalizedRanges);

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate/normalized-ranges/sorted"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cryptoNormalizedRanges)));
    }

    @Test
    void calculateHighestNormalizedRange() throws Exception {
        LocalDate date = LocalDate.of(2020,1,1);
        CryptoNormalizedRangeDto cryptoNormalizedRangeDto = new CryptoNormalizedRangeDto(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(0.9), "BTC");

        Mockito.when(recommendationService.calculate(date)).thenReturn(cryptoNormalizedRangeDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate/highest-normalized-range")
                        .param("date", date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cryptoNormalizedRangeDto)));
    }

    @Test
    void calculateGeneralInformation() throws Exception {
        String symbol = "BTC";
        CryptoGeneralInformationDto cryptoGeneralInformationDto = new CryptoGeneralInformationDto(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(5), BigDecimal.valueOf(2), symbol);

        Mockito.when(recommendationService.calculate(symbol)).thenReturn(cryptoGeneralInformationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate/{symbol}", symbol))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cryptoGeneralInformationDto)));
    }
}

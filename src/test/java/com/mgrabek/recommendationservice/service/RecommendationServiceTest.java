package com.mgrabek.recommendationservice.service;

import com.mgrabek.recommendationservice.model.dto.CryptoNormalizedRangeDto;
import com.mgrabek.recommendationservice.repository.CryptoValueRepository;
import com.mgrabek.recommendationservice.mapper.CryptoMapper;
import com.mgrabek.recommendationservice.model.CryptoGeneralInformationImpl;
import com.mgrabek.recommendationservice.model.CryptoNormalizedRangeImpl;
import com.mgrabek.recommendationservice.model.dto.CryptoGeneralInformationDto;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoGeneralInformation;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoNormalizedRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private CryptoValueRepository cryptoValueRepository;

    @Mock
    private CryptoMapper cryptoMapper;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void calculateCryptoNormalizedRange() {
        LocalDate localDate = LocalDate.of(2020,1,1);
        CryptoNormalizedRange cryptoNormalizedRange = new CryptoNormalizedRangeImpl("BTC", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(0.9));
        List<CryptoNormalizedRange> cryptoNormalizedRanges = Collections.singletonList(cryptoNormalizedRange);

        Mockito.when(cryptoValueRepository.findCryptoWithHighestNormalizedRangeForSpecificDay(eq(localDate), any()))
                .thenReturn(cryptoNormalizedRanges);
        Mockito.when(cryptoMapper.toCryptoNormalizedRange(Mockito.any()))
                .thenReturn(new CryptoNormalizedRangeDto(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(0.9), "BTC"));

        CryptoNormalizedRangeDto result = recommendationService.calculate(localDate);

        assertNotNull(result);
        assertEquals("BTC", result.symbol());
        assertEquals(BigDecimal.TEN, result.maxPrice());
        assertEquals(BigDecimal.ONE, result.minPrice());
        assertEquals(BigDecimal.valueOf(0.9), result.normalizedRange());
    }

    @Test
    void calculateCryptoGeneralInformation() {
        String symbol = "BTC";
        CryptoGeneralInformation cryptoGeneralInformation = new CryptoGeneralInformationImpl("BTC", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(5), BigDecimal.valueOf(2));

        Mockito.when(cryptoValueRepository.existsBySymbol(eq(symbol)))
                .thenReturn(true);
        Mockito.when(cryptoValueRepository.findGeneralInformationForCrypto(eq(symbol)))
                .thenReturn(cryptoGeneralInformation);
        Mockito.when(cryptoMapper.toCryptoGeneralInformation(Mockito.any()))
                .thenReturn(new CryptoGeneralInformationDto(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(5), "BTC"));

        CryptoGeneralInformationDto result = recommendationService.calculate(symbol);

        assertNotNull(result);
        assertEquals("BTC", result.symbol());
        assertEquals(BigDecimal.TEN, result.maxPrice());
        assertEquals(BigDecimal.ONE, result.minPrice());
        assertEquals(BigDecimal.valueOf(5), result.newest());
        assertEquals(BigDecimal.valueOf(2), result.oldest());
    }

    @Test
    void calculateNormalizedRanges() {
        List<CryptoNormalizedRange> cryptoNormalizedRanges = Collections.singletonList(
                new CryptoNormalizedRangeImpl("BTC", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(0.9))
        );

        Mockito.when(cryptoValueRepository.findDescendingSortedCryptosByNormalizedRange())
                .thenReturn(cryptoNormalizedRanges);
        Mockito.when(cryptoMapper.toCryptosNormalizedRange(Mockito.any()))
                .thenReturn(Collections.singletonList(new CryptoNormalizedRangeDto(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(0.9), "BTC")));

        List<CryptoNormalizedRangeDto> result = recommendationService.calculateNormalizedRanges();

        assertNotNull(result);
        assertFalse(result.isEmpty());

        CryptoNormalizedRangeDto firstCrypto = result.get(0);
        assertEquals("BTC", firstCrypto.symbol());
        assertEquals(BigDecimal.TEN, firstCrypto.maxPrice());
        assertEquals(BigDecimal.ONE, firstCrypto.minPrice());
        assertEquals(BigDecimal.valueOf(0.9), firstCrypto.normalizedRange());
    }
}

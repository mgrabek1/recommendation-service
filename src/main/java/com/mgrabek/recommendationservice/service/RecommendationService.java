package com.mgrabek.recommendationservice.service;

import com.mgrabek.recommendationservice.exception.UnsupportedOperationException;
import com.mgrabek.recommendationservice.mapper.CryptoMapper;
import com.mgrabek.recommendationservice.model.dto.CryptoNormalizedRangeDto;
import com.mgrabek.recommendationservice.repository.CryptoValueRepository;
import com.mgrabek.recommendationservice.model.dto.CryptoGeneralInformationDto;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoGeneralInformation;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoNormalizedRange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final CryptoValueRepository cryptoValueRepository;
    private final CryptoMapper cryptoMapper;

    public CryptoNormalizedRangeDto calculate(LocalDate localDate) {
        List<CryptoNormalizedRange> cryptoWithHighestNormalizedRange = cryptoValueRepository.findCryptoWithHighestNormalizedRangeForSpecificDay(localDate, PageRequest.of(0, 1));
        return cryptoMapper.toCryptoNormalizedRange(cryptoWithHighestNormalizedRange.stream()
                .findFirst()
                .orElseThrow());
    }

    public CryptoGeneralInformationDto calculate(String symbol) {
        if (!cryptoValueRepository.existsBySymbol(symbol)) {
            throw new UnsupportedOperationException(String.format("Crypto with symbol: '%s' is not currently supported.", symbol));
        }
        CryptoGeneralInformation cryptoNormalizedRange = cryptoValueRepository.findGeneralInformationForCrypto(symbol);
        return cryptoMapper.toCryptoGeneralInformation(cryptoNormalizedRange);
    }

    public List<CryptoNormalizedRangeDto> calculateNormalizedRanges() {
        List<CryptoNormalizedRange> cryptoDetails = cryptoValueRepository.findDescendingSortedCryptosByNormalizedRange();
        return cryptoMapper.toCryptosNormalizedRange(cryptoDetails);
    }

}

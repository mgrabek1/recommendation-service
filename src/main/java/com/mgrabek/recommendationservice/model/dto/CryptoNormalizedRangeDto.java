package com.mgrabek.recommendationservice.model.dto;

import java.math.BigDecimal;

public record CryptoNormalizedRangeDto(BigDecimal maxPrice, BigDecimal minPrice, BigDecimal normalizedRange,
                                       String symbol) {
}

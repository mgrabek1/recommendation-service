package com.mgrabek.recommendationservice.model.dto;

import java.math.BigDecimal;

public record CryptoGeneralInformationDto(BigDecimal maxPrice, BigDecimal minPrice, BigDecimal oldest,
                                          BigDecimal newest, String symbol) {
}

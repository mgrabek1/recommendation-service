package com.mgrabek.recommendationservice.model;

import com.mgrabek.recommendationservice.model.entity.projection.CryptoNormalizedRange;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CryptoNormalizedRangeImpl implements CryptoNormalizedRange {
    private String symbol;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private BigDecimal normalizedRange;

}


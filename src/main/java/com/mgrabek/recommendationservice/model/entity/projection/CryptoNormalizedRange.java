package com.mgrabek.recommendationservice.model.entity.projection;

import java.math.BigDecimal;

public interface CryptoNormalizedRange {
    String getSymbol();

    BigDecimal getMaxPrice();

    BigDecimal getMinPrice();

    BigDecimal getNormalizedRange();
}

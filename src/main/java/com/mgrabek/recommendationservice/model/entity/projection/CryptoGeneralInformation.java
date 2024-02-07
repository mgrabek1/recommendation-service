package com.mgrabek.recommendationservice.model.entity.projection;

import java.math.BigDecimal;

public interface CryptoGeneralInformation {
    String getSymbol();

    BigDecimal getMaxPrice();

    BigDecimal getMinPrice();

    BigDecimal getNewest();

    BigDecimal getOldest();
}

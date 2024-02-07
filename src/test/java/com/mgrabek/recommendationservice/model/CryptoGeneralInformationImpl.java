package com.mgrabek.recommendationservice.model;

import com.mgrabek.recommendationservice.model.entity.projection.CryptoGeneralInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CryptoGeneralInformationImpl implements CryptoGeneralInformation {
    private String symbol;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private BigDecimal newest;
    private BigDecimal oldest;

}

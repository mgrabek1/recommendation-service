package com.mgrabek.recommendationservice.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CryptoValueCsv {
    @CsvBindByName(column = "symbol")
    private String symbol;
    @CsvBindByName(column = "timestamp")
    private Long timestamp;
    @CsvBindByName(column = "price")
    private BigDecimal price;
}

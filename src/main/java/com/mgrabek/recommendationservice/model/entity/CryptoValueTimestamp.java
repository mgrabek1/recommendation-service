package com.mgrabek.recommendationservice.model.entity;

import com.mgrabek.recommendationservice.model.CryptoValueCsv;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CryptoValueTimestamp {
    @EmbeddedId
    private CryptoValueTimestampId id;
    private BigDecimal price;

    public static CryptoValueTimestamp from(CryptoValueCsv cryptoValueCsv) {
        return new CryptoValueTimestamp(new CryptoValueTimestampId(new Timestamp(cryptoValueCsv.getTimestamp()), cryptoValueCsv.getSymbol()), cryptoValueCsv.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoValueTimestamp that = (CryptoValueTimestamp) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}

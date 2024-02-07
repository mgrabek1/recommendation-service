package com.mgrabek.recommendationservice.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class CryptoValueTimestampId implements Serializable {
    private Timestamp timestamp;
    private String symbol;

}

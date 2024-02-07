package com.mgrabek.recommendationservice.repository;

import com.mgrabek.recommendationservice.model.entity.CryptoValueTimestamp;
import com.mgrabek.recommendationservice.model.entity.CryptoValueTimestampId;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoGeneralInformation;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoNormalizedRange;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CryptoValueRepository extends JpaRepository<CryptoValueTimestamp, CryptoValueTimestampId> {

    @Query("SELECT c.id.symbol as symbol, MAX(c.price) AS maxPrice, MIN(c.price) AS minPrice, (MAX(c.price) - MIN(c.price)) / MIN(c.price) AS normalizedRange " +
            "FROM CryptoValueTimestamp c " +
            "GROUP BY c.id.symbol " +
            "ORDER BY normalizedRange DESC")
    List<CryptoNormalizedRange> findDescendingSortedCryptosByNormalizedRange();

    @Query("SELECT c.id.symbol as symbol, MAX(c.price) AS maxPrice, MIN(c.price) AS minPrice, (MAX(c.price) - MIN(c.price)) / MIN(c.price) AS normalizedRange " +
            "FROM CryptoValueTimestamp c " +
            "WHERE CAST(c.id.timestamp As date) = :specificDate " +
            "GROUP BY c.id.symbol " +
            "ORDER BY normalizedRange DESC ")
    List<CryptoNormalizedRange> findCryptoWithHighestNormalizedRangeForSpecificDay(LocalDate specificDate, Pageable pageable);

    @Query("SELECT " +
            "    c.id.symbol as symbol, " +
            "    MIN(c.price) AS minPrice, " +
            "    MAX(c.price) AS maxPrice, " +
            "    (SELECT c1.price FROM CryptoValueTimestamp c1 WHERE c1.id.symbol = c.id.symbol AND c1.id.timestamp = (SELECT MIN(c2.id.timestamp) FROM CryptoValueTimestamp c2 WHERE c2.id.symbol = c.id.symbol)) AS oldest, " +
            "    (SELECT c2.price FROM CryptoValueTimestamp c2 WHERE c2.id.symbol = c.id.symbol AND c2.id.timestamp = (SELECT MAX(c3.id.timestamp) FROM CryptoValueTimestamp c3 WHERE c3.id.symbol = c.id.symbol)) AS newest " +
            "FROM CryptoValueTimestamp c " +
            "WHERE c.id.symbol = :symbol " +
            "GROUP BY c.id.symbol")
    CryptoGeneralInformation findGeneralInformationForCrypto(String symbol);

    @Query("SELECT COUNT(c) > 0 FROM CryptoValueTimestamp c WHERE c.id.symbol = :symbol")
    boolean existsBySymbol(String symbol);
}


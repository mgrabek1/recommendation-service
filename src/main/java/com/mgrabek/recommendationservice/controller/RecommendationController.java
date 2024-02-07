package com.mgrabek.recommendationservice.controller;

import com.mgrabek.recommendationservice.model.dto.CryptoNormalizedRangeDto;
import com.mgrabek.recommendationservice.model.dto.CryptoGeneralInformationDto;
import com.mgrabek.recommendationservice.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @Operation(summary = "Calculate a descending sorted list of all the cryptos, comparing the normalized range", tags = "Calculation")
    @GetMapping("/calculate/normalized-ranges/sorted")
    public List<CryptoNormalizedRangeDto> calculateNormalizedRanges() {
        return recommendationService.calculateNormalizedRanges();
    }

    @Operation(summary = "Calculate the highest normalized range for a specific day", tags = "Calculation")
    @GetMapping("/calculate/highest-normalized-range")
    public CryptoNormalizedRangeDto calculate(@RequestParam LocalDate date) {
        return recommendationService.calculate(date);
    }

    @Operation(summary = "Calculate the oldest, newest, max and min values for a requested crypto", tags = "Calculation")
    @GetMapping("/calculate/{symbol}")
    public CryptoGeneralInformationDto calculate(@PathVariable String symbol) {
        return recommendationService.calculate(symbol);
    }

}

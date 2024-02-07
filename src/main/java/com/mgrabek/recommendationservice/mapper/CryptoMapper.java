package com.mgrabek.recommendationservice.mapper;

import com.mgrabek.recommendationservice.model.dto.CryptoNormalizedRangeDto;
import com.mgrabek.recommendationservice.model.dto.CryptoGeneralInformationDto;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoGeneralInformation;
import com.mgrabek.recommendationservice.model.entity.projection.CryptoNormalizedRange;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CryptoMapper {
    List<CryptoNormalizedRangeDto> toCryptosNormalizedRange(List<CryptoNormalizedRange> cryptoDetails);

    CryptoNormalizedRangeDto toCryptoNormalizedRange(CryptoNormalizedRange cryptoNormalizedRange);

    CryptoGeneralInformationDto toCryptoGeneralInformation(CryptoGeneralInformation cryptoGeneralInformation);

}

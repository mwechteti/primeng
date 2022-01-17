package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Country;
import com.teamwill.rmkpro.service.dto.CountryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Country} and its DTO called {@link CountryDTO}.
 */
@Mapper
public interface CountryMapper {

    CountryDTO mapEntityToDto(Country entity);

    Country mapDtoToEntity(CountryDTO entityDto);

}

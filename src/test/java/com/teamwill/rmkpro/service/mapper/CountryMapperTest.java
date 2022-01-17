package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Country;
import com.teamwill.rmkpro.service.dto.CountryDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMapperTest {

    private CountryMapper mapper = Mappers.getMapper(CountryMapper.class);

    @Test
    void mapEntityToDto() {
        // Given
        Country country = new Country(2L, "Denmark", "DK");

        // When
        CountryDTO result = mapper.mapEntityToDto(country);

        // Then
        assertThat(result.getId()).isEqualTo(country.getId());
        assertThat(result.getName()).isEqualTo(country.getName());
        assertThat(result.getCode()).isEqualTo(country.getCode());
    }

    @Test
    void mapDtoToEntity() {
        // Given
        CountryDTO countryDTO = new CountryDTO(1L, "United Kingdom", "UK");

        // When
        Country result = mapper.mapDtoToEntity(countryDTO);

        // Then
        assertThat(result.getId()).isEqualTo(countryDTO.getId());
        assertThat(result.getName()).isEqualTo(countryDTO.getName());
        assertThat(result.getCode()).isEqualTo(countryDTO.getCode());
    }
}

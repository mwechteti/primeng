package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Address;
import com.teamwill.rmkpro.domain.Country;
import com.teamwill.rmkpro.service.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AddressMapperTest {

    private AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void mapEntityToDto() {
        // Given
        Address address = new Address();
        address.setId(3L);
        address.setLabel("My main address");
        address.setMain(true);
        address.setAddressLine1("5 Street lane. ");
        address.setAddressLine2("Heartfordshire");
        address.setPostCode("GH ct12X");
        address.setCity("Manchester");
        address.setCountry(new Country(1L, "United Kingdom", "UK"));

        // When
        AddressDTO result = mapper.mapEntityToDto(address);

        // Then
        assertThat(result.getId()).isEqualTo(address.getId());
        assertThat(result.getLabel()).isEqualTo(address.getLabel());
        assertThat(result.getMain()).isEqualTo(address.getMain());
        assertThat(result.getAddressLine1()).isEqualTo(address.getAddressLine1());
        assertThat(result.getAddressLine2()).isEqualTo(address.getAddressLine2());
        assertThat(result.getPostCode()).isEqualTo(address.getPostCode());
        assertThat(result.getCity()).isEqualTo(address.getCity());

        assertThat(result.getCountry().getId()).isEqualTo(address.getCountry().getId());
        assertThat(result.getCountry().getName()).isEqualTo(address.getCountry().getName());
        assertThat(result.getCountry().getCode()).isEqualTo(address.getCountry().getCode());
    }

    @Test
    void mapDtoToEntity() {
    }
}

package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Address;
import com.teamwill.rmkpro.domain.LegalEntity;
import com.teamwill.rmkpro.domain.LegalEntityType;
import com.teamwill.rmkpro.domain.Vehicle;
import com.teamwill.rmkpro.service.dto.LegalEntityDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LegalEntityMapperTest {

    private LegalEntityMapper mapper = Mappers.getMapper(LegalEntityMapper.class);

    @Test
    void mapEntityToDto() {
        // Given
        Address address1 = Mockito.mock(Address.class);
        Address address2 = Mockito.mock(Address.class);
        Set<Address> addresses = new HashSet<>();
        Collections.addAll(addresses, address1, address2);

        Vehicle vehicle1 = Mockito.mock(Vehicle.class);
        Vehicle vehicle2 = Mockito.mock(Vehicle.class);
        Vehicle vehicle3 = Mockito.mock(Vehicle.class);
        Set<Vehicle> vehicles = new HashSet<>();
        Collections.addAll(vehicles, vehicle1, vehicle2, vehicle3);

        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(1L);
        legalEntity.setName("Coca-Cola");
        legalEntity.setType(new LegalEntityType(10L, "Company"));
        legalEntity.setAddresses(addresses);
        legalEntity.setVehicles(vehicles);

        // When
        LegalEntityDTO result = mapper.mapEntityToDto(legalEntity);

        // Then
        assertThat(result.getId()).isEqualTo(legalEntity.getId());
        assertThat(result.getName()).isEqualTo(legalEntity.getName());
        assertThat(result.getType()).isEqualTo(legalEntity.getType());
        assertThat(result.getAddresses()).isNotNull().isNotEmpty();
        assertThat(result.getAddresses().size()).isEqualTo(legalEntity.getAddresses().size());
        assertThat(result.getVehicles()).isNotNull().isNotEmpty();
        assertThat(result.getVehicles().size()).isEqualTo(legalEntity.getVehicles().size());
    }

    @Test
    void mapDtoToEntity() {
    }
}

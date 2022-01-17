package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Make;
import com.teamwill.rmkpro.domain.Model;
import com.teamwill.rmkpro.domain.Vehicle;
import com.teamwill.rmkpro.enums.FuelType;
import com.teamwill.rmkpro.service.dto.VehicleDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleMapperTest {

    private VehicleMapper mapper = Mappers.getMapper(VehicleMapper.class);

    @Test
    void mapEntityToDto() {
        // Given
        Vehicle vehicle = new Vehicle();
        vehicle.setId(10L);
        vehicle.setMileage(160000);
        vehicle.setPlateNumber("CV-99-HY");
        vehicle.setVin("9UGF65C0XSE55FDS");
        Make make = new Make(22L, "Toyota");
        vehicle.setMake(make);
        vehicle.setModel(new Model(1L, "RAV4", 1982, make));
        vehicle.setFuelType(FuelType.PETROL);

        // When
        VehicleDTO result = mapper.mapEntityToDto(vehicle);

        // Then
        assertThat(result.getId()).isEqualTo(vehicle.getId());
        assertThat(result.getMileage()).isEqualTo(vehicle.getMileage());
        assertThat(result.getFuelType()).isEqualTo(vehicle.getFuelType());
        assertThat(result.getPlateNumber()).isEqualTo(vehicle.getPlateNumber());
        assertThat(result.getVin()).isEqualTo(vehicle.getVin());
        assertThat(result.getMake().getId()).isEqualTo(vehicle.getMake().getId());
        assertThat(result.getMake().getLabel()).isEqualTo(vehicle.getMake().getLabel());
        assertThat(result.getModel().getId()).isEqualTo(vehicle.getModel().getId());
        assertThat(result.getModel().getLabel()).isEqualTo(vehicle.getModel().getLabel());
        assertThat(result.getModel().getYear()).isEqualTo(vehicle.getModel().getYear());
    }

}

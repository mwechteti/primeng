package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Make;
import com.teamwill.rmkpro.domain.Model;
import com.teamwill.rmkpro.domain.Portfolio;
import com.teamwill.rmkpro.domain.PortfolioItem;
import com.teamwill.rmkpro.domain.PortfolioItemStatus;
import com.teamwill.rmkpro.domain.PortfolioItemStatusEntry;
import com.teamwill.rmkpro.domain.Vehicle;
import com.teamwill.rmkpro.enums.FuelType;
import com.teamwill.rmkpro.enums.PortfolioItemStatusEnum;
import com.teamwill.rmkpro.service.dto.PortfolioItemDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PortfolioItemMapperTest {

    private PortfolioItemMapper mapper = Mappers.getMapper(PortfolioItemMapper.class);

    PortfolioItem portfolioItem = new PortfolioItem();
    private Make make;
    private Model model;
    private Vehicle vehicle;
    private Set<PortfolioItemStatusEntry> statusEntries;

    @BeforeEach
    public void init() {
        portfolioItem.setId(101L);
        make = new Make(22L, "Toyota");
        model = new Model(1L, "RAV4", 1982, make);
        vehicle = new Vehicle();
        vehicle.setId(10L);
        vehicle.setMileage(160000);
        vehicle.setPlateNumber("HX0VHG");
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setFuelType(FuelType.PETROL);

        PortfolioItemStatusEntry statusEntry1 = new PortfolioItemStatusEntry();
        statusEntry1.setId(1L);
        statusEntry1.setStatus(new PortfolioItemStatus(10, PortfolioItemStatusEnum.COLLECTED));
        statusEntry1.setStatusCreationDate(LocalDate.of(2019, 12, 23));
        PortfolioItemStatusEntry statusEntry2 = new PortfolioItemStatusEntry();
        statusEntry2.setId(2L);
        statusEntry2.setStatus(new PortfolioItemStatus(10, PortfolioItemStatusEnum.COLLECTED));
        statusEntry2.setStatusCreationDate(LocalDate.of(2020, 1, 1));
        statusEntries = new HashSet<>();
        Collections.addAll(statusEntries, statusEntry1, statusEntry2);
    }


    @Test
    void mapEntityToDto() {
        // Given
        portfolioItem.setVehicle(vehicle);
        portfolioItem.setStatusEntries(statusEntries);
        portfolioItem.portfolio(mock(Portfolio.class));

        // When
        PortfolioItemDTO result = mapper.mapEntityToDto(portfolioItem);

        // Then
        assertThat(result.getId()).isEqualTo(portfolioItem.getId());
        assertThat(result.getVehicle().getId()).isEqualTo(vehicle.getId());
        assertThat(result.getVehicle().getMileage()).isEqualTo(vehicle.getMileage());
        assertThat(result.getVehicle().getFuelType()).isEqualTo(vehicle.getFuelType());
        assertThat(result.getVehicle().getMake().getLabel()).isEqualTo(vehicle.getMake().getLabel());
        assertThat(result.getVehicle().getModel().getLabel()).isEqualTo(vehicle.getModel().getLabel());
        Assertions.assertThat(result.getStatusEntries()).isNotNull().isNotEmpty();
        Assertions.assertThat(result.getStatusEntries().size()).isEqualTo(portfolioItem.getStatusEntries().size());
    }

    @Test
    void mapDtoToEntity() {
    }
}

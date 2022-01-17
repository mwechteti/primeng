package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Vehicle;
import com.teamwill.rmkpro.service.dto.VehicleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Vehicle} and its DTO called {@link VehicleDTO}.
 */
@Mapper
public interface VehicleMapper {

    VehicleDTO mapEntityToDto(Vehicle entity);

    Vehicle mapDtoToEntity(VehicleDTO entityDto);

}

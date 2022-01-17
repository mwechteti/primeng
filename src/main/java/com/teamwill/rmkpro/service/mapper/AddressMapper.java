package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Address;
import com.teamwill.rmkpro.service.dto.AddressDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link com.teamwill.rmkpro.domain.Address} and its DTO called {@link com.teamwill.rmkpro.service.dto.AddressDTO}.
 * MapStruct creates the implementation for us
 */
@Mapper
public interface AddressMapper {

    AddressDTO mapEntityToDto(Address entity);

    Address mapDtoToEntity(AddressDTO entityDTO);

}

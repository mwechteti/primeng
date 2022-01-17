package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Make;
import com.teamwill.rmkpro.service.dto.MakeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Make} and its DTO called {@link MakeDTO}.
 */
@Mapper
public interface MakeMapper {

    MakeDTO mapEntityToDto(Make entity);

    Make mapDtoToEntity(MakeDTO entityDto);

}

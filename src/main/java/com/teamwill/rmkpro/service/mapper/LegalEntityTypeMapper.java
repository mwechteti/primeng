package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.LegalEntityType;
import com.teamwill.rmkpro.service.dto.LegalEntityTypeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link LegalEntityType} and its DTO called {@link LegalEntityTypeDTO}.
 */
@Mapper
public interface LegalEntityTypeMapper {

    LegalEntityTypeDTO mapEntityToDto(LegalEntityType entity);

    LegalEntityType mapDtoToEntity(LegalEntityTypeDTO entityDto);

}

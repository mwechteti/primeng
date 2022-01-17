package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.LegalEntity;
import com.teamwill.rmkpro.service.dto.LegalEntityDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link LegalEntity} and its DTO called {@link LegalEntityDTO}.
 */
@Mapper
public interface LegalEntityMapper {

    LegalEntityDTO mapEntityToDto(LegalEntity entity);

    LegalEntity mapDtoToEntity(LegalEntityDTO entityDTO);

}

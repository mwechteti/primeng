package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Model;
import com.teamwill.rmkpro.service.dto.ModelDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Model} and its DTO called {@link ModelDTO}.
 */
@Mapper
public interface ModelMapper {

    ModelDTO mapEntityToDto(Model entity);

    Model mapDtoToEntity(ModelDTO entityDto);

}

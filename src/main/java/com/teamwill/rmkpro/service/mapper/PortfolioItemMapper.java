package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.PortfolioItem;
import com.teamwill.rmkpro.service.dto.PortfolioItemDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link com.teamwill.rmkpro.domain.PortfolioItem} and its DTO called {@link com.teamwill.rmkpro.service.dto.PortfolioItemDTO}.
 */
@Mapper
public interface PortfolioItemMapper {

    PortfolioItemDTO mapEntityToDto(PortfolioItem entity);

    PortfolioItem mapDtoToEntity(PortfolioItemDTO entityDto);

}

package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Portfolio;
import com.teamwill.rmkpro.service.dto.PortfolioDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Portfolio} and its DTO called {@link PortfolioDTO}.
 */
@Mapper
public interface PortfolioMapper {

    PortfolioDTO mapEntityToDto(Portfolio entity);

    Portfolio mapDtoToEntity(PortfolioDTO entityDto);

}

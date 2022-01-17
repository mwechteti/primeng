package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.PortfolioItemStatusEntry;
import com.teamwill.rmkpro.service.dto.PortfolioItemStatusEntryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link PortfolioItemStatusEntry} and its DTO called {@link PortfolioItemStatusEntryDTO}.
 */
@Mapper
public interface PortfolioItemStatusEntryMapper {

    PortfolioItemStatusEntryDTO mapEntityToDto(PortfolioItemStatusEntry entity);

    PortfolioItemStatusEntry mapDtoToEntity(PortfolioItemStatusEntryDTO entityDto);

}

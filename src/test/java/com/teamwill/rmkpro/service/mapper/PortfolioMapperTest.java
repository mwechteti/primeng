package com.teamwill.rmkpro.service.mapper;

import com.teamwill.rmkpro.domain.Portfolio;
import com.teamwill.rmkpro.domain.PortfolioItem;
import com.teamwill.rmkpro.service.dto.PortfolioDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PortfolioMapperTest {

    private PortfolioMapper mapper = Mappers.getMapper(PortfolioMapper.class);

    @Test
    void mapEntityToDto() {
        // Given
        PortfolioItem portfolioItem1 = mock(PortfolioItem.class);
        PortfolioItem portfolioItem2 = mock(PortfolioItem.class);
        Set<PortfolioItem> portfolioItems = new HashSet<>();
        Collections.addAll(portfolioItems, portfolioItem1, portfolioItem2);

        Portfolio portfolio = new Portfolio();
        portfolio.setId(10L);
        portfolio.setLabel("FR_PORTFOLIO");
        portfolio.setPortfolioItems(portfolioItems);

        // When
        PortfolioDTO result = mapper.mapEntityToDto(portfolio);

        // Then
        assertThat(result.getId()).isEqualTo(portfolio.getId());
        assertThat(result.getLabel()).isEqualTo(portfolio.getLabel());
        assertThat(result.getPortfolioItems()).isNotNull().isNotEmpty();
        assertThat(result.getPortfolioItems().size()).isEqualTo(portfolio.getPortfolioItems().size());
    }

    @Test
    void mapDtoToEntity() {
    }
}

package com.teamwill.rmkpro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamwill.rmkpro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortfolioItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortfolioItem.class);
        PortfolioItem portfolioItem1 = new PortfolioItem();
        portfolioItem1.setId(1L);
        PortfolioItem portfolioItem2 = new PortfolioItem();
        portfolioItem2.setId(portfolioItem1.getId());
        assertThat(portfolioItem1).isEqualTo(portfolioItem2);
        portfolioItem2.setId(2L);
        assertThat(portfolioItem1).isNotEqualTo(portfolioItem2);
        portfolioItem1.setId(null);
        assertThat(portfolioItem1).isNotEqualTo(portfolioItem2);
    }
}

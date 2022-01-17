package com.teamwill.rmkpro.domain;

import com.teamwill.rmkpro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioItemDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortfolioItemDetails.class);
        PortfolioItemDetails portfolioItemDetails1 = new PortfolioItemDetails();
        portfolioItemDetails1.setId(1L);
        PortfolioItemDetails portfolioItemDetails2 = new PortfolioItemDetails();
        portfolioItemDetails2.setId(portfolioItemDetails1.getId());
        assertThat(portfolioItemDetails1).isEqualTo(portfolioItemDetails2);
        portfolioItemDetails2.setId(2L);
        assertThat(portfolioItemDetails1).isNotEqualTo(portfolioItemDetails2);
        portfolioItemDetails1.setId(null);
        assertThat(portfolioItemDetails1).isNotEqualTo(portfolioItemDetails2);
    }
}

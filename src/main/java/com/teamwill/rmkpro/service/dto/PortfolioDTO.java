package com.teamwill.rmkpro.service.dto;

import com.teamwill.rmkpro.domain.Portfolio;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO representing a portfolio
 */
public class PortfolioDTO {

    private Long id;

    private String label;

    private Set<PortfolioItemDTO> portfolioItems = new HashSet<>();

    public PortfolioDTO() {
        // Empty constructor needed for Jackson.
    }

    public PortfolioDTO(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.label = portfolio.getLabel();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<PortfolioItemDTO> getPortfolioItems() {
        return portfolioItems;
    }

    public void setPortfolioItems(Set<PortfolioItemDTO> portfolioItems) {
        this.portfolioItems = portfolioItems;
    }
}

package com.teamwill.rmkpro.service.dto;

import com.teamwill.rmkpro.domain.PortfolioItem;
import com.teamwill.rmkpro.domain.PortfolioItemStatus;

import java.time.LocalDate;

/**
 * A DTO representing a portfolio
 */
public class PortfolioItemStatusEntryDTO {

    private Long id;

    private PortfolioItem portfolioItem;

    private PortfolioItemStatus status;

    private LocalDate statusCreationDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PortfolioItem getPortfolioItem() {
        return portfolioItem;
    }

    public void setPortfolioItem(PortfolioItem portfolioItem) {
        this.portfolioItem = portfolioItem;
    }

    public PortfolioItemStatus getStatus() {
        return status;
    }

    public void setStatus(PortfolioItemStatus status) {
        this.status = status;
    }

    public LocalDate getStatusCreationDate() {
        return statusCreationDate;
    }

    public void setStatusCreationDate(LocalDate statusCreationDate) {
        this.statusCreationDate = statusCreationDate;
    }
}

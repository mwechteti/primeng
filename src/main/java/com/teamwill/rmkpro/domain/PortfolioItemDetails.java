package com.teamwill.rmkpro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A PortfolioItemDetails.
 */
@Entity
@Table(name = "portfolio_item_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortfolioItemDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    /**
     * We have a shared primary key between PortfolioItemDetails and PortfolioItem
     * The foreign key on PortfolioItemDetails acts as a primary key for cleaner design and better performance
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_item_id", nullable = false)
    @MapsId
    private PortfolioItem portfolioItem;

    @Column(name = "current_location")
    private String currentLocation;

    @Column(name = "stock_entrance_date")
    private LocalDate stockEntranceDate;

    @Column(name = "reserve_price")
    private BigDecimal reservePrice;

    @Column(name = "proposed_sales_price")
    private BigDecimal proposedSalesPrice;

    @Column(name = "net_book_value")
    private BigDecimal netBookValue;

    public Long getId() {
        return this.id;
    }

    public PortfolioItemDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public LocalDate getStockEntranceDate() {
        return stockEntranceDate;
    }

    public void setStockEntranceDate(LocalDate stockEntranceDate) {
        this.stockEntranceDate = stockEntranceDate;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public BigDecimal getProposedSalesPrice() {
        return proposedSalesPrice;
    }

    public void setProposedSalesPrice(BigDecimal proposedSalesPrice) {
        this.proposedSalesPrice = proposedSalesPrice;
    }

    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    public PortfolioItem getPortfolioItem() {
        return portfolioItem;
    }

    public void setPortfolioItem(PortfolioItem portfolioItem) {
        this.portfolioItem = portfolioItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortfolioItemDetails)) {
            return false;
        }
        return id != null && id.equals(((PortfolioItemDetails) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PortfolioItemDetails{" +
            "id=" + id +
            ", currentLocation='" + currentLocation + '\'' +
            ", stockEntranceDate=" + stockEntranceDate +
            ", reservePrice=" + reservePrice +
            ", proposedSalesPrice=" + proposedSalesPrice +
            ", netBookValue=" + netBookValue +
            '}';
    }
}

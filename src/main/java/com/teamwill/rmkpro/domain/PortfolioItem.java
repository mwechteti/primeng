package com.teamwill.rmkpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PortfolioItem.
 */
@Entity
@Table(name = "portfolio_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortfolioItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="vehicle_id", unique = true)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn( name="portfolio_id", nullable = false )
    private Portfolio portfolio;

    @OneToMany(mappedBy = "portfolioItem", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "portfolioItem" }, allowSetters = true)
    private Set<PortfolioItemStatusEntry> statusEntries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PortfolioItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public PortfolioItem vehicle(Vehicle vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public PortfolioItem portfolio(Portfolio portfolio) {
        this.setPortfolio(portfolio);
        return this;
    }

    public Set<PortfolioItemStatusEntry> getStatusEntries() {
        return statusEntries;
    }

    public void setStatusEntries(Set<PortfolioItemStatusEntry> statusEntries) {
        this.statusEntries = statusEntries;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortfolioItem)) {
            return false;
        }
        return id != null && id.equals(((PortfolioItem) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PortfolioItem{" +
            "id=" + id +
            ", vehicle=" + vehicle.toString() +
            ", portfolio=" + portfolio.toString() +
            '}';
    }
}

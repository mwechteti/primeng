package com.teamwill.rmkpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PortfolioItemStatusEntry
 */
@Entity
@Table(name = "portfolio_item_status_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortfolioItemStatusEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(unique = true)
    @JsonIgnoreProperties(value = { "vehicle", "portfolioItemDetails", "portfolio" }, allowSetters = true)
    private PortfolioItem portfolioItem;

    @OneToOne
    @JoinColumn(name = "status_id", unique = true)
    private PortfolioItemStatus status;

    @NotNull
    @Column(name = "status_creation_date", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortfolioItemStatusEntry)) {
            return false;
        }
        return id != null && id.equals(((PortfolioItemStatusEntry) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PortfolioItemStatusEntry{" +
            "id=" + id +
            ", portfolioItem=" + portfolioItem.toString() +
            ", status=" + status.toString() +
            ", statusCreationDate=" + statusCreationDate.toString() +
            '}';
    }
}

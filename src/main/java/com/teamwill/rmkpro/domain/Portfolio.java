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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Portfolio.
 */
@Entity
@Table(name = "portfolio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @OneToMany(mappedBy = "portfolio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "portfolio" }, allowSetters = true)
    private Set<PortfolioItem> portfolioItems = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_portfolio",
        joinColumns = @JoinColumn(name = "portfolio_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "login", "firstName", "lastName", "email", "activated", "langKey", "imageUrl", "authorities" }, allowSetters = true)
    private Set<User> assignedUsers = new HashSet<>();

    public Portfolio() {
    }

    public Portfolio(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Portfolio id(Long id) {
        this.setId(id);
        return this;
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

    public Set<PortfolioItem> getPortfolioItems() {
        return this.portfolioItems;
    }

    public void setPortfolioItems(Set<PortfolioItem> portfolioItems) {
        if (this.portfolioItems != null) {
            this.portfolioItems.forEach(i -> i.setPortfolio(null));
        }
        if (portfolioItems != null) {
            portfolioItems.forEach(i -> i.setPortfolio(this));
        }
        this.portfolioItems = portfolioItems;
    }

    public Portfolio portfolioItems(Set<PortfolioItem> portfolioItems) {
        this.setPortfolioItems(portfolioItems);
        return this;
    }

    public Portfolio addPortfolioItem(PortfolioItem portfolioItem) {
        this.portfolioItems.add(portfolioItem);
        portfolioItem.setPortfolio(this);
        return this;
    }

    public Portfolio removePortfolioItem(PortfolioItem portfolioItem) {
        this.portfolioItems.remove(portfolioItem);
        portfolioItem.setPortfolio(null);
        return this;
    }

    public Set<User> getAssignedUsers() {
        return this.assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Portfolio assignedUsers(Set<PortfolioItem> portfolioItems) {
        this.setPortfolioItems(portfolioItems);
        return this;
    }

    public Portfolio addAssignedUser(User user) {
        this.assignedUsers.add(user);
        user.addPortfolio(this);
        return this;
    }

    public Portfolio removeAssignedUser(User user) {
        this.assignedUsers.remove(user);
        user.removePortfolio(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Portfolio)) {
            return false;
        }
        return id != null && id.equals(((Portfolio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Portfolio{" +
            "id=" + getId() +
            "label=" + getLabel() +
            "}";
    }
}

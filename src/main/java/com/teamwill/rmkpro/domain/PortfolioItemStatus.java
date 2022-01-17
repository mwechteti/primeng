package com.teamwill.rmkpro.domain;

import com.teamwill.rmkpro.enums.PortfolioItemStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A PortfolioItemStatus.
 */
@Entity
@Table(name = "portfolio_item_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortfolioItemStatus  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "label", nullable = false)
    @Enumerated(EnumType.STRING)
    private PortfolioItemStatusEnum label;

    public PortfolioItemStatus() {
    }

    public PortfolioItemStatus(Integer id, PortfolioItemStatusEnum label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PortfolioItemStatus id(Integer id) {
        this.setId(id);
        return this;
    }

    public PortfolioItemStatusEnum getLabel() {
        return this.label;
    }

    public PortfolioItemStatus label(PortfolioItemStatusEnum label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(PortfolioItemStatusEnum label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortfolioItemStatus)) {
            return false;
        }
        return id != null && id.equals(((PortfolioItemStatus) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PortfolioItemStatus{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }


}

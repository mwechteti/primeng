package com.teamwill.rmkpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LegalEntity.
 */
@Entity
@Table(name = "legal_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LegalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private LegalEntityType type;

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "make", "model", "owner" }, allowSetters = true)
    private Set<Vehicle> vehicles = new HashSet<>();

    @OneToMany(mappedBy = "legalEntity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "legalEntity" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LegalEntity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LegalEntity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LegalEntityType getType() {
        return this.type;
    }

    public void setType(LegalEntityType legalEntityType) {
        this.type = legalEntityType;
    }

    public LegalEntity type(LegalEntityType legalEntityType) {
        this.setType(legalEntityType);
        return this;
    }

    public Set<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        if (this.vehicles != null) {
            this.vehicles.forEach(i -> i.setOwner(null));
        }
        if (vehicles != null) {
            vehicles.forEach(i -> i.setOwner(this));
        }
        this.vehicles = vehicles;
    }

    public LegalEntity vehicles(Set<Vehicle> vehicles) {
        this.setVehicles(vehicles);
        return this;
    }

    public LegalEntity addVehicles(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setOwner(this);
        return this;
    }

    public LegalEntity removeVehicles(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setOwner(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setLegalEntity(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setLegalEntity(this));
        }
        this.addresses = addresses;
    }

    public LegalEntity addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public LegalEntity addAddresses(Address address) {
        this.addresses.add(address);
        address.setLegalEntity(this);
        return this;
    }

    public LegalEntity removeAddresses(Address address) {
        this.addresses.remove(address);
        address.setLegalEntity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LegalEntity)) {
            return false;
        }
        return id != null && id.equals(((LegalEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LegalEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

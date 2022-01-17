package com.teamwill.rmkpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamwill.rmkpro.enums.FuelType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @NotNull
    @Column(name = "first_registration_date", nullable = false)
    private LocalDate firstRegistrationDate;

    @NotNull
    @Column(name = "used", nullable = false)
    private Boolean used;

    @NotNull
    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @NotNull
    @Column(name = "mileage_unit", nullable = false, columnDefinition = "char(2)")
    private String mileageUnit;

    @NotNull
    @Column(name = "fuel_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "vin")
    private String vin;

    @ManyToOne(cascade = CascadeType.ALL)
    private Make make;

    @ManyToOne
    @JsonIgnoreProperties(value = { "make" }, allowSetters = true)
    private Model model;

    @ManyToOne
    @JsonIgnoreProperties(value = { "type", "vehicles", "addresses" }, allowSetters = true)
    private LegalEntity owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return this.plateNumber;
    }

    public Vehicle plateNumber(String plateNumber) {
        this.setPlateNumber(plateNumber);
        return this;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public LocalDate getFirstRegistrationDate() {
        return this.firstRegistrationDate;
    }

    public Vehicle firstRegistrationDate(LocalDate firstRegistrationDate) {
        this.setFirstRegistrationDate(firstRegistrationDate);
        return this;
    }

    public void setFirstRegistrationDate(LocalDate firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public Boolean getUsed() {
        return this.used;
    }

    public Vehicle used(Boolean used) {
        this.setUsed(used);
        return this;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Integer getMileage() {
        return this.mileage;
    }

    public Vehicle mileage(Integer mileage) {
        this.setMileage(mileage);
        return this;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getMileageUnit() {
        return this.mileageUnit;
    }

    public Vehicle mileageUnit(String mileageUnit) {
        this.setMileageUnit(mileageUnit);
        return this;
    }

    public void setMileageUnit(String mileageUnit) {
        this.mileageUnit = mileageUnit;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public Vehicle fuelType(FuelType fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getVin() {
        return this.vin;
    }

    public Vehicle vin(String vin) {
        this.setVin(vin);
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Make getMake() {
        return this.make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Vehicle make(Make make) {
        this.setMake(make);
        return this;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Vehicle model(Model model) {
        this.setModel(model);
        return this;
    }

    public LegalEntity getOwner() {
        return this.owner;
    }

    public void setOwner(LegalEntity legalEntity) {
        this.owner = legalEntity;
    }

    public Vehicle owner(LegalEntity legalEntity) {
        this.setOwner(legalEntity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", plateNumber='" + getPlateNumber() + "'" +
            ", firstRegistrationDate='" + getFirstRegistrationDate() + "'" +
            ", used='" + getUsed() + "'" +
            ", mileage=" + getMileage() +
            ", mileageUnit='" + getMileageUnit() + "'" +
            ", fuelType='" + getFuelType() + "'" +
            ", vin='" + getVin() + "'" +
            "}";
    }
}

package com.teamwill.rmkpro.service.dto;

import com.teamwill.rmkpro.domain.LegalEntityType;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO representing a portfolio
 */
public class LegalEntityDTO {

    private Long id;

    private String name;

    private LegalEntityType type;

    private Set<AddressDTO> addresses = new HashSet<>();

    private Set<VehicleDTO> vehicles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LegalEntityType getType() {
        return type;
    }

    public void setType(LegalEntityType type) {
        this.type = type;
    }

    public Set<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public Set<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }
}

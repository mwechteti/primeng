package com.teamwill.rmkpro.service.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO representing a PortfolioItem
 */
public class PortfolioItemDTO {

    private Long id;

    private VehicleDTO vehicle;

    private Set<PortfolioItemStatusEntryDTO> statusEntries = new HashSet<>();

    public PortfolioItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public Set<PortfolioItemStatusEntryDTO> getStatusEntries() {
        return statusEntries;
    }

    public void setStatusEntries(Set<PortfolioItemStatusEntryDTO> statusEntries) {
        this.statusEntries = statusEntries;
    }
}

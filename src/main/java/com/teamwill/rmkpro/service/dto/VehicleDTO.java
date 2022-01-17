package com.teamwill.rmkpro.service.dto;

import com.teamwill.rmkpro.enums.FuelType;

import java.time.LocalDate;

/**
 * A DTO representing a Vehicle
 */
public class VehicleDTO {

    private Long id;

    private String plateNumber;

    private LocalDate firstRegistrationDate;

    private Boolean used;

    private Integer mileage;

    private String mileageUnit;

    private FuelType fuelType;

    private String vin;

    private MakeDTO make;

    private ModelDTO model;

    //private LegalEntityDTO owner;

    public VehicleDTO() {
        // Empty constructor needed for Jackson.
    }

//    public VehicleDTO(Vehicle vehicle) {
//        this.id = vehicle.getId();
//        this.plateNumber = vehicle.getPlateNumber();
//        this.firstRegistrationDate = vehicle.getFirstRegistrationDate();
//        this.used = vehicle.getUsed();
//        this.mileage = vehicle.getMileage();
//        this.mileageUnit = vehicle.getMileageUnit();
//        this.fuelType = vehicle.getFuelType();
//        this.vin = vehicle.getVin();
//        this.make = vehicle.getMake();
//        this.model = vehicle.getModel();
//        //this.owner = LegalEntityMapper
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public LocalDate getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(LocalDate firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getMileageUnit() {
        return mileageUnit;
    }

    public void setMileageUnit(String mileageUnit) {
        this.mileageUnit = mileageUnit;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public MakeDTO getMake() {
        return make;
    }

    public void setMake(MakeDTO make) {
        this.make = make;
    }

    public ModelDTO getModel() {
        return model;
    }

    public void setModel(ModelDTO model) {
        this.model = model;
    }

//    public LegalEntityDTO getOwner() {
//        return owner;
//    }
//
//    public void setOwner(LegalEntityDTO owner) {
//        this.owner = owner;
//    }
}

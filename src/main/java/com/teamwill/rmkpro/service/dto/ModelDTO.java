package com.teamwill.rmkpro.service.dto;

/**
 * A DTO representing a Model
 */
public class ModelDTO {

    private Long id;

    private String label;

    private Integer year;

    private MakeDTO make;

    public ModelDTO() {
        // Empty constructor needed for Jackson.
    }

    public ModelDTO(Long id, String label, Integer year, MakeDTO make) {
        this.id = id;
        this.label = label;
        this.year = year;
        this.make = make;
    }

    public Long getId() {
        return id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MakeDTO getMake() {
        return make;
    }

    public void setMake(MakeDTO make) {
        this.make = make;
    }
}

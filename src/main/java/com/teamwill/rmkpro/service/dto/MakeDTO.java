package com.teamwill.rmkpro.service.dto;

/**
 * A DTO representing a Make
 */
public class MakeDTO {

    private Long id;

    private String label;

    public MakeDTO() {
        // Empty constructor needed for Jackson.
    }

    public MakeDTO(Long id, String label) {
        this.id = id;
        this.label = label;
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

}

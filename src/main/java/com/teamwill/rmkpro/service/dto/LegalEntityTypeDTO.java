package com.teamwill.rmkpro.service.dto;

/**
 * A DTO representing a LegalEntityType
 */
public class LegalEntityTypeDTO {

    private Long id;

    private String label;

    public LegalEntityTypeDTO() {}

    public LegalEntityTypeDTO(Long id, String label) {
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

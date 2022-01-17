package com.teamwill.rmkpro.enums;

public enum PortfolioItemStatusEnum {

    COLLECTED("COLLECTED"),
    IN_AUCTION("IN_AUCTION"),
    REQUEST_FOR_COLLECTION("REQUEST_FOR_COLLECTION"),
    SOLD("SOLD");

    private String value;

    PortfolioItemStatusEnum(String value) {
        this.value = value;
    }

}

package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EFundingState {

    PLANNED("PLANNED"),
    APPLIED("APPLIED"),
    GRANTED("GRANTED"),
    REJECTED("REJECTED"),
    UNSPECIFIED("UNSPECIFIED");

    EFundingState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String toString() {
        return state;
    }
}

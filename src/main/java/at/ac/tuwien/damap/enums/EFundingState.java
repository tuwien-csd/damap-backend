package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EFundingState {

    PLANNED("planned"),
    APPLIED("applied"),
    GRANTED("granted"),
    REJECTED("rejected"),
    UNSPECIFIED("unspecified");

    EFundingState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String toString() {
        return state;
    }
}

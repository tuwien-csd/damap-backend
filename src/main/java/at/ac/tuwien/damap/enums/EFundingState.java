package at.ac.tuwien.damap.enums;

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

    @Override
    public String toString() {
        return state;
    }
}

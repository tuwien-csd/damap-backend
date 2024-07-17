package org.damap.base.enums;

/** EFundingState class. */
public enum EFundingState {
  PLANNED("planned"),
  APPLIED("applied"),
  GRANTED("granted"),
  REJECTED("rejected"),
  UNSPECIFIED("unspecified");

  /**
   * Constructor for EFundingState.
   *
   * @param state a {@link java.lang.String} object
   */
  EFundingState(String state) {
    this.state = state;
  }

  /** {@inheritDoc} */
  private final String state;

  @Override
  public String toString() {
    return state;
  }
}

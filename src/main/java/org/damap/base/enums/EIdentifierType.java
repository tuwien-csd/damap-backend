package org.damap.base.enums;

import java.util.ArrayList;
import java.util.List;

/** EIdentifierType class. */
public enum EIdentifierType {
  ORCID("ORCID"),
  ISNI("ISNI"),
  OPENID("OpenId"),
  OTHER("Other"),
  HANDLE("Handle"),
  DOI("DOI"),
  ARK("ARK"),
  URL("URL"),
  HDL("HDL"),
  PURL("PURL"),
  URN("URN"),
  FUNDREF("FundRef"),
  ROR("ROR");

  private final String type;

  private static final List<EIdentifierType> personIdentifierType = new ArrayList<>();
  private static final List<EIdentifierType> datasetIdentifierType = new ArrayList<>();
  private static final List<EIdentifierType> funderIdentifierType = new ArrayList<>();
  private static final List<EIdentifierType> grantIdentifierType = new ArrayList<>();
  private static final List<EIdentifierType> metadataIdentifierType = new ArrayList<>();

  static {
    personIdentifierType.add(EIdentifierType.ORCID);
    personIdentifierType.add(EIdentifierType.ISNI);
    personIdentifierType.add(EIdentifierType.OPENID);
    personIdentifierType.add(EIdentifierType.OTHER);

    datasetIdentifierType.add(EIdentifierType.HANDLE);
    datasetIdentifierType.add(EIdentifierType.DOI);
    datasetIdentifierType.add(EIdentifierType.ARK);
    datasetIdentifierType.add(EIdentifierType.URL);
    datasetIdentifierType.add(EIdentifierType.OTHER);
    datasetIdentifierType.add(EIdentifierType.HDL);
    datasetIdentifierType.add(EIdentifierType.PURL);
    datasetIdentifierType.add(EIdentifierType.URN);

    funderIdentifierType.add(EIdentifierType.FUNDREF);
    funderIdentifierType.add(EIdentifierType.URL);
    funderIdentifierType.add(EIdentifierType.ROR);
    funderIdentifierType.add(EIdentifierType.ISNI);
    funderIdentifierType.add(EIdentifierType.OTHER);

    grantIdentifierType.add(EIdentifierType.URL);
    grantIdentifierType.add(EIdentifierType.OTHER);

    metadataIdentifierType.add(EIdentifierType.URL);
    metadataIdentifierType.add(EIdentifierType.OTHER);
  }

  EIdentifierType(String type) {
    this.type = type;
  }

  /**
   * Getter for the field <code>type</code>.
   *
   * @return a {@link java.lang.String} object
   */
  public String getType() {
    return type;
  }

  /**
   * getPersonIdentifierTypeList.
   *
   * @return a {@link java.util.List} object
   */
  public static List<EIdentifierType> getPersonIdentifierTypeList() {
    return personIdentifierType;
  }

  /**
   * getDatasetIdentifierTypeList.
   *
   * @return a {@link java.util.List} object
   */
  public static List<EIdentifierType> getDatasetIdentifierTypeList() {
    return datasetIdentifierType;
  }

  /**
   * getFunderIdentifierTypeList.
   *
   * @return a {@link java.util.List} object
   */
  public static List<EIdentifierType> getFunderIdentifierTypeList() {
    return funderIdentifierType;
  }

  /**
   * getGrantIdentifierTypeList.
   *
   * @return a {@link java.util.List} object
   */
  public static List<EIdentifierType> getGrantIdentifierTypeList() {
    return grantIdentifierType;
  }

  /**
   * getMetadataIdentifierTypeList.
   *
   * @return a {@link java.util.List} object
   */
  public static List<EIdentifierType> getMetadataIdentifierTypeList() {
    return metadataIdentifierType;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return type;
  }
}

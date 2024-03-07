package at.ac.tuwien.damap.enums;

import java.util.ArrayList;
import java.util.List;

public enum EIdentifierType {

    ORCID("orcid"),
    ISNI("isni"),
    OPENID("openid"),
    OTHER("other"),
    HANDLE("handle"),
    DOI("doi"),
    ARK("ark"),
    URL("url"),
    FUNDREF("fundref"),
    ROR("ror");

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

    public List<EIdentifierType> getPersonIdentifierTypeList() {
        return personIdentifierType;
    }

    public static List<EIdentifierType> getDatasetIdentifierTypeList() {
        return datasetIdentifierType;
    }

    public static List<EIdentifierType> getFunderIdentifierTypeList() {
        return funderIdentifierType;
    }

    public static List<EIdentifierType> getGrantIdentifierTypeList() {
        return grantIdentifierType;
    }

    public static List<EIdentifierType> getMetadataIdentifierTypeList() {
        return metadataIdentifierType;
    }

    @Override
    public String toString() {
        return type;
    }
}

package at.ac.tuwien.damap.enums;

import com.fasterxml.jackson.annotation.JsonValue;

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
    FUNDREF("fundref");

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
        funderIdentifierType.add(EIdentifierType.OTHER);

        grantIdentifierType.add(EIdentifierType.URL);
        grantIdentifierType.add(EIdentifierType.OTHER);

        metadataIdentifierType.add(EIdentifierType.URL);
        metadataIdentifierType.add(EIdentifierType.OTHER);
    }

    EIdentifierType(String type) {
        this.type = type;
    }

    public List<EIdentifierType> getPersonIdentifierTypeList(){
        return personIdentifierType;
    }

    public List<EIdentifierType> getDatasetIdentifierTypeList(){
        return datasetIdentifierType;
    }

    public List<EIdentifierType> getFunderIdentifierTypeList(){
        return funderIdentifierType;
    }

    public List<EIdentifierType> getGrantIdentifierTypeList(){
        return grantIdentifierType;
    }

    public List<EIdentifierType> getMetadataIdentifierTypeList(){
        return metadataIdentifierType;
    }

    @Override
    @JsonValue
    public String toString() {
        return type;
    }
}

package at.ac.tuwien.damap.r3data.dto;

import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.enums.EIdentifierType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDetails {

    private String id;
    private String name;
    private List<String> repositoryIdentifier;
    private String repositoryURL;
    private List<String> repositoryLanguages;
    private String description;
    private Boolean versioning;
    private List<String> contentTypes;
    private List<String> metadataStandards;
    private List<EIdentifierType> pidSystems;

}

package at.ac.tuwien.rest.tiss.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectOverview {

    @Getter
    @Setter
    private String projectId;

    @Getter
    @Setter
    private String shortDescription;

    @Getter
    @Setter
    @JsonbProperty("titleEn")
    private String title;

    @Getter
    @Setter
    private String begin;

    @Getter
    @Setter
    private String end;

    @Getter
    @Setter
    private String orgunit;

    @Getter
    @Setter
    private String orgunitTid;

    @Getter
    @Setter
    private String projectleaderTid;
}

package at.ac.tuwien.rest.projectdatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * This class is used as a target for unmarshalling the JSON response from the TISS API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetails {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String shortDescription;

    @Getter
    @Setter
    @JsonbProperty("title")
    private ProjectTitle title;

    @Getter
    @Setter
    private String projectBegin;

    @Getter
    @Setter
    private String projectEnd;

    @Getter
    @Setter
    @JsonbProperty("abstract")
    private ProjectAbstract projectAbstract;

    @Getter
    @Setter
    @JsonbProperty("institutes")
    private InstituteWrapper institutes;

    @Getter
    @Setter
    private ResearchAreaWrapper researchAreas;

    @Getter
    @Setter
    private FundingSourceWrapper financiers;


    @Override
    public String toString() {
        return "ProjectDetails{" +
                "id='" + id + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", title=" + title +
                ", projectBegin='" + projectBegin + '\'' +
                ", projectEnd='" + projectEnd + '\'' +
                ", projectAbstract=" + projectAbstract +
                ", institutes=" + institutes +
                ", researchAreas=" + researchAreas +
                ", financiers=" + financiers +
                '}';
    }
}

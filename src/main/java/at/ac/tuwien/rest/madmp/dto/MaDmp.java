package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmp {

    private MaDmpIdentifier dmp_id;
    private String title;
    private MaDmpContact contact;
    private List<MaDmpContributor> contributor;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date modified;
    private List<MaDmpDataset> dataset;
    private String description;
    private MaDmpProject project;
    private Boolean ethical_issues_exist;
    private String ethical_issues_report;
}

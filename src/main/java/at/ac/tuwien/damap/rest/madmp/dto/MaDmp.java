package at.ac.tuwien.damap.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.List;

import at.ac.tuwien.damap.domain.Cost;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmp {

    private MaDmpContact contact;
    private List<MaDmpContributor> contributor;
    private Cost cost;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created;
    private List<MaDmpDataset> dataset;
    private String description;
    private MaDmpIdentifier dmp_id;
    private String ethical_issues_description;
    private Boolean ethical_issues_exist;
    private String ethical_issues_report;
    private String language;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date modified;
    private MaDmpProject project;
    private String title;
}

package at.ac.tuwien.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpDataset {

    private String data_quality_assurance;
    private MaDmpIdentifier dataset_id;
    private String description;
    private MaDmpDistribution distribution;
    private Date issued;
    private String keyword;
    private String language;
    private MaDmpMetadata metadata;
    private String personal_data;
    private String preservation_statement;
    private MaDmpSecurityAndPrivacy security_and_privacy;
    private String sensitive_data;
    private MaDmpTechnicalResource technical_resource;
    private String title;
    private String type;

}

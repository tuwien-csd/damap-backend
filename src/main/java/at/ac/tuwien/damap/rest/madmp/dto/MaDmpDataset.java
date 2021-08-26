package at.ac.tuwien.damap.rest.madmp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaDmpDataset {

    private String data_quality_assurance;
    private MaDmpIdentifier dataset_id;
    private String description;
    private List<MaDmpDistribution> distribution;
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

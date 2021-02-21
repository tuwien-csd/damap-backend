package at.ac.tuwien.damap.rest.domain;

import at.ac.tuwien.damap.enums.EDataKind;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmpDO {

    private Long id;
    private String title;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date modified;
    private String description;
    private ProjectDO project;
    private PersonDO contact;
    private EDataKind dataKind;
    private List<ContributorDO> contributors;
    private String noDataExplanation;
    private String metadata;
    private String dataGeneration;
    private String structure;
    private String targetAudience;
    private Boolean personalInformation;
    private Boolean sensitiveData;
    private Boolean legalRestrictions;
    private Boolean ethicalIssuesExist;
    private Boolean committeeApproved;
    private String ethicsReport;
    private String optionalStatement;
    private List<DatasetDO> datasets;
    private List<HostDO> hosts;
}

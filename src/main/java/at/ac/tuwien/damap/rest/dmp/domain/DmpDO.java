package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EDataKind;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.List;

@Data
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
    private String tools;
    private String restrictedDataAccess;
    private Boolean personalData;
    private String personalDataAccess;
    private List<String> personalDataCompliance;
    private String otherPersonalDataCompliance;
    private Boolean sensitiveData;
    private String sensitiveDataSecurity;
    private Boolean legalRestrictions;
    private String legalRestrictionsComment;
    private Boolean ethicalIssuesExist;
    private Boolean committeeApproved;
    private String ethicsReport;
    private String ethicalComplianceStatement;
    private List<DatasetDO> datasets;
    private List<HostDO> hosts;
    private List<StorageDO> storage;
    private List<StorageDO> externalStorage;
    private String externalStorageInfo;
    private String restrictedAccessInfo;
    private String closedAccessInfo;
    private Boolean costsExist;
    private List<CostDO> costs;
}

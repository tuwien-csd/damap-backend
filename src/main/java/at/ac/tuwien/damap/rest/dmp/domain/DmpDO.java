package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.EDataKind;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.ArrayList;
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
    private List<ContributorDO> contributors = new ArrayList<ContributorDO>();
    private String noDataExplanation;
    private String metadata;
    private String dataGeneration;
    private String structure;
    private String targetAudience;
    private String tools;
    private String restrictedDataAccess;
    private Boolean personalData;
    private List<String> personalDataCompliance = new ArrayList<String>();
    private String otherPersonalDataCompliance;
    private Boolean sensitiveData;
    private List<String> sensitiveDataSecurity = new ArrayList<String>();
    private String otherDataSecurityMeasures;
    private String sensitiveDataAccess;
    private Boolean legalRestrictions;
    private List<String> legalRestrictionsDocuments = new ArrayList<String>();
    private String otherLegalRestrictionsDocument;
    private String legalRestrictionsComment;
    private String dataRightsAndAccessControl;
    private Boolean humanParticipants;
    private Boolean ethicalIssuesExist;
    private Boolean committeeReviewed;
    private List<DatasetDO> datasets = new ArrayList<DatasetDO>();
    private List<HostDO> hosts = new ArrayList<HostDO>();
    private List<StorageDO> storage = new ArrayList<StorageDO>();
    private List<StorageDO> externalStorage = new ArrayList<StorageDO>();
    private String externalStorageInfo;
    private String restrictedAccessInfo;
    private String closedAccessInfo;
    private Boolean costsExist;
    private List<CostDO> costs = new ArrayList<CostDO>();
}

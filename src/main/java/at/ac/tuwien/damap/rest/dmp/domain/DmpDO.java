package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Valid
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmpDO {

    private Long id;
    @Size(max = 255)
    private String title;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date modified;
    @Size(max = 4000)
    private String description;
    private ProjectDO project;
    private EDataKind dataKind;
    private EDataKind reusedDataKind;
    private List<ContributorDO> contributors = new ArrayList<ContributorDO>();
    @Size(max = 4000)
    private String noDataExplanation;
    @Size(max = 4000)
    private String metadata;
    @Size(max = 4000)
    private String dataGeneration;
    @Size(max = 4000)
    private String structure;
    private List<EDataQualityType> dataQuality = new ArrayList<EDataQualityType>();
    @Size(max = 4000)
    private String otherDataQuality;
    @Size(max = 4000)
    private String targetAudience;
    @Size(max = 4000)
    private String tools;
    @Size(max = 4000)
    private String restrictedDataAccess;
    private Boolean personalData;
    private Boolean personalDataCris;
    private List<EComplianceType> personalDataCompliance = new ArrayList<EComplianceType>();
    @Size(max = 4000)
    private String otherPersonalDataCompliance;
    private Boolean sensitiveData;
    private Boolean sensitiveDataCris;
    private List<ESecurityMeasure> sensitiveDataSecurity = new ArrayList<ESecurityMeasure>();
    @Size(max = 4000)
    private String otherDataSecurityMeasures;
    @Size(max = 4000)
    private String sensitiveDataAccess;
    private Boolean legalRestrictions;
    private Boolean legalRestrictionsCris;
    private List<EAgreement> legalRestrictionsDocuments = new ArrayList<EAgreement>();
    @Size(max = 4000)
    private String otherLegalRestrictionsDocument;
    @Size(max = 4000)
    private String legalRestrictionsComment;
    @Size(max = 4000)
    private String dataRightsAndAccessControl;
    private Boolean humanParticipants;
    private Boolean humanParticipantsCris;
    private Boolean ethicalIssuesExist;
    private Boolean ethicalIssuesExistCris;
    private Boolean committeeReviewed;
    private Boolean committeeReviewedCris;
    private List<DatasetDO> datasets = new ArrayList<DatasetDO>();
    private List<RepositoryDO> repositories = new ArrayList<RepositoryDO>();
    private List<StorageDO> storage = new ArrayList<StorageDO>();
    private List<ExternalStorageDO> externalStorage = new ArrayList<ExternalStorageDO>();
    @Size(max = 4000)
    private String externalStorageInfo;
    @Size(max = 4000)
    private String restrictedAccessInfo;
    @Size(max = 4000)
    private String closedAccessInfo;
    private Boolean costsExist;
    private Boolean costsExistCris;
    private List<CostDO> costs = new ArrayList<CostDO>();
    private String documentation;

    public ContributorDO getContact(){
        Optional<ContributorDO> contact = contributors.stream().filter(ContributorDO::isContact).findFirst();
        return contact.orElse(null);
    }
}

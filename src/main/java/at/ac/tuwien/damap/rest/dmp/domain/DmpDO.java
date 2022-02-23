package at.ac.tuwien.damap.rest.dmp.domain;

import at.ac.tuwien.damap.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private EDataKind dataKind;
    private List<ContributorDO> contributors = new ArrayList<ContributorDO>();
    private String noDataExplanation;
    private String metadata;
    private String dataGeneration;
    private String structure;
    private List<EDataQualityType> dataQuality = new ArrayList<EDataQualityType>();
    private String otherDataQuality;
    private String targetAudience;
    private String tools;
    private String restrictedDataAccess;
    private Boolean personalData;
    private Boolean personalDataCris = false;
    private List<EComplianceType> personalDataCompliance = new ArrayList<EComplianceType>();
    private String otherPersonalDataCompliance;
    private Boolean sensitiveData;
    private Boolean sensitiveDataCris = false;
    private List<ESecurityMeasure> sensitiveDataSecurity = new ArrayList<ESecurityMeasure>();
    private String otherDataSecurityMeasures;
    private String sensitiveDataAccess;
    private Boolean legalRestrictions;
    private Boolean legalRestrictionsCris = false;
    private List<EAgreement> legalRestrictionsDocuments = new ArrayList<EAgreement>();
    private String otherLegalRestrictionsDocument;
    private String legalRestrictionsComment;
    private String dataRightsAndAccessControl;
    private Boolean humanParticipants;
    private Boolean humanParticipantsCris = false;
    private Boolean ethicalIssuesExist;
    private Boolean ethicalIssuesExistCris = false;
    private Boolean committeeReviewed;
    private Boolean committeeReviewedCris = false;
    private List<DatasetDO> datasets = new ArrayList<DatasetDO>();
    private List<RepositoryDO> repositories = new ArrayList<RepositoryDO>();
    private List<StorageDO> storage = new ArrayList<StorageDO>();
    private List<ExternalStorageDO> externalStorage = new ArrayList<ExternalStorageDO>();
    private String externalStorageInfo;
    private String restrictedAccessInfo;
    private String closedAccessInfo;
    private Boolean costsExist;
    private Boolean costsExistCris = false;
    private List<CostDO> costs = new ArrayList<CostDO>();

    public ContributorDO getContact(){
        Optional<ContributorDO> contact = contributors.stream().filter(ContributorDO::isContact).findFirst();
        return contact.orElse(null);
    }
}

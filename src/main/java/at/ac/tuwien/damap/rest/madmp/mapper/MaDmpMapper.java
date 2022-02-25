package at.ac.tuwien.damap.rest.madmp.mapper;

import at.ac.tuwien.damap.enums.EAgreement;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.ESecurityMeasure;
import at.ac.tuwien.damap.r3data.RepositoriesService;
import at.ac.tuwien.damap.rest.dmp.domain.*;
import at.ac.tuwien.damap.rest.madmp.dto.*;
import lombok.experimental.UtilityClass;
import org.re3data.schema._2_2.*;

import java.net.URI;
import java.util.*;

@UtilityClass
public class MaDmpMapper {

    public Dmp mapToMaDmp(DmpDO dmpDO, Dmp dmp, RepositoriesService repositoriesService) {

        if (dmpDO.getContact() != null)
            dmp.setContact(mapToMaDmp(dmpDO.getContact(), new Contact()));

        List<Contributor> contributorList = new ArrayList<>();
        dmpDO.getContributors().forEach(contributorDO -> {
            contributorList.add(mapToMaDmp(contributorDO, new Contributor()));
        });
        dmp.setContributor(contributorList);

        List<Cost> costList = new ArrayList<>();
        dmpDO.getCosts().forEach(costDO -> {
            costList.add(mapToMaDmp(costDO, new Cost()));
        });
        dmp.setCost(costList);

        dmp.setCreated(dmpDO.getCreated());

        List<Dataset> datasetList = new ArrayList<>();
        dmpDO.getDatasets().forEach(datasetDO -> {
            datasetList.add(mapToMaDmp(dmpDO, datasetDO, new Dataset(), repositoriesService));
        });
        dmp.setDataset(datasetList);

        dmp.setDescription(dmpDO.getDescription());
        dmp.setDmpId(null);
        dmp.setEthicalIssuesDescription(null);
        dmp.setEthicalIssuesExist(getEthicalIssuesExist(dmpDO));
        dmp.setEthicalIssuesReport(null);
        dmp.setLanguage(null);
        dmp.setModified(dmpDO.getModified());

        if (dmpDO.getProject() != null) {
            List<Project> projectList = new ArrayList<>();
            projectList.add(mapToMaDmp(dmpDO.getProject(), new Project()));
            dmp.setProject(projectList);
        }

        dmp.setTitle(dmpDO.getTitle());

        return dmp;
    }

    public Contact mapToMaDmp(ContributorDO contactDO, Contact contact) {

        if (contactDO.getPersonId() != null)
            contact.setContactId(mapToMaDmp(contactDO.getPersonId(), new ContactId()));
        contact.setMbox(contactDO.getMbox());
        contact.setName(contactDO.getFirstName() + " " + contactDO.getLastName());

        return contact;
    }

    public ContactId mapToMaDmp(IdentifierDO identifierDO, ContactId contactId) {

        contactId.setIdentifier(identifierDO.getIdentifier());
        switch (identifierDO.getType()) {
            case ORCID: contactId.setType(ContactId.Type.ORCID); break;
            case ISNI: contactId.setType(ContactId.Type.ISNI); break;
            case OPENID: contactId.setType(ContactId.Type.OPENID); break;
            default: contactId.setType(ContactId.Type.OTHER);
        }
        return contactId;
    }

    public Contributor mapToMaDmp(ContributorDO contributorDO, Contributor contributor) {

        if (contributorDO.getPersonId() != null)
            contributor.setContributorId(mapToMaDmp(contributorDO.getPersonId(), new ContributorId()));
        contributor.setMbox(contributorDO.getMbox());
        contributor.setName(contributorDO.getFirstName() + " " + contributorDO.getLastName());

        Set<String> role = new LinkedHashSet<String>();
        if (contributorDO.getRole() != null)
            role.add(contributorDO.getRole().getRole());
        contributor.setRole(role);

        return contributor;
    }

    public ContributorId mapToMaDmp(IdentifierDO identifierDO, ContributorId contributorId) {

        contributorId.setIdentifier(identifierDO.getIdentifier());
        switch (identifierDO.getType()) {
            case ORCID: contributorId.setType(ContributorId.Type.ORCID); break;
            case ISNI: contributorId.setType(ContributorId.Type.ISNI); break;
            case OPENID: contributorId.setType(ContributorId.Type.OPENID); break;
            default: contributorId.setType(ContributorId.Type.OTHER);
        }
        return contributorId;
    }

    public Cost mapToMaDmp(CostDO costDO, Cost cost) {

        if (costDO.getCurrencyCode() != null)
            cost.setCurrencyCode(Cost.CurrencyCode.fromValue(costDO.getCurrencyCode()));
        cost.setDescription(costDO.getDescription());
        cost.setTitle(costDO.getTitle());
        cost.setValue(cost.getValue());

        return cost;
    }

    public Dataset mapToMaDmp(DmpDO dmpDO, DatasetDO datasetDO, Dataset dataset, RepositoriesService repositoriesService) {

        dataset.setDataQualityAssurance(null);
        dataset.setDatasetId(null);
        dataset.setDescription(datasetDO.getComment());

        List<Distribution> distributionList = new ArrayList<>();
        dmpDO.getHosts().stream().filter(hostDO ->
            hostDO.getDatasets().contains(datasetDO.getReferenceHash())
        ).forEach(hostDO -> {
            Distribution distribution = mapToMaDmp(datasetDO, new Distribution());
            distributionList.add(mapToMaDmpFromHost(hostDO, distribution, repositoriesService));
        });
        dmpDO.getStorage().stream().filter(hostDO ->
                hostDO.getDatasets().contains(datasetDO.getReferenceHash())
        ).forEach(storageDO -> {
            Distribution distribution = mapToMaDmp(datasetDO, new Distribution());
            distributionList.add(mapToMaDmpFromStorage(storageDO, distribution));
        });
        dmpDO.getExternalStorage().stream().filter(hostDO ->
                hostDO.getDatasets().contains(datasetDO.getReferenceHash())
        ).forEach(storageDO -> {
            Distribution distribution = mapToMaDmp(datasetDO, new Distribution());
            distributionList.add(mapToMaDmpFromStorage(storageDO, distribution));
        });
        dataset.setDistribution(distributionList);

        dataset.setIssued(null);
        dataset.setKeyword(null);
        dataset.setLanguage(null);
        dataset.setMetadata(null);
        dataset.setPersonalData(getPersonalData(datasetDO));
        dataset.setPreservationStatement(null);
        dataset.setSecurityAndPrivacy(getSecurityAndPrivacyList(dmpDO, datasetDO));
        dataset.setSensitiveData(getSensitiveData(datasetDO));
        dataset.setTechnicalResource(null);
        dataset.setTitle(datasetDO.getTitle());
        dataset.setType(datasetDO.getType());

        return dataset;
    }

    public Distribution mapToMaDmp(DatasetDO datasetDO, Distribution distribution) {

        if (datasetDO.getDateOfDeletion() != null)
            distribution.setAvailableUntil(datasetDO.getDateOfDeletion().toString());
        if (datasetDO.getSize() != null)
            distribution.setByteSize(datasetDO.getSize().intValue());
        if (datasetDO.getDataAccess() != null)
            distribution.setDataAccess(getDataAccess(datasetDO.getDataAccess()));
        distribution.setDescription(datasetDO.getComment());
        distribution.setDownloadUrl(null);
        if (datasetDO.getType() != null)
            distribution.setFormat(List.of(datasetDO.getType()));
        if (datasetDO.getLicense() != null)
            distribution.setLicense(List.of(mapToMaDmp(datasetDO, new License())));
        distribution.setTitle(datasetDO.getTitle());
        return distribution;
    }

    public Distribution mapToMaDmpFromHost(HostDO hostDO, Distribution distribution, RepositoriesService repositoriesService) {

        Re3Data.Repository repository = repositoriesService.getById(hostDO.getHostId()).getRepository().get(0);
        distribution.setAccessUrl(repository.getRepositoryURL());
        distribution.setHost(mapToMaDmpFromRepository(repository, new Host()));
        return distribution;
    }

    public Distribution mapToMaDmpFromStorage(StorageDO storageDO, Distribution distribution) {

        distribution.setAccessUrl(storageDO.getUrl());
        distribution.setHost(mapToMaDmpFromStorage(storageDO, new Host()));
        return distribution;
    }

    public Distribution.DataAccess getDataAccess(EDataAccessType eDataAccessType) {
        switch (eDataAccessType) {
            case CLOSED: return Distribution.DataAccess.CLOSED;
            case RESTRICTED: return Distribution.DataAccess.SHARED;
            case OPEN:
            default: return Distribution.DataAccess.OPEN;
        }
    }

    public Host mapToMaDmpFromRepository(Re3Data.Repository repository, Host host) {

        host.setAvailability(null);
        host.setBackupFrequency(null);
        host.setBackupType(null);
        host.setCertifiedWith(getCertifiedWith(repository.getCertificate()));
        if (repository.getDescription() != null)
            host.setDescription(repository.getDescription().getValue());
        host.setGeoLocation(null);

        List<PidSystem> pidSystemList = new ArrayList<>();
        repository.getPidSystem().forEach(repoPidSystems -> {
            pidSystemList.add(getPidSystem(repoPidSystems));
        });
        host.setPidSystem(pidSystemList);
        repository.getType().stream().findFirst().ifPresent(repositoryTypes -> host.setStorageType(repositoryTypes.value()));
        host.setSupportVersioning(getSupportVersioning(repository.getVersioning()));
        if (repository.getRepositoryName() != null)
            host.setTitle(repository.getRepositoryName().getValue());
        if (repository.getRepositoryURL() != null)
            host.setUrl(URI.create(repository.getRepositoryURL()));
        return host;
    }

    public Host.CertifiedWith getCertifiedWith(List<Certificates> certificates) {
        //This currently returns the very first available certificate it can map to.
        for (Certificates certificate : certificates) {
            switch (certificate) {
                case DIN_31644: return Host.CertifiedWith.DIN_31644;
                case DINI_CERTIFICATE: return Host.CertifiedWith.DINI_ZERTIFIKAT;
                case DSA: return Host.CertifiedWith.DSA;
                case ISO_16363: return Host.CertifiedWith.ISO_16363;
                case ISO_16919: return Host.CertifiedWith.ISO_16919;
                case TRAC: return Host.CertifiedWith.TRAC;
                case WDS: return Host.CertifiedWith.WDS;
//                case XXX: return Host.CertifiedWith.CORETRUSTSEAL;
                case CLARIN_CERTIFICATE_B:
                case DRAMBORA:
                case RAT_SWD:
                case TRUSTED_DIGITAL_REPOSITORY:
                case OTHER:
            }
        }
        return null;
    }

    public PidSystem getPidSystem(PidSystems pidSystems) {
        switch (pidSystems) {
            case ARK: return PidSystem.ARK;
            case DOI: return PidSystem.DOI;
            case HDL: return PidSystem.HANDLE;
            case PURL: return PidSystem.PURL;
            case URN: return PidSystem.URN;
            case OTHER: return PidSystem.OTHER;
            case NONE:
            default: return null;
        }
    }

    public Host.SupportVersioning getSupportVersioning(Yesno versioning) {
        if (versioning == Yesno.YES)
            return Host.SupportVersioning.YES;
        if (versioning == Yesno.NO)
            return Host.SupportVersioning.NO;
        return Host.SupportVersioning.UNKNOWN;
    }

    public Host mapToMaDmpFromStorage(StorageDO storageDO, Host host) {

        host.setAvailability(null);
        host.setBackupFrequency(storageDO.getBackupFrequency());
        host.setBackupType(null);
        host.setCertifiedWith(null);
        host.setDescription(null);
        host.setGeoLocation(null);
        host.setPidSystem(null);
        host.setStorageType(null);
        host.setSupportVersioning(null);
        host.setTitle(null);
        if (storageDO.getUrl() != null)
            host.setUrl(URI.create(storageDO.getUrl()));
        return host;
    }

    public License mapToMaDmp(DatasetDO datasetDO, License license) {

        if (datasetDO.getLicense() != null)
            license.setLicenseRef(URI.create(datasetDO.getLicense()));
        if (datasetDO.getStartDate() != null)
            license.setStartDate(datasetDO.getStartDate().toString());
        return license;
    }

    public Dataset.PersonalData getPersonalData(DatasetDO datasetDO){

        if (Boolean.TRUE.equals(datasetDO.getPersonalData()))
            return Dataset.PersonalData.YES;
        if (Boolean.FALSE.equals(datasetDO.getPersonalData()))
            return Dataset.PersonalData.NO;
        return Dataset.PersonalData.UNKNOWN;
    }

    public List<SecurityAndPrivacy> getSecurityAndPrivacyList(DmpDO dmpDO, DatasetDO datasetDO){

        List<SecurityAndPrivacy> securityAndPrivacyList = new ArrayList<>();

        if (datasetDO.getSensitiveData()) {
            dmpDO.getSensitiveDataSecurity().forEach(eSecurityMeasure -> {
                securityAndPrivacyList.add(mapToMaDmp(eSecurityMeasure, new SecurityAndPrivacy()));
                    });
        }
        if (datasetDO.getPersonalData()) {
            dmpDO.getPersonalDataCompliance().forEach(eComplianceType -> {
                securityAndPrivacyList.add(mapToMaDmp(eComplianceType, new SecurityAndPrivacy()));
            });
        }
        if (datasetDO.getLegalRestrictions()) {
            dmpDO.getLegalRestrictionsDocuments().forEach(eAgreement -> {
                securityAndPrivacyList.add(mapToMaDmp(eAgreement, new SecurityAndPrivacy()));
            });
        }
        return securityAndPrivacyList;
    }

    public SecurityAndPrivacy mapToMaDmp(ESecurityMeasure eSecurityMeasure, SecurityAndPrivacy securityAndPrivacy) {

        securityAndPrivacy.setDescription(eSecurityMeasure.getValue());
        securityAndPrivacy.setTitle(eSecurityMeasure.name());
        return securityAndPrivacy;
    }

    public SecurityAndPrivacy mapToMaDmp(EComplianceType eComplianceType, SecurityAndPrivacy securityAndPrivacy) {

        securityAndPrivacy.setDescription(eComplianceType.getValue());
        securityAndPrivacy.setTitle(eComplianceType.name());
        return securityAndPrivacy;
    }

    public SecurityAndPrivacy mapToMaDmp(EAgreement eAgreement, SecurityAndPrivacy securityAndPrivacy) {

        securityAndPrivacy.setDescription(eAgreement.getValue());
        securityAndPrivacy.setTitle(eAgreement.name());
        return securityAndPrivacy;
    }

    public Dataset.SensitiveData getSensitiveData(DatasetDO datasetDO){

        if (Boolean.TRUE.equals(datasetDO.getSensitiveData()))
            return Dataset.SensitiveData.YES;
        if (Boolean.FALSE.equals(datasetDO.getSensitiveData()))
            return Dataset.SensitiveData.NO;
        return Dataset.SensitiveData.UNKNOWN;
    }

    public Dmp.EthicalIssuesExist getEthicalIssuesExist(DmpDO dmpDO){

        if (Boolean.TRUE.equals(dmpDO.getEthicalIssuesExist()) || Boolean.TRUE.equals(dmpDO.getHumanParticipants()))
            return Dmp.EthicalIssuesExist.YES;
        if (Boolean.FALSE.equals(dmpDO.getEthicalIssuesExist()) || Boolean.FALSE.equals(dmpDO.getHumanParticipants()))
            return Dmp.EthicalIssuesExist.NO;
        return Dmp.EthicalIssuesExist.UNKNOWN;
    }

    public Project mapToMaDmp(ProjectDO projectDO, Project project) {

        project.setDescription(project.getDescription());
        if (projectDO.getEnd() != null)
            project.setEnd(projectDO.getEnd().toString());

        if (projectDO.getFunding() != null) {
            List<Funding> fundingList = new ArrayList<>();
            fundingList.add(mapToMaDmp(projectDO.getFunding(), new Funding()));
            project.setFunding(fundingList);
        }

        if (projectDO.getStart() != null)
            project.setStart(projectDO.getStart().toString());
        project.setTitle(projectDO.getTitle());

        return project;
    }

    public Funding mapToMaDmp(FundingDO fundingDO, Funding funding) {

        if (fundingDO.getFunderId() != null)
            funding.setFunderId(mapToMaDmp(fundingDO.getFunderId(), new FunderId()));
        switch (fundingDO.getFundingStatus()) {
            case PLANNED: funding.setFundingStatus(Funding.FundingStatus.PLANNED); break;
            case APPLIED: funding.setFundingStatus(Funding.FundingStatus.APPLIED); break;
            case GRANTED: funding.setFundingStatus(Funding.FundingStatus.GRANTED); break;
            case REJECTED: funding.setFundingStatus(Funding.FundingStatus.REJECTED); break;
            case UNSPECIFIED:
            default: funding.setFundingStatus(null);
        }
        if (fundingDO.getGrantId() != null)
            funding.setGrantId(mapToMaDmp(fundingDO.getGrantId(), new GrantId()));

        return funding;
    }

    public FunderId mapToMaDmp(IdentifierDO identifierDO, FunderId funderId) {

        funderId.setIdentifier(identifierDO.getIdentifier());
        switch (identifierDO.getType()) {
            case FUNDREF: funderId.setType(FunderId.Type.FUNDREF); break;
            case URL: funderId.setType(FunderId.Type.URL); break;
            default: funderId.setType(FunderId.Type.OTHER);
        }
        return funderId;
    }

    public GrantId mapToMaDmp(IdentifierDO identifierDO, GrantId grantId) {

        grantId.setIdentifier(identifierDO.getIdentifier());
        if (identifierDO.getType() != null) {
            switch (identifierDO.getType()) {
                case URL:
                    grantId.setType(GrantId.Type.URL);
                    break;
                default:
                    grantId.setType(GrantId.Type.OTHER);
            }
        }
        return grantId;
    }
}

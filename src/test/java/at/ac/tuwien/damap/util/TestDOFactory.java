package at.ac.tuwien.damap.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.domain.DmpVersion;
import at.ac.tuwien.damap.domain.InternalStorage;
import at.ac.tuwien.damap.domain.InternalStorageTranslation;
import at.ac.tuwien.damap.enums.EAccessRight;
import at.ac.tuwien.damap.enums.EAgreement;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.damap.enums.ECostType;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.EDataKind;
import at.ac.tuwien.damap.enums.EDataQualityType;
import at.ac.tuwien.damap.enums.EDataSource;
import at.ac.tuwien.damap.enums.EDataType;
import at.ac.tuwien.damap.enums.EFundingState;
import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.enums.ELicense;
import at.ac.tuwien.damap.enums.ESecurityMeasure;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.repo.DmpVersionRepo;
import at.ac.tuwien.damap.repo.InternalStorageTranslationRepo;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.CostDO;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.ExternalStorageDO;
import at.ac.tuwien.damap.rest.dmp.domain.FundingDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.domain.RepositoryDO;
import at.ac.tuwien.damap.rest.dmp.domain.StorageDO;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpDOMapper;
import at.ac.tuwien.damap.rest.persons.orcid.models.ORCIDRecord;
import at.ac.tuwien.damap.rest.version.VersionDO;
import at.ac.tuwien.damap.rest.version.VersionDOMapper;
import at.ac.tuwien.damap.rest.version.VersionService;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
public class TestDOFactory {

    @Inject
    DmpRepo dmpRepo;

    @Inject
    InternalStorageTranslationRepo internalStorageTranslationRepo;

    @Inject
    MockDmpService dmpService;

    @Inject
    VersionService versionService;

    @Inject
    DmpVersionRepo dmpVersionRepo;

    private final String editorId = "012345";

    @Transactional
    public DmpDO createDmp(String title, boolean withDefaultData) {

        if ("TestDmp".equals(title)) {
            // TODO: fix test cases to not depend on the TestDmp. Instead, create a new DMP
            // if required.
            throw new ValidationException(title + "is reserved for the TestDmp");
        }

        DmpDO newTestDmpDO = new DmpDO();
        if (withDefaultData) {
            this.setDataOnDMP(newTestDmpDO);
        }
        newTestDmpDO.setTitle(title);
        return dmpService.create(newTestDmpDO, editorId);
    }

    private void setDataOnDMP(DmpDO dmpDO) {
        dmpDO.setTitle("TestDmp");
        dmpDO.setCreated(new Date());
        dmpDO.setModified(new Date());
        dmpDO.setDescription("This DMP is created for an automated test.");
        dmpDO.setProject(getTestProjectDO());
        dmpDO.setDataKind(EDataKind.NONE);
        dmpDO.setReusedDataKind(EDataKind.UNKNOWN);
        dmpDO.setContributors(getTestContributorList());
        dmpDO.setNoDataExplanation("This is why there are no datasets.");
        dmpDO.setMetadata("String for metadata.");
        dmpDO.setDataGeneration("Text on data generation.");
        dmpDO.setStructure("Structure of the data.");
        dmpDO.setDataQuality(getDataQualityList());
        dmpDO.setOtherDataQuality("Other data quality measures.");
        dmpDO.setTargetAudience("This is the target audience.");
        dmpDO.setTools("Tools used for gathering data.");
        dmpDO.setRestrictedDataAccess("Here is why access to the data is restricted.");
        dmpDO.setPersonalData(true);
        dmpDO.setPersonalDataCompliance(getComplianceTypeList());
        dmpDO.setOtherPersonalDataCompliance("Option for additional data compliance.");
        dmpDO.setSensitiveData(true);
        dmpDO.setSensitiveDataSecurity(getSensitiveDataSecurityList());
        dmpDO.setOtherDataSecurityMeasures("Option for additional security measures.");
        dmpDO.setSensitiveDataAccess("Text for sensitive data access.");
        dmpDO.setLegalRestrictions(true);
        dmpDO.setLegalRestrictionsDocuments(getLegalRestrictionsDocuments());
        dmpDO.setOtherLegalRestrictionsDocument("Option for additional legal restriction documents.");
        dmpDO.setLegalRestrictionsComment("Additional legal restriction comment.");
        dmpDO.setDataRightsAndAccessControl("List of people/institutions having access to the restricted data.");
        dmpDO.setHumanParticipants(true);
        dmpDO.setEthicalIssuesExist(true);
        dmpDO.setCommitteeReviewed(true);
        dmpDO.setDataKind(EDataKind.SPECIFY);
        dmpDO.setReusedDataKind(EDataKind.SPECIFY);
        dmpDO.setDatasets(getTestDatasetList());
        dmpDO.setRepositories(getTestRepositoryList());
        dmpDO.setStorage(getTestStorageList());
        dmpDO.setExternalStorage(getTestExternalStorageList());
        dmpDO.setExternalStorageInfo("Additional Info on the selected external storage.");
        dmpDO.setRestrictedAccessInfo("Additional Info on how restricted access is handled.");
        dmpDO.setClosedAccessInfo("Additional Info on how closed access for the data is handled");
        dmpDO.setCostsExist(true);
        dmpDO.setCosts(getTestCostList());
    }

    @Transactional
    public DmpDO getOrCreateTestDmpDO() {

        prepareInternalStorageOption();

        final Optional<Dmp> testDmp = dmpRepo.getAll().stream()
                .filter(a -> a.getTitle().equals("TestDmp"))
                .findAny();
        if (testDmp.isPresent())
            return DmpDOMapper.mapEntityToDO(testDmp.get(), new DmpDO());

        DmpDO newTestDmpDO = new DmpDO();

        this.setDataOnDMP(newTestDmpDO);
        dmpService.create(newTestDmpDO, editorId);
        return getOrCreateTestDmpDO();
    }

    private List<EDataQualityType> getDataQualityList() {
        return Arrays.asList(EDataQualityType.REPRESENTATION_WITH_CONTROLLED_VOCABULARIES, EDataQualityType.OTHERS);
    }

    private List<EComplianceType> getComplianceTypeList() {
        return Arrays.asList(EComplianceType.INFORMED_CONSENT, EComplianceType.ANONYMISATION);
    }

    private List<ESecurityMeasure> getSensitiveDataSecurityList() {
        return Arrays.asList(ESecurityMeasure.INDIVIDUAL_LOGIN, ESecurityMeasure.AUTOMATIC_LOCKING_OF_CLIENTS);
    }

    private List<EAgreement> getLegalRestrictionsDocuments() {
        return Arrays.asList(EAgreement.CONSORTIUM_AGREEMENT, EAgreement.CONFIDENTIALITY_AGREEMENT);
    }

    public ProjectDO getTestProjectDO() {
        ProjectDO project = new ProjectDO();
        project.setAcronym("TEST");
        project.setUniversityId("123456");
        project.setDescription("Test Project description.");
        project.setTitle("Test Project");
        project.setFunding(getTestFundingDO());
        project.setStart(new Date());
        project.setEnd(new Date());
        return project;
    }

    public ProjectDO getRecommendedTestProjectDO() {
        ProjectDO project = new ProjectDO();
        project.setAcronym("RECOMMENDED");
        project.setUniversityId("123REC");
        project.setDescription("Test Recommended Project description.");
        project.setTitle("Test Recommended Project");
        project.setFunding(getTestFundingDO());
        project.setStart(new Date());
        project.setEnd(new Date());
        return project;
    }

    private FundingDO getTestFundingDO() {
        FundingDO funding = new FundingDO();
        funding.setFundingName("Funder Institutiuon");
        funding.setFundingProgram("Funding Program");
        funding.setFunderId(getTestIdentifierDO(EIdentifierType.FUNDREF));
        funding.setGrantId(getTestIdentifierDO(EIdentifierType.OTHER));
        funding.setFundingStatus(EFundingState.GRANTED);
        return funding;
    }

    private IdentifierDO getTestIdentifierDO(EIdentifierType type) {
        IdentifierDO identifier = new IdentifierDO();
        identifier.setIdentifier("Unique Identifier 123456");
        identifier.setType(type);
        return identifier;
    }

    public ContributorDO getTestContributorDO() {
        ContributorDO contributor = new ContributorDO();
        contributor.setUniversityId("Internal Identifier 123456");
        contributor.setPersonId(getTestIdentifierDO(EIdentifierType.ORCID));
        contributor.setFirstName("Jane");
        contributor.setLastName("Doe");
        contributor.setMbox("jane.doe@research.institution.com");
        contributor.setAffiliation("Affiliated Institution");
        contributor.setAffiliationId(getTestIdentifierDO(EIdentifierType.ROR));
        contributor.setContact(true);
        return contributor;
    }

    private List<ContributorDO> getTestContributorList() {
        ContributorDO contributor = getTestContributorDO();
        contributor.setRole(EContributorRole.DATA_MANAGER);

        ContributorDO secondContributor = getTestContributorDO();
        secondContributor.setFirstName("John");
        secondContributor.setLastName("Doe");
        secondContributor.setMbox("john.doe@research.institution.com");
        secondContributor.setRole(EContributorRole.DATA_COLLECTOR);
        secondContributor.setContact(false);
        return Arrays.asList(contributor, secondContributor);
    }

    private List<DatasetDO> getTestDatasetList() {
        DatasetDO dataset = new DatasetDO();
        dataset.setTitle("Dataset Title");
        dataset.setSource(EDataSource.NEW);
        dataset.setType(List.of(EDataType.CONFIGURATION_DATA));
        dataset.setSize(50000L);
        dataset.setDescription("Dataset description.");
        dataset.setRetentionPeriod(10);
        dataset.setPersonalData(true);
        dataset.setSensitiveData(true);
        dataset.setLegalRestrictions(true);
        dataset.setLicense(ELicense.CCBY);
        dataset.setStartDate(new Date());
        dataset.setReferenceHash("referenceHash123456");
        dataset.setDataAccess(EDataAccessType.OPEN);
        dataset.setSelectedProjectMembersAccess(EAccessRight.WRITE);
        dataset.setOtherProjectMembersAccess(EAccessRight.READ);
        dataset.setPublicAccess(EAccessRight.READ);
        dataset.setDelete(true);
        dataset.setDateOfDeletion(new Date());
        dataset.setReasonForDeletion("Explanation on why data is being deleted.");
        dataset.setSource(EDataSource.REUSED);
        IdentifierDO identifierDO = new IdentifierDO();
        identifierDO.setIdentifier("Unique Identifier 123456");
        identifierDO.setType(EIdentifierType.DOI);
        dataset.setDatasetId(identifierDO);
        List<ContributorDO> contributors = getTestContributorList();
        if (!contributors.isEmpty())
            dataset.setDeletionPerson(contributors.get(0));

        DatasetDO dataset2 = new DatasetDO();
        dataset2.setTitle("Dataset2 Title");
        dataset2.setSource(EDataSource.NEW);
        dataset2.setReferenceHash("referenceHash234567");

        return List.of(dataset, dataset2);
    }

    private List<RepositoryDO> getTestRepositoryList() {
        RepositoryDO repository = new RepositoryDO();
        repository.setRepositoryId("r3d100013557");
        repository.setTitle("TU Data");
        repository.setDatasets(List.of("referenceHash123456", "referenceHash234567"));
        return List.of(repository);
    }

    private List<StorageDO> getTestStorageList() {
        StorageDO storage = new StorageDO();
        Optional<InternalStorageTranslation> testInternalStorage = internalStorageTranslationRepo
                .getAllInternalStorageByLanguage("eng").stream()
                .filter(a -> a.getTitle().equals("Test Storage Title"))
                .findAny();
        testInternalStorage.ifPresent(
                storageTranslation -> storage.setInternalStorageId(storageTranslation.getInternalStorageId().id));
        storage.setTitle("Internal Host");
        storage.setDatasets(List.of("referenceHash123456"));

        return List.of(storage);
    }

    private List<ExternalStorageDO> getTestExternalStorageList() {
        ExternalStorageDO storage = new ExternalStorageDO();
        storage.setTitle("External Host");
        storage.setDatasets(List.of("referenceHash123456"));
        storage.setUrl("external.storage.url");
        storage.setBackupFrequency("Frequency of data backups.");
        storage.setStorageLocation("Location of Storages");
        storage.setBackupLocation("Location of Backups");
        return List.of(storage);
    }

    private List<CostDO> getTestCostList() {
        CostDO cost = new CostDO();
        cost.setTitle("Cost Item Title");
        cost.setValue(50000f);
        cost.setCurrencyCode("EUR");
        cost.setDescription("Descriptiopn of required expense");
        cost.setType(ECostType.DATABASE);
        cost.setTitle("Custom Typology");
        return List.of(cost);
    }

    public DmpDO getOrCreateTestDmpDOEmpty() {
        final Optional<Dmp> testDmp = dmpRepo.getAll().stream()
                .filter(a -> a.getTitle().equals("EmptyTestDmp"))
                .findAny();
        if (testDmp.isPresent())
            return DmpDOMapper.mapEntityToDO(testDmp.get(), new DmpDO());

        DmpDO newTestDmpDO = new DmpDO();
        newTestDmpDO.setTitle("EmptyTestDmp");

        dmpService.create(newTestDmpDO, editorId);
        return getOrCreateTestDmpDOEmpty();
    }

    @Transactional
    public void prepareInternalStorageOption() {

        if (internalStorageTranslationRepo.listAll().size() > 0)
            return;

        InternalStorage internalStorage = new InternalStorage();
        internalStorage.setUrl("test.url.com");
        internalStorage.setStorageLocation("AUT");
        internalStorage.setBackupLocation("AUT");
        internalStorage.persist();

        InternalStorageTranslation internalStorageTranslation = new InternalStorageTranslation();
        internalStorageTranslation.setInternalStorageId(internalStorage);
        internalStorageTranslation.setTitle("Test Storage Title");
        internalStorageTranslation.setDescription("Long winded yet brief description of the storage option");
        internalStorageTranslation.setLanguageCode("eng");
        internalStorageTranslation.persistAndFlush();
    }

    @Transactional
    public DmpDO getOrCreateTestDmpDOInvalidData() {
        DmpDO newInvalidTestDmpDO = getOrCreateTestDmpDO();
        newInvalidTestDmpDO.setTitle("MalformedTestDmp");

        newInvalidTestDmpDO.getDatasets().get(0).setLicense(ELicense.CCBY);
        newInvalidTestDmpDO.getCosts().get(0).setCurrencyCode("DOUBLOONS");
        newInvalidTestDmpDO.getRepositories().get(0).setRepositoryId("Arbitrary Host ID");
        newInvalidTestDmpDO.getExternalStorage().get(0).setUrl("Link to the storage service");

        return newInvalidTestDmpDO;
    }

    public VersionDO getOrCreateTestVersionDO() {
        DmpDO dmpDO = getOrCreateTestDmpDO();

        final Optional<DmpVersion> testDmpVersion = dmpVersionRepo.getAll().stream()
                .filter(a -> a.getVersionName().equals("TestVersion"))
                .findAny();
        if (testDmpVersion.isPresent()) {
            return VersionDOMapper.mapEntityToDO(testDmpVersion.get(), new VersionDO());
        }

        VersionDO versionDO = new VersionDO();
        versionDO.setVersionName("TestVersion");
        versionDO.setDmpId(dmpDO.getId());

        versionService.createOrUpdate(versionDO);

        return getOrCreateTestVersionDO();
    }

    public ORCIDRecord getORCIDTestRecord() {
        ORCIDRecord record = new ORCIDRecord();
        URL url = Resources.getResource("json/orcidRecord.json");
        try (InputStream in = url.openStream()) {
            ObjectMapper mapper = new ObjectMapper();
            record = mapper.readValue(in, ORCIDRecord.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return record;
    }
}

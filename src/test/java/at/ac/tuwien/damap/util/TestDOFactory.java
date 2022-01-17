package at.ac.tuwien.damap.util;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.enums.EDataKind;
import at.ac.tuwien.damap.enums.EFundingState;
import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.*;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpDOMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class TestDOFactory {

    @Inject
    DmpRepo dmpRepo;

    @Transactional
    public DmpDO getOrCreateTestDmpDO() {
        final Optional<Dmp> testDmp = dmpRepo.getAll().stream()
                .filter(a -> a.getTitle().equals("TestDmp"))
                .findAny();
        if (testDmp.isPresent())
            return DmpDOMapper.mapEntityToDO(testDmp.get(), new DmpDO());

        DmpDO newTestDmpDO = new DmpDO();
        newTestDmpDO.setTitle("TestDmp");
        newTestDmpDO.setCreated(new Date());
        newTestDmpDO.setModified(new Date());
        newTestDmpDO.setDescription("This DMP is created for an automated test.");
        newTestDmpDO.setProject(getTestProjectDO());
        newTestDmpDO.setContact(getTestPersonDO());
        newTestDmpDO.setDataKind(EDataKind.NONE);
        newTestDmpDO.setContributors(getTestContributorList());
        newTestDmpDO.setNoDataExplanation("This is why there are no datasets.");
        newTestDmpDO.setMetadata("String for metadata.");
        newTestDmpDO.setDataGeneration("Text on data generation.");
        newTestDmpDO.setStructure("Structure of the data.");
        newTestDmpDO.setTargetAudience("This is the target audience.");
        newTestDmpDO.setTools("Tools used for gathering data.");
        newTestDmpDO.setRestrictedDataAccess("Here is why access to the data is restricted.");
        newTestDmpDO.setPersonalData(true);
        newTestDmpDO.setPersonalDataCompliance(getComplianceTypeList());
        newTestDmpDO.setOtherPersonalDataCompliance("Option for additional data compliance.");
        newTestDmpDO.setSensitiveData(true);
        newTestDmpDO.setSensitiveDataSecurity(getSensitiveDataSecurityList());
        newTestDmpDO.setOtherDataSecurityMeasures("Option for additional security measures.");
        newTestDmpDO.setSensitiveDataAccess("Text for sensitive data access.");
        newTestDmpDO.setLegalRestrictions(true);
        newTestDmpDO.setLegalRestrictionsDocuments(getLegalRestrictionsDocuments());
        newTestDmpDO.setOtherLegalRestrictionsDocument("Option for additional legal restriction documents.");
        newTestDmpDO.setLegalRestrictionsComment("Additional legal restriction comment.");
        newTestDmpDO.setDataRightsAndAccessControl("List of people/institutions having access to the restricted data.");
        newTestDmpDO.setHumanParticipants(true);
        newTestDmpDO.setEthicalIssuesExist(true);
        newTestDmpDO.setCommitteeReviewed(true);
        newTestDmpDO.setDatasets(getTestDatasetList());
        newTestDmpDO.setHosts(getTestHostList());
        newTestDmpDO.setStorage(getTestStorageList());
        newTestDmpDO.setExternalStorage(getTestExternalStorageList());
        newTestDmpDO.setExternalStorageInfo("Additional Info on the selected external storage.");
        newTestDmpDO.setRestrictedAccessInfo("Additional Info on how restricted access is handled.");
        newTestDmpDO.setClosedAccessInfo("Additional Info on how closed access for the data is handled");
        newTestDmpDO.setCostsExist(true);
        newTestDmpDO.setCosts(getTestCostList());

        Dmp dmp = DmpDOMapper.mapDOtoEntity(newTestDmpDO, new Dmp());
        dmp.setCreated(new Date());
        dmp.setModified(new Date());
        dmp.persistAndFlush();
        return getOrCreateTestDmpDO();
    }


    private List<String> getComplianceTypeList() {
        return Arrays.asList("by gaining informed consent for processing personal data",
                "by anonymisation of personal data for preservation and/or sharing (truly anonymous data are no longer considered personal data)");
    }
    private List<String> getSensitiveDataSecurityList() {
        return Arrays.asList("individual log-in and password",
                "automatic locking of clients (timeout)");
    }
    private List<String> getLegalRestrictionsDocuments() {
        return Arrays.asList("consortium agreement",
                "data processing agreement");
    }

    private ProjectDO getTestProjectDO() {
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

    private FundingDO getTestFundingDO() {
        FundingDO funding = new FundingDO();
        funding.setFundingName("Funder Institutiuon");
        funding.setFundingProgram("Funding Program");
        funding.setFunderId(getTestIdentifierDO(EIdentifierType.FUNDREF));
        funding.setGrantId(getTestIdentifierDO(EIdentifierType.OTHER));
        funding.setFundingStatus(EFundingState.GRANTED);
        return funding;
    }

    private IdentifierDO getTestIdentifierDO(EIdentifierType type){
        IdentifierDO identifier = new IdentifierDO();
        identifier.setIdentifier("Unique Identifier 123456");
        identifier.setType(type);
        return identifier;
    }

    private PersonDO getTestPersonDO(){
        PersonDO person = new PersonDO();
        person.setUniversityId("Internal Identifier 123456");
        person.setPersonId(getTestIdentifierDO(EIdentifierType.ORCID));
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setMbox("jane.doe@research.institution.com");
        person.setAffiliation("Affiliated Institution");
        person.setAffiliationId(getTestIdentifierDO(EIdentifierType.ROR));
        return person;
    }

    private List<ContributorDO> getTestContributorList(){
        ContributorDO contributor = new ContributorDO();
        contributor.setPerson(getTestPersonDO());
        contributor.setRole("Important Person");

        ContributorDO secondContributor = new ContributorDO();
        secondContributor.setPerson(getTestPersonDO());
        secondContributor.getPerson().setFirstName("John");
        secondContributor.getPerson().setLastName("Doe");
        secondContributor.getPerson().setMbox("john.doe@research.institution.com");
        secondContributor.setRole("Important Person");
        return Arrays.asList(contributor, secondContributor);
    }

    private List<DatasetDO> getTestDatasetList(){
        DatasetDO dataset = new DatasetDO();
        dataset.setTitle("Dataset Title");
        dataset.setType("Dataset Type");
        dataset.setSize(50000L);
        dataset.setComment("Comments related to the dataset.");
        dataset.setPersonalData(true);
        dataset.setSensitiveData(true);
        dataset.setLegalRestrictions(true);
        dataset.setLicense("Dataset License Link");
        dataset.setStartDate(new Date());
        dataset.setReferenceHash("referenceHash123456");
        dataset.setDataAccess("Here the data can be accessed.");
        dataset.setSelectedProjectMembersAccess("Here some project members can access.");
        dataset.setOtherProjectMembersAccess("Here all project members can access the data");
        dataset.setPublicAccess("Here the public can access the data");
        dataset.setDelete(true);
        dataset.setDateOfDeletion(new Date());
        dataset.setReasonForDeletion("Explanation on why data is being deleted.");
        return List.of(dataset);
    }

    private List<HostDO> getTestHostList(){
        HostDO host = new HostDO();
        host.setHostId("r3d100013557");
        host.setTitle("TU Data");
        host.setDate(new Date());
        host.setDatasets(List.of("referenceHash123456"));
        return List.of(host);
    }

    private List<StorageDO> getTestStorageList(){
        StorageDO storage = new StorageDO();
        storage.setHostId("123456");
        storage.setTitle("Internal Host");
        storage.setDate(new Date());
        storage.setDatasets(List.of("referenceHash123456"));
        storage.setUrl("Link to the storage service");
        storage.setBackupFrequency("Frequency of data backups.");
        storage.setStorageLocation("Location of Storages");
        storage.setBackupLocation("Location of Backups");
        return List.of(storage);
    }

    private List<StorageDO> getTestExternalStorageList(){
        StorageDO storage = new StorageDO();
        storage.setHostId(null);
        storage.setTitle("External Host");
        storage.setDate(new Date());
        storage.setDatasets(List.of("referenceHash123456"));
        storage.setUrl("Link to the storage service");
        storage.setBackupFrequency("Frequency of data backups.");
        storage.setStorageLocation("Location of Storages");
        storage.setBackupLocation("Location of Backups");
        return List.of(storage);
    }

    private List<CostDO> getTestCostList(){
        CostDO cost = new CostDO();
        cost.setTitle("Cost Item Title");
        cost.setValue(50000f);
        cost.setCurrencyCode("EUR");
        cost.setDescription("Descriptiopn of required expense");
        cost.setType("Typology of expense");
        cost.setTitle("Custom Typology");
        return List.of(cost);
    }


    @Transactional
    public DmpDO getOrCreateTestDmpDOEmpty() {
        final Optional<Dmp> testDmp = dmpRepo.getAll().stream()
                .filter(a -> a.getTitle().equals("EmptyTestDmp"))
                .findAny();
        if (testDmp.isPresent())
            return DmpDOMapper.mapEntityToDO(testDmp.get(), new DmpDO());

        DmpDO newTestDmpDO = new DmpDO();
        newTestDmpDO.setTitle("EmptyTestDmp");

        dmpRepo.persistAndFlush(DmpDOMapper.mapDOtoEntity(newTestDmpDO, new Dmp()));
        return getOrCreateTestDmpDOEmpty();
    }
}

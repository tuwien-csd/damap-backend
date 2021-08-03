package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.rest.domain.*;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class DmpDOMapper {

    public DmpDO mapEntityToDO(Dmp dmp, DmpDO dmpDO) {
        dmpDO.setId(dmp.id);
        dmpDO.setTitle(dmp.getTitle());
        dmpDO.setCreated(dmp.getCreated());
        dmpDO.setModified(dmp.getModified());
        dmpDO.setDescription(dmp.getDescription());

        if (dmp.getProject() != null) {
            ProjectDO projectDO = new ProjectDO();
            ProjectDOMapper.mapEntityToDO(dmp.getProject(), projectDO);
            dmpDO.setProject(projectDO);
        }

        if (dmp.getContact() != null) {
            PersonDO contactDO = new PersonDO();
            PersonDOMapper.mapEntityToDO(dmp.getContact(), contactDO);
            dmpDO.setContact(contactDO);
        }

        dmpDO.setDataKind(dmp.getDataKind());
        dmpDO.setNoDataExplanation(dmp.getNoDataExplanation());
        dmpDO.setMetadata(dmp.getMetadata());
        dmpDO.setDataGeneration(dmp.getDataGeneration());
        dmpDO.setStructure(dmp.getStructure());
        dmpDO.setTargetAudience(dmp.getTargetAudience());
        dmpDO.setTools(dmp.getTools());
        dmpDO.setRestrictedDataAccess(dmp.getRestrictedDataAccess());
        dmpDO.setPersonalData(dmp.getPersonalData());
        dmpDO.setPersonalDataAccess(dmp.getPersonalDataAccess());
        dmpDO.setOtherPersonalDataCompliance(dmp.getOtherPersonalDataCompliance());
        dmpDO.setSensitiveData(dmp.getSensitiveData());
        dmpDO.setSensitiveDataSecurity(dmp.getSensitiveDataSecurity());
        dmpDO.setLegalRestrictions(dmp.getLegalRestrictions());
        dmpDO.setLegalRestrictionsComment(dmp.getLegalRestrictionsComment());
        dmpDO.setEthicalIssuesExist(dmp.getEthicalIssuesExist());
        dmpDO.setCommitteeApproved(dmp.getCommitteeApproved());
        dmpDO.setEthicsReport(dmp.getEthicsReport());
        dmpDO.setEthicalComplianceStatement(dmp.getEthicalComplianceStatement());
        dmpDO.setExternalStorageInfo(dmp.getExternalStorageInfo());
        dmpDO.setRestrictedAccessInfo(dmp.getRestrictedAccessInfo());
        dmpDO.setClosedAccessInfo(dmp.getClosedAccessInfo());
        dmpDO.setCostsExist(dmp.getCostsExist());

        List<ContributorDO> contributorDOList = new ArrayList<>();
        dmp.getContributorList().forEach(contributor -> {
            ContributorDO contributorDO = new ContributorDO();
            ContributorDOMapper.mapEntityToDO(contributor, contributorDO);
            contributorDOList.add(contributorDO);
        });
        dmpDO.setContributors(contributorDOList);

        List<String> personalDataComplianceDOList = new ArrayList<>();
        dmp.getPersonalDataCompliance().forEach(option -> {
            personalDataComplianceDOList.add(option.getValue());
        });
        dmpDO.setPersonalDataCompliance(personalDataComplianceDOList);

        List<DatasetDO> datasetDOList = new ArrayList<>();
        dmp.getDatasetList().forEach(dataset -> {
            DatasetDO datasetDO = new DatasetDO();
            DatasetDOMapper.mapEntityToDO(dataset, datasetDO);
            datasetDOList.add(datasetDO);
        });
        dmpDO.setDatasets(datasetDOList);

        List<HostDO> repositoryDOList = new ArrayList<>();
        List<StorageDO> storageDOList = new ArrayList<>();
        List<StorageDO> externalStorageDOList = new ArrayList<>();
        dmp.getHostList().forEach(host -> {
            HostDO hostDO = null;

            if (Repository.class.isAssignableFrom(host.getClass())) {
                hostDO = new HostDO();
                HostDOMapper.mapEntityToDO(host, hostDO);
                repositoryDOList.add(hostDO);
            } else if (Storage.class.isAssignableFrom(host.getClass())) {
                hostDO = new StorageDO();
                HostDOMapper.mapEntityToDO(host, hostDO);
                StorageDOMapper.mapEntityToDO((Storage) host, (StorageDO) hostDO);
                storageDOList.add((StorageDO) hostDO);
            } else if (ExternalStorage.class.isAssignableFrom(host.getClass())) {
                hostDO = new StorageDO();
                HostDOMapper.mapEntityToDO(host, hostDO);
                ExternalStorageDOMapper.mapEntityToDO((ExternalStorage) host, (StorageDO) hostDO);
                externalStorageDOList.add((StorageDO) hostDO);
            }

            //add frontend referenceHash list to host
            List<String> referenceHashList = new ArrayList<>();
            host.getDistributionList().forEach(distribution -> {
                referenceHashList.add(distribution.getDataset().getReferenceHash());
            });
            if (!referenceHashList.isEmpty())
                hostDO.setDatasets(referenceHashList);

        });
        dmpDO.setHosts(repositoryDOList);
        dmpDO.setStorage(storageDOList);
        dmpDO.setExternalStorage(externalStorageDOList);


        List<CostDO> costDOList = new ArrayList<>();
        dmp.getCosts().forEach(cost -> {
            CostDO costDO = new CostDO();
            CostDOMapper.mapEntityToDO(cost, costDO);
            costDOList.add(costDO);
        });
        dmpDO.setCosts(costDOList);

        return dmpDO;
    }

    public Dmp mapDOtoEntity(DmpDO dmpDO, Dmp dmp) {
        if (dmpDO.getId() != null)
            dmp.id = dmpDO.getId();
        dmp.setTitle(dmpDO.getTitle());
        dmp.setDescription(dmpDO.getDescription());

        if (dmpDO.getProject() != null) {
            Project project = new Project();
            if (dmp.getProject() != null)
                project = dmp.getProject();
            ProjectDOMapper.mapDOtoEntity(dmpDO.getProject(), project);
            dmp.setProject(project);
        } else
            dmp.setProject(null);

        if (dmpDO.getContact() != null) {
            Person contact = new Person();
            if (dmp.getContact() != null)
                contact = dmp.getContact();
            PersonDOMapper.mapDOtoEntity(dmpDO.getContact(), contact);
            dmp.setContact(contact);
        } else
            dmp.setContact(null);

        dmp.setDataKind(dmpDO.getDataKind());
        dmp.setNoDataExplanation(dmpDO.getNoDataExplanation());
        dmp.setMetadata(dmpDO.getMetadata());
        dmp.setDataGeneration(dmpDO.getDataGeneration());
        dmp.setStructure(dmpDO.getStructure());
        dmp.setTargetAudience(dmpDO.getTargetAudience());
        dmp.setTools(dmpDO.getTools());
        dmp.setRestrictedDataAccess(dmpDO.getRestrictedDataAccess());
        dmp.setPersonalData(dmpDO.getPersonalData());
        dmp.setPersonalDataAccess(dmpDO.getPersonalDataAccess());
        dmp.setOtherPersonalDataCompliance(dmpDO.getOtherPersonalDataCompliance());
        dmp.setSensitiveDataSecurity(dmpDO.getSensitiveDataSecurity());
        dmp.setSensitiveData(dmpDO.getSensitiveData());
        dmp.setLegalRestrictions(dmpDO.getLegalRestrictions());
        dmp.setLegalRestrictionsComment(dmpDO.getLegalRestrictionsComment());
        dmp.setEthicalIssuesExist(dmpDO.getEthicalIssuesExist());
        dmp.setCommitteeApproved(dmpDO.getCommitteeApproved());
        dmp.setEthicsReport(dmpDO.getEthicsReport());
        dmp.setEthicalComplianceStatement(dmpDO.getEthicalComplianceStatement());
        dmp.setExternalStorageInfo(dmpDO.getExternalStorageInfo());
        dmp.setRestrictedAccessInfo(dmpDO.getRestrictedAccessInfo());
        dmp.setClosedAccessInfo(dmpDO.getClosedAccessInfo());
        dmp.setCostsExist(dmpDO.getCostsExist());

        //TODO also check for existing contributors based on Identifier, not just universityId

        //remove all existing Contributor objects, that are not included in the DO anymore
        List<Contributor> contributorList = dmp.getContributorList();
        List<Contributor> contributorListToRemove = new ArrayList<>();
        contributorList.forEach(contributor -> {
            Optional<ContributorDO> contributorDOOptional = dmpDO.getContributors().stream().filter(contributorDO ->
                    contributorDO.getId() != null && contributorDO.getId().equals(contributor.id)).findFirst();
            if (contributorDOOptional.isEmpty()) {
                contributorListToRemove.add(contributor);
            }
        });
        contributorList.removeAll(contributorListToRemove);

        List<EComplianceType> personalDataComplianceList = new ArrayList<>();
        dmpDO.getPersonalDataCompliance().forEach(option -> {
            if (option != null) {
                personalDataComplianceList.add(EComplianceType.getByValue(option));
            }
        });
        dmp.setPersonalDataCompliance(personalDataComplianceList);

        //update existing Contributor objects and create new ones
        dmpDO.getContributors().forEach(contributorDO -> {
            Optional<Contributor> contributorOptional = contributorList.stream().filter(contributor ->
                    contributorDO.getId() != null && contributorDO.getId().equals(contributor.id)).findFirst();
            if (contributorOptional.isPresent()) {
                Contributor contributor = contributorOptional.get();
                ContributorDOMapper.mapDOtoEntity(contributorDO, contributor);
            } else {
                Contributor contributor = new Contributor();
                ContributorDOMapper.mapDOtoEntity(contributorDO, contributor);
                contributor.setDmp(dmp);
                contributorList.add(contributor);
            }
        });


        //remove all existing Dataset objects, that are not included in the DO anymore
        List<Dataset> datasetList = dmp.getDatasetList();
        List<Dataset> datasetListToRemove = new ArrayList<>();
        datasetList.forEach(dataset -> {
            Optional<DatasetDO> datasetDOOptional = dmpDO.getDatasets().stream().filter(datasetDO ->
                    datasetDO.getId() != null && datasetDO.getId().equals(dataset.id)).findFirst();
            if (datasetDOOptional.isEmpty()) {
                datasetListToRemove.add(dataset);
            }
        });
        datasetList.removeAll(datasetListToRemove);

        //update existing Dataset objects and create new ones
        dmpDO.getDatasets().forEach(datasetDO -> {
            Optional<Dataset> datasetOptional = datasetList.stream().filter(dataset ->
                    datasetDO.getId() != null && datasetDO.getId().equals(dataset.id)).findFirst();
            if (datasetOptional.isPresent()) {
                Dataset dataset = datasetOptional.get();
                DatasetDOMapper.mapDOtoEntity(datasetDO, dataset);
            } else {
                Dataset dataset = new Dataset();
                DatasetDOMapper.mapDOtoEntity(datasetDO, dataset);
                dataset.setDmp(dmp);
                datasetList.add(dataset);
            }
        });


        //remove all existing Host objects, that are not included in the DO anymore
        List<Host> hostList = dmp.getHostList();
        List<Host> hostListToRemove = new ArrayList<>();
        hostList.forEach(host -> {
            Optional<HostDO> hostDOOptional = dmpDO.getHosts().stream().filter(hostDO ->
                    hostDO.getId() != null &&
                            hostDO.getId().equals(host.id)).findFirst();
            if (Repository.class.isAssignableFrom(host.getClass())) {
                if (hostDOOptional.isEmpty()) {
                    hostListToRemove.add(host);
                }
            }
        });
        hostList.forEach(host -> {
            Optional<StorageDO> hostDOOptional = dmpDO.getStorage().stream().filter(hostDO ->
                    hostDO.getId() != null &&
                            hostDO.getId().equals(host.id)).findFirst();
            if (Storage.class.isAssignableFrom(host.getClass())) {
                if (hostDOOptional.isEmpty()) {
                    hostListToRemove.add(host);
                }
            }
        });
        hostList.forEach(host -> {
            Optional<StorageDO> hostDOOptional = dmpDO.getExternalStorage().stream().filter(hostDO ->
                    hostDO.getId() != null &&
                            hostDO.getId().equals(host.id)).findFirst();
            if (ExternalStorage.class.isAssignableFrom(host.getClass())) {
                if (hostDOOptional.isEmpty()) {
                    hostListToRemove.add(host);
                }
            }
        });
        hostList.removeAll(hostListToRemove);

        //update existing Host objects and create new ones
        dmpDO.getHosts().forEach(hostDO -> {
            Optional<Host> hostOptional = hostList.stream().filter(host ->
                    hostDO.getId() != null &&
                            hostDO.getId().equals(host.id)).findFirst();
            Repository host;
            if (hostOptional.isPresent()) {
                host = (Repository) hostOptional.get();
                HostDOMapper.mapDOtoEntity(hostDO, host);
            } else {
                host = new Repository();
                HostDOMapper.mapDOtoEntity(hostDO, host);
                host.setDmp(dmp);
                hostList.add(host);
            }
            determineDistributions(dmp, hostDO, host);
        });

        //create Storage objects
        dmpDO.getStorage().forEach(hostDO -> {
            Optional<Host> hostOptional = hostList.stream().filter(host ->
                    hostDO.getId() != null &&
                            hostDO.getId().equals(host.id)).findFirst();
            Storage host;
            if (hostOptional.isPresent()) {
                host = (Storage) hostOptional.get();
                HostDOMapper.mapDOtoEntity(hostDO, host);
                StorageDOMapper.mapDOtoEntity(hostDO, host);
            } else {
                host = new Storage();
                HostDOMapper.mapDOtoEntity(hostDO, host);
                StorageDOMapper.mapDOtoEntity(hostDO, host);
                host.setDmp(dmp);
                hostList.add(host);
            }
            determineDistributions(dmp, hostDO, host);
        });

        //create ExternalStorage
        dmpDO.getExternalStorage().forEach(hostDO -> {
            Optional<Host> hostOptional = hostList.stream().filter(host ->
                    hostDO.getId() != null &&
                            hostDO.getId().equals(host.id)).findFirst();
            ExternalStorage host;
            if (hostOptional.isPresent()) {
                host = (ExternalStorage) hostOptional.get();
                HostDOMapper.mapDOtoEntity(hostDO, host);
                ExternalStorageDOMapper.mapDOtoEntity(hostDO, host);
            } else {
                host = new ExternalStorage();
                HostDOMapper.mapDOtoEntity(hostDO, host);
                ExternalStorageDOMapper.mapDOtoEntity(hostDO, host);
                host.setDmp(dmp);
                host.setHostId(String.valueOf(System.currentTimeMillis()) + Math.random());
                hostList.add(host);
            }
            determineDistributions(dmp, hostDO, host);
        });


        //remove all existing Cost objects, that are not included in the DO anymore
        List<Cost> costList = dmp.getCosts();
        List<Cost> costListToRemove = new ArrayList<>();
        costList.forEach(cost -> {
            Optional<CostDO> costDOOptional = dmpDO.getCosts().stream().filter(costDO ->
                    costDO.getId() != null && costDO.getId().equals(cost.id)).findFirst();
            if (costDOOptional.isEmpty()) {
                costListToRemove.add(cost);
            }
        });
        costList.removeAll(costListToRemove);

        //update existing Costs objects and create new ones
        dmpDO.getCosts().forEach(costDO -> {
            Optional<Cost> costOptional = costList.stream().filter(cost ->
                    costDO.getId() != null && costDO.getId().equals(cost.id)).findFirst();
            if (costOptional.isPresent()) {
                Cost cost = costOptional.get();
                CostDOMapper.mapDOtoEntity(costDO, cost);
            } else {
                Cost cost = new Cost();
                CostDOMapper.mapDOtoEntity(costDO, cost);
                cost.setDmp(dmp);
                costList.add(cost);
            }
        });

        return dmp;
    }

    private void determineDistributions(Dmp dmp, HostDO hostDO, Host host) {
        //convert datasetHash to id references from dataset to hosts
        if (hostDO.getDatasets() != null) {
            List<Distribution> distributionList = host.getDistributionList();
            List<Distribution> distributionUpdatedList = new ArrayList<>();

            dmp.getDatasetList().forEach(dataset -> {
                if (hostDO.getDatasets().contains(dataset.getReferenceHash())) {
                    Distribution distribution = new Distribution();
                    distribution.setHost(host);
                    distribution.setDataset(dataset);

                    if (!distributionList.contains(distribution))
                        distributionList.add(distribution);
                    distributionUpdatedList.add(distribution);
                }
            });
            List<Distribution> distributionRemoveList = new ArrayList<>();
            distributionList.forEach(distribution -> {
                if (!distributionUpdatedList.contains(distribution))
                    distributionRemoveList.add(distribution);
            });
            distributionList.removeAll(distributionRemoveList);
        }
    }
}

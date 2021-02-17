package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.rest.domain.*;

import java.util.ArrayList;
import java.util.List;

public class DmpDOMapper {

    public static void mapAtoB(Dmp dmp, DmpDO dmpDO) {
        dmpDO.setId(dmp.id);
        dmpDO.setVersion(dmp.getVersion());
        dmpDO.setTitle(dmp.getTitle());
        dmpDO.setCreated(dmp.getCreated());
        dmpDO.setModified(dmp.getModified());
        dmpDO.setDescription(dmp.getDescription());

        ProjectDO projectDO = new ProjectDO();
        ProjectDOMapper.mapAtoB(dmp.getProject(), projectDO);
        dmpDO.setProject(projectDO);

        PersonDO contactDO = new PersonDO();
        PersonDOMapper.mapAtoB(dmp.getContact(), contactDO);
        dmpDO.setContact(contactDO);

        dmpDO.setDataKind(dmp.getDataKind());
        dmpDO.setNoDataExplanation(dmp.getNoDataExpalnation());
        dmpDO.setMetadata(dmp.getMetadata());
        dmpDO.setDataGeneration(dmp.getDataGeneration());
        dmpDO.setStructure(dmp.getStructure());
        dmpDO.setTargetAudience(dmp.getTargetAudience());
        dmpDO.setPersonalInformation(dmp.isPersonalInformation());
        dmpDO.setSensitiveData(dmp.isSensitiveData());
        dmpDO.setLegalRestrictions(dmp.isLegalRestrictions());
        dmpDO.setEthicalIssuesExist(dmp.isEthicalIssuesExist());
        dmpDO.setCommitteeApproved(dmp.isCommitteeApproved());
        dmpDO.setEthicsReport(dmp.getEthicsReport());
        dmpDO.setOptionalStatement(dmp.getOptionalStatement());

        List<ContributorDO> contributorDOList = new ArrayList<>();
        dmp.getContributorList().forEach(contributor -> {
            ContributorDO contributorDO = new ContributorDO();
            ContributorDOMapper.mapAtoB(contributor, contributorDO);
            contributorDOList.add(contributorDO);
        });
        dmpDO.setContributors(contributorDOList);

        List<DatasetDO> datasetDOList = new ArrayList<>();
        dmp.getDatasetList().forEach(dataset -> {
            DatasetDO datasetDO = new DatasetDO();
            DatasetDOMapper.mapAtoB(dataset, datasetDO);
            datasetDOList.add(datasetDO);
        });
        dmpDO.setDatasets(datasetDOList);

        List<HostDO> hostDOList = new ArrayList<>();
        dmp.getHostList().forEach(host -> {
            HostDO hostDO = new HostDO();
            HostDOMapper.mapAtoB(host, hostDO);
            hostDOList.add(hostDO);
        });
        dmpDO.setHosts(hostDOList);
    }
}

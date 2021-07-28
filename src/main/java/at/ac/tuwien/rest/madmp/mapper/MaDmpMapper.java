package at.ac.tuwien.rest.madmp.mapper;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.rest.madmp.dto.*;

import java.util.ArrayList;
import java.util.List;

public class MaDmpMapper {

    public static void mapEntityToMaDmp(Dmp dmp, MaDmp maDmp) {

        maDmp.setTitle(dmp.getTitle());
        maDmp.setDescription(dmp.getDescription());
        maDmp.setCreated(dmp.getCreated());
        maDmp.setModified(dmp.getModified());
        maDmp.setEthical_issues_exist(dmp.getEthicalIssuesExist());
        maDmp.setEthical_issues_report(dmp.getEthicsReport());

        if (dmp.getContact() != null) {
            MaDmpContact maDmpContact = new MaDmpContact();
            mapEntityToMaDmp(dmp.getContact(), maDmpContact);
            maDmp.setContact(maDmpContact);
        }

        if (dmp.getProject() != null) {
            MaDmpProject maDmpProject = new MaDmpProject();
            mapEntityToMaDmp(dmp.getProject(), maDmpProject);
            maDmp.setProject(maDmpProject);
        }

        List<MaDmpContributor> maDmpContributorList = new ArrayList<>();
        dmp.getContributorList().forEach(contributor -> {
            MaDmpContributor maDmpContributor = new MaDmpContributor();
            mapEntityToMaDmp(contributor, maDmpContributor);
            maDmpContributorList.add(maDmpContributor);
        });
        maDmp.setContributor(maDmpContributorList);

        List<MaDmpDataset> maDmpDatasetList = new ArrayList<>();
        dmp.getDatasetList().forEach(dataset -> {
            MaDmpDataset maDmpDataset = new MaDmpDataset();
            mapEntityToMaDmp(dataset, maDmpDataset);
            maDmpDatasetList.add(maDmpDataset);
        });
        maDmp.setDataset(maDmpDatasetList);
    }

    public static void mapEntityToMaDmp(Person person, MaDmpContact maDmpContact) {
        maDmpContact.setName(person.getFirstName() + " " + person.getLastName());
        maDmpContact.setMbox(person.getMbox());

        if (person.getPersonIdentifier() != null) {
            MaDmpIdentifier maDmpIdentifier = new MaDmpIdentifier();
            mapEntityToMaDmp(person.getPersonIdentifier(), maDmpIdentifier);
            maDmpContact.setContact_id(maDmpIdentifier);
        }
    }

    public static void mapEntityToMaDmp(Contributor contributor, MaDmpContributor maDmpContributor) {
        Person person = contributor.getContributor();
        maDmpContributor.setName(person.getFirstName() + " " + person.getLastName());
        maDmpContributor.setMbox(person.getMbox());

        if (person.getPersonIdentifier() != null) {
            MaDmpIdentifier maDmpIdentifier = new MaDmpIdentifier();
            mapEntityToMaDmp(person.getPersonIdentifier(), maDmpIdentifier);
            maDmpContributor.setContributor_id(maDmpIdentifier);
        }

        List<EContributorRole> roleList = new ArrayList<>();
        roleList.add(contributor.getContributorRole());
        maDmpContributor.setRole(roleList);
    }

    public static void mapEntityToMaDmp(Identifier identifier, MaDmpIdentifier maDmpIdentifier) {
        maDmpIdentifier.setIdentifier(identifier.getIdentifier());
        if (identifier.getIdentifierType() != null)
            maDmpIdentifier.setIdentifierType(identifier.getIdentifierType().toString());
    }

    public static void mapEntityToMaDmp(Project project, MaDmpProject maDmpProject) {
        maDmpProject.setTitle(project.getTitle());
        maDmpProject.setDescription(project.getDescription());
        maDmpProject.setStart(project.getStart());
        maDmpProject.setEnd(project.getEnd());

        List<MaDmpFunding> maDmpFundingList = new ArrayList<>();
        if (project.getFunding() != null) {
            MaDmpFunding maDmpFunding = new MaDmpFunding();
            mapEntityToMaDmp(project.getFunding(), maDmpFunding);
            maDmpFundingList.add(maDmpFunding);
        }
        maDmpProject.setFunding(maDmpFundingList);
    }

    public static void mapEntityToMaDmp(Funding funding, MaDmpFunding maDmpFunding) {
        if (funding.getFundingStatus() != null)
            maDmpFunding.setFunding_status(funding.getFundingStatus().toString());

        if (funding.getFunderIdentifier() != null) {
            MaDmpIdentifier maDmpIdentifier = new MaDmpIdentifier();
            mapEntityToMaDmp(funding.getFunderIdentifier(), maDmpIdentifier);
            maDmpFunding.setFunder_id(maDmpIdentifier);
        }

        if (funding.getGrantIdentifier() != null) {
            MaDmpIdentifier maDmpIdentifier = new MaDmpIdentifier();
            mapEntityToMaDmp(funding.getGrantIdentifier(), maDmpIdentifier);
            maDmpFunding.setGrant_id(maDmpIdentifier);
        }
    }

    public static void mapEntityToMaDmp(Dataset dataset, MaDmpDataset maDmpDataset) {
        maDmpDataset.setTitle(dataset.getTitle());
        maDmpDataset.setDescription(dataset.getComment());
        maDmpDataset.setType(dataset.getType());

        List<MaDmpDistribution> maDmpDistributionList = new ArrayList<>();
        dataset.getDistributionList().forEach(distribution -> {
            MaDmpDistribution maDmpDistribution = new MaDmpDistribution();
            mapEntityToMaDmp(dataset, distribution, maDmpDistribution);
            maDmpDistributionList.add(maDmpDistribution);
        });
        maDmpDataset.setDistribution(maDmpDistributionList);
    }

    public static void mapEntityToMaDmp(Dataset dataset, Distribution distribution, MaDmpDistribution maDmpDistribution) {
        maDmpDistribution.setByte_size(dataset.getSize());

        if (dataset.getLicense() != null) {
            MaDmpLicense maDmpLicense = new MaDmpLicense();
            //TODO map to license object should include license date
            maDmpLicense.setLicense_ref(dataset.getLicense());
            maDmpDistribution.setLicense(maDmpLicense);
        }
        if (distribution.getHost() != null) {
            MaDmpHost maDmpHost = new MaDmpHost();
            mapEntityToMaDmp(distribution.getHost(), maDmpHost);
            maDmpDistribution.setHost(maDmpHost);
        }
    }

    public static void mapEntityToMaDmp(Host host, MaDmpHost maDmpHost) {
        maDmpHost.setTitle(host.getTitle());
    }
}

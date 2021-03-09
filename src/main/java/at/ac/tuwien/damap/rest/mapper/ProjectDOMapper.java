package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Funding;
import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.domain.Project;
import at.ac.tuwien.damap.rest.domain.FundingDO;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.domain.ProjectDO;

public class ProjectDOMapper {

    public static void mapEntityToDO(Project project, ProjectDO projectDO) {
        projectDO.setTitle(project.getTitle());
        projectDO.setDescription(project.getDescription());
        projectDO.setStart(project.getStart());
        projectDO.setEnd(project.getEnd());

        if (project.getFunding() != null) {
            FundingDO fundingDO = new FundingDO();
            mapEntityToDO(project.getFunding(), fundingDO);
            projectDO.setFunding(fundingDO);
        }
    }

    public static void mapEntityToDO(Funding funding, FundingDO fundingDO) {
        fundingDO.setFunding_status(funding.getFundingStatus());

        if (funding.getFunderIdentifier() != null) {
            IdentifierDO identifierFundingDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(funding.getFunderIdentifier(), identifierFundingDO);
            fundingDO.setFunder_id(identifierFundingDO);
        }

        if (funding.getGrantIdentifier() != null) {
            IdentifierDO identifierGrantDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(funding.getGrantIdentifier(), identifierGrantDO);
            fundingDO.setGrant_id(identifierGrantDO);
        }
    }


    public static void mapDOtoEntity(ProjectDO projectDO, Project project){

        project.setTitle(projectDO.getTitle());
        project.setDescription(projectDO.getDescription());
        project.setStart(projectDO.getStart());
        project.setEnd(projectDO.getEnd());

        if (projectDO.getFunding() != null) {
            Funding funding = new Funding();
            if (project.getFunding() != null)
                funding = project.getFunding();
            mapDOtoEntity(projectDO.getFunding(), funding);
            project.setFunding(funding);
        } else
            project.setFunding(null);
    }

    public static void mapDOtoEntity(FundingDO fundingDO, Funding funding) {
        funding.setFundingStatus(fundingDO.getFunding_status());

        if (fundingDO.getFunder_id() != null) {
            Identifier identifierFunding = new Identifier();
            if (funding.getFunderIdentifier() != null)
                identifierFunding = funding.getFunderIdentifier();
            IdentifierDOMapper.mapDOtoEntity(fundingDO.getFunder_id(), identifierFunding);
            funding.setFunderIdentifier(identifierFunding);
        } else
            funding.setFunderIdentifier(null);

        if (fundingDO.getGrant_id() != null) {
            Identifier identifierGrant = new Identifier();
            if (funding.getGrantIdentifier() != null)
                identifierGrant = funding.getGrantIdentifier();
            IdentifierDOMapper.mapDOtoEntity(fundingDO.getGrant_id(), identifierGrant);
            funding.setGrantIdentifier(identifierGrant);
        } else
            funding.setGrantIdentifier(null);
    }
}
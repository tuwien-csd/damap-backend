package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Funding;
import at.ac.tuwien.damap.domain.Project;
import at.ac.tuwien.damap.rest.domain.FundingDO;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.domain.ProjectDO;

public class ProjectDOMapper {

    public static void mapAtoB(Project project, ProjectDO projectDO) {
        projectDO.setId(project.id);
        projectDO.setTitle(project.getTitle());
        projectDO.setDescription(project.getDescription());
        projectDO.setStart(project.getStart());
        projectDO.setEnd(project.getEnd());

        FundingDO fundingDO = new FundingDO();
        mapAtoB(project.getFunding(), fundingDO);
        projectDO.setFunding(fundingDO);
    }

    public static void mapAtoB(Funding funding, FundingDO fundingDO) {
        fundingDO.setFunding_status(funding.getFundingStatus());

        IdentifierDO identifierFundingDO = new IdentifierDO();
        IdentifierDOMapper.mapAtoB(funding.getFunderIdentifier(), identifierFundingDO);
        fundingDO.setFunder_id(identifierFundingDO);

        IdentifierDO identifierGrantDO = new IdentifierDO();
        IdentifierDOMapper.mapAtoB(funding.getGrantIdentifier(), identifierGrantDO);
        fundingDO.setGrant_id(identifierGrantDO);
    }
}

package at.ac.tuwien.damap.rest.projects.mapper;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.dmp.domain.FundingDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.projects.dto.FundingDTO;
import at.ac.tuwien.damap.rest.projects.dto.IdentifierDTO;
import at.ac.tuwien.damap.rest.projects.dto.ProjectDTO;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@UtilityClass
public class ProjectDTOMapper {

    public ProjectDO mapAtoB(ProjectDTO projectDTO, ProjectDO projectDO) {
        projectDO.setUniversityId(projectDTO.getId());
        projectDO.setTitle(projectDTO.getTitle());
        projectDO.setDescription(projectDTO.getDescription());
        try {
            projectDO.setStart(new SimpleDateFormat("dd-MM-yyyy").parse(projectDTO.getStart()));
            projectDO.setEnd(new SimpleDateFormat("dd-MM-yyyy").parse(projectDTO.getEnd()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FundingDO fundingDO = new FundingDO();
        mapAtoB(projectDTO.getFunding(), fundingDO);
        projectDO.setFunding(fundingDO);

        return projectDO;
    }

    public FundingDO mapAtoB(FundingDTO fundingDTO, FundingDO fundingDO) {
        fundingDO.setFunding_status(fundingDTO.getFunding_status());

        IdentifierDO identifierFundingDO = new IdentifierDO();
        mapAtoB(fundingDTO.getFunder_id(), identifierFundingDO);
        fundingDO.setFunder_id(identifierFundingDO);

        IdentifierDO identifierGrantDO = new IdentifierDO();
        mapAtoB(fundingDTO.getGrant_id(), identifierGrantDO);
        fundingDO.setGrant_id(identifierGrantDO);

        return fundingDO;
    }

    public IdentifierDO mapAtoB(IdentifierDTO identifierDTO, IdentifierDO identifierDO){
        identifierDO.setIdentifier(identifierDTO.getIdentifier());
        if (identifierDTO.getType() != null)
            identifierDO.setType(EIdentifierType.valueOf(identifierDTO.getType()));

        return identifierDO;
    }
}

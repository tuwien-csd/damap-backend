package at.ac.tuwien.rest.projectdatabase.mapper;

import at.ac.tuwien.damap.rest.domain.FundingDO;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.domain.ProjectDO;
import at.ac.tuwien.rest.projectdatabase.dto.FundingDTO;
import at.ac.tuwien.rest.projectdatabase.dto.IdentifierDTO;
import at.ac.tuwien.rest.projectdatabase.dto.ProjectDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProjectDTOMapper {

    public static void mapAtoB(ProjectDTO projectDTO, ProjectDO projectDO) {
        projectDO.setId(projectDTO.getId());
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
    }

    public static void mapAtoB(FundingDTO fundingDTO, FundingDO fundingDO) {
        fundingDO.setFunding_status(fundingDTO.getFunding_status());

        IdentifierDO identifierFundingDO = new IdentifierDO();
        mapAtoB(fundingDTO.getFunder_id(), identifierFundingDO);
        fundingDO.setFunder_id(identifierFundingDO);

        IdentifierDO identifierGrantDO = new IdentifierDO();
        mapAtoB(fundingDTO.getGrant_id(), identifierGrantDO);
        fundingDO.setGrant_id(identifierGrantDO);
    }

    public static void mapAtoB(IdentifierDTO identifierDTO, IdentifierDO identifierDO){
        identifierDO.setIdentifier(identifierDTO.getIdentifier());
        identifierDO.setType(identifierDTO.getType());
    }
}

package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.rest.projects.ProjectSupplementDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectSupplementDOMapper {

    public Dmp mapDOtoEntity(ProjectSupplementDO projectSupplementDO, Dmp dmp){

        if (projectSupplementDO.getPersonalData() != null) {
            dmp.setPersonalData(projectSupplementDO.getPersonalData());
            dmp.setPersonalDataCris(true);
        }
        if (projectSupplementDO.getSensitiveData() != null) {
            dmp.setSensitiveData(projectSupplementDO.getSensitiveData());
            dmp.setSensitiveDataCris(true);
        }
        if (projectSupplementDO.getLegalRestrictions() != null) {
            dmp.setLegalRestrictions(projectSupplementDO.getLegalRestrictions());
            dmp.setLegalRestrictionsCris(true);
        }
        if (projectSupplementDO.getHumanParticipants() != null) {
            dmp.setHumanParticipants(projectSupplementDO.getHumanParticipants());
            dmp.setHumanParticipantsCris(true);
        }
        if (projectSupplementDO.getEthicalIssuesExist() != null) {
            dmp.setEthicalIssuesExist(projectSupplementDO.getEthicalIssuesExist());
            dmp.setEthicalIssuesExistCris(true);
        }
        if (projectSupplementDO.getCommitteeReviewed() != null) {
            dmp.setCommitteeReviewed(projectSupplementDO.getCommitteeReviewed());
            dmp.setCommitteeReviewedCris(true);
        }
        if (projectSupplementDO.getCostsExist() != null) {
            dmp.setCostsExist(projectSupplementDO.getCostsExist());
            dmp.setCostsExistCris(true);
        }

        return dmp;
    }
}

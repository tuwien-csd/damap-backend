package org.damap.base.rest.dmp.mapper;

import org.damap.base.domain.Dmp;
import org.damap.base.rest.projects.ProjectSupplementDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectSupplementDOMapper {

    public Dmp mapDOtoEntity(ProjectSupplementDO projectSupplementDO, Dmp dmp){

        if (projectSupplementDO.getPersonalData() != null) {
            dmp.setPersonalData(projectSupplementDO.getPersonalData());
            dmp.setPersonalDataCris(projectSupplementDO.getPersonalData());
        } else
            dmp.setPersonalDataCris(null);

        if (projectSupplementDO.getSensitiveData() != null) {
            dmp.setSensitiveData(projectSupplementDO.getSensitiveData());
            dmp.setSensitiveDataCris(projectSupplementDO.getSensitiveData());
        } else
            dmp.setSensitiveDataCris(null);

        if (projectSupplementDO.getLegalRestrictions() != null) {
            dmp.setLegalRestrictions(projectSupplementDO.getLegalRestrictions());
            dmp.setLegalRestrictionsCris(projectSupplementDO.getLegalRestrictions());
        } else
            dmp.setLegalRestrictionsCris(null);

        if (projectSupplementDO.getHumanParticipants() != null) {
            dmp.setHumanParticipants(projectSupplementDO.getHumanParticipants());
            dmp.setHumanParticipantsCris(projectSupplementDO.getHumanParticipants());
        } else
            dmp.setHumanParticipantsCris(null);

        if (projectSupplementDO.getEthicalIssuesExist() != null) {
            dmp.setEthicalIssuesExist(projectSupplementDO.getEthicalIssuesExist());
            dmp.setEthicalIssuesExistCris(projectSupplementDO.getEthicalIssuesExist());
        } else
            dmp.setEthicalIssuesExistCris(null);

        if (projectSupplementDO.getCommitteeReviewed() != null) {
            dmp.setCommitteeReviewed(projectSupplementDO.getCommitteeReviewed());
            dmp.setCommitteeReviewedCris(projectSupplementDO.getCommitteeReviewed());
        } else
            dmp.setCommitteeReviewedCris(null);

        if (projectSupplementDO.getCostsExist() != null) {
            dmp.setCostsExist(projectSupplementDO.getCostsExist());
            dmp.setCostsExistCris(projectSupplementDO.getCostsExist());
        } else
            dmp.setCostsExistCris(null);

        return dmp;
    }
}

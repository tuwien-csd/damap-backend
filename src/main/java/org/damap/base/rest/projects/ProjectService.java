package org.damap.base.rest.projects;

import java.util.List;

import jakarta.ws.rs.core.MultivaluedMap;

import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.service.ServiceRead;
import org.damap.base.rest.base.service.ServiceSearch;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.ProjectDO;

public interface ProjectService extends ServiceSearch<ProjectDO>, ServiceRead<ProjectDO> {

    List<ContributorDO> getProjectStaff(String projectId);
    ProjectSupplementDO getProjectSupplement(String projectId);
    ContributorDO getProjectLeader(String projectId);
    ResultList<ProjectDO> getRecommended(MultivaluedMap<String, String> queryParams);
}

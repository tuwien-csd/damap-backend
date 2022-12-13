package at.ac.tuwien.damap.rest.projects;

import java.util.List;

import at.ac.tuwien.damap.rest.base.service.ServiceRead;
import at.ac.tuwien.damap.rest.base.service.ServiceSearch;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;

public interface ProjectService extends ServiceSearch<ProjectDO>, ServiceRead<ProjectDO> {

    List<ContributorDO> getProjectStaff(String projectId);

    ProjectSupplementDO getProjectSupplement(String projectId);

    ContributorDO getProjectLeader(String projectId);
}

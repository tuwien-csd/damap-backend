package at.ac.tuwien.damap.rest.projects;

import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;

import java.util.List;

public interface ProjectService {

    List<ProjectDO> getProjectList(String personId);

    List<ProjectMemberDO> getProjectStaff(String projectId);

    ProjectDO getProjectDetails(String projectId);

    ProjectSupplementDO getProjectSupplement(String projectId);
}

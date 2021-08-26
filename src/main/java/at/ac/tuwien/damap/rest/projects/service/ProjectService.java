package at.ac.tuwien.damap.rest.projects.service;

import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectMemberDO;

import java.util.List;

public interface ProjectService {

    List<ProjectDO> getProjectList(String personId);

    List<ProjectMemberDO> getProjectStaff(String projectId);
}

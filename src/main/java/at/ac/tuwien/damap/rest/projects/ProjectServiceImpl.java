package at.ac.tuwien.damap.rest.projects;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import io.quarkus.arc.DefaultBean;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class ProjectServiceImpl implements ProjectService {
    @Override
    public List<ProjectDO> getProjectList(String personId) {
        return Collections.emptyList();
    }

    @Override
    public List<ContributorDO> getProjectStaff(String projectId) {
        return Collections.emptyList();
    }

    @Override
    public ProjectDO getProjectDetails(String projectId) {
        return null;
    }

    @Override
    public ProjectSupplementDO getProjectSupplement(String projectId) {
        return null;
    }

    @Override
    public ContributorDO getProjectLeader(String projectId) {
        return null;
    }
}

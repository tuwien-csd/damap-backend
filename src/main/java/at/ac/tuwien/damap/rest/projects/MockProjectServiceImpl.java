package at.ac.tuwien.damap.rest.projects;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.persons.MockPersonRestService;
import io.quarkus.arc.DefaultBean;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import java.util.List;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class MockProjectServiceImpl implements ProjectService {

    @Inject
    @RestClient
    MockPersonRestService mockPersonRestService;

    @Inject
    @RestClient
    MockProjectRestService mockProjectRestService;

    @Override
    public List<ProjectDO> getProjectList(String personId) {
        return mockProjectRestService.getProjectList();
    }

    @Override
    public List<ContributorDO> getProjectStaff(String projectId) {
        return mockPersonRestService.getContributorSearchResult();
    }

    @Override
    public ProjectDO getProjectDetails(String projectId) {
        return mockProjectRestService.getProjectDetails(projectId).get(0);
    }

    @Override
    public ProjectSupplementDO getProjectSupplement(String projectId) {
        try {
            return mockProjectRestService.getProjectSupplement();
        } catch (ProcessingException pe) {
            return null;
        }
    }

    @Override
    public ContributorDO getProjectLeader(String projectId) {
        return mockPersonRestService.getContributorSearchResult().get(0);
    }
}

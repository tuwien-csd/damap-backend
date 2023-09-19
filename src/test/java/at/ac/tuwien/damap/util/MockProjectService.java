package at.ac.tuwien.damap.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import io.quarkus.test.Mock;
import io.quarkus.test.InjectMock;

@Mock
@ApplicationScoped
public class MockProjectService extends MockProjectServiceImpl{
    @Inject TestDOFactory testDOFactory;

    @InjectMock
    MockProjectServiceImpl projectService;

    @Override
    public ContributorDO getProjectLeader(String projectId) {
        return testDOFactory.getTestContributorDO();
    }
}

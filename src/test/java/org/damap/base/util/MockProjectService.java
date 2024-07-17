package org.damap.base.util;

import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.projects.MockProjectServiceImpl;

/** MockProjectService class. */
@Mock
@ApplicationScoped
public class MockProjectService extends MockProjectServiceImpl {
  @Inject TestDOFactory testDOFactory;

  @InjectMock MockProjectServiceImpl projectService;

  /** {@inheritDoc} */
  @Override
  public ContributorDO getProjectLeader(String projectId) {
    return testDOFactory.getTestContributorDO();
  }
}

package org.damap.base.util;

import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;
import org.damap.base.rest.dmp.service.DmpService;

@Mock
@ApplicationScoped
public class MockDmpService extends DmpService {
  @InjectMock MockProjectService projectService;
}

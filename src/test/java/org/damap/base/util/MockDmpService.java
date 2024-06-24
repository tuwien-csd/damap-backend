package org.damap.base.util;

import jakarta.enterprise.context.ApplicationScoped;
import org.damap.base.rest.dmp.service.DmpService;
import io.quarkus.test.Mock;
import io.quarkus.test.InjectMock;

@Mock
@ApplicationScoped
public class MockDmpService extends DmpService{
    @InjectMock
    MockProjectService projectService;
}

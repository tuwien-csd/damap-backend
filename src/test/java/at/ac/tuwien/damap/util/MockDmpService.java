package at.ac.tuwien.damap.util;

import jakarta.enterprise.context.ApplicationScoped;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import io.quarkus.test.Mock;
import io.quarkus.test.InjectMock;

@Mock
@ApplicationScoped
public class MockDmpService extends DmpService{
    @InjectMock
    MockProjectService projectService;
}

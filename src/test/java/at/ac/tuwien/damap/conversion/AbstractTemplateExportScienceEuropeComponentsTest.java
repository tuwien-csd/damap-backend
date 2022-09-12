package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.extern.jbosslog.JBossLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@JBossLog
public class AbstractTemplateExportScienceEuropeComponentsTest extends AbstractTemplateExportScienceEuropeComponents {

    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    MockProjectServiceImpl mockProjectRestService;

    @BeforeEach
    public void setup() {
        Mockito.when(mockProjectRestService.getProjectDetails(anyString())).thenReturn(testDOFactory.getTestProjectDO());
    }

    @Test
    void determinteDatasetIDsTest(){
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        exportSetup(dmpDO.getId());
        Assertions.assertEquals(datasetTableIDs.size(), datasets.size(), dmpDO.getDatasets().size());
    }
}

package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.persons.orcid.ORCIDPersonServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@JBossLog
class AbstractTemplateExportScienceEuropeComponentsTest extends AbstractTemplateExportScienceEuropeComponents {

    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    MockProjectServiceImpl mockProjectService;

    @InjectMock
    ORCIDPersonServiceImpl orcidPersonServiceImpl;

    @BeforeEach
    public void setup() {
        Mockito.when(mockProjectService.read(anyString())).thenReturn(testDOFactory.getTestProjectDO());
        Mockito.when(orcidPersonServiceImpl.read(any(String.class))).thenReturn(testDOFactory.getTestContributorDO());
    }

    @Test
    void determinteDatasetIDsTest() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        exportSetup(dmpDO.getId());
        Assertions.assertEquals(datasetTableIDs.size(), datasets.size(), dmpDO.getDatasets().size());
    }
}

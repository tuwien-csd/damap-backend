package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
public class ExportTemplateBrokerTest {

    @Inject
    ExportTemplateBroker exportTemplateBroker;

    @Inject
    TestDOFactory testDOFactory;

    @Inject
    DmpService dmpService;

    @InjectMock
    MockProjectServiceImpl mockProjectRestService;

    @InjectSpy
    ExportScienceEuropeTemplate exportScienceEuropeTemplate;

    @InjectSpy
    ExportFWFTemplate exportFWFTemplate;

    @BeforeEach
    public void setup() {
        Mockito.when(mockProjectRestService.getProjectDetails(anyString())).thenReturn(testDOFactory.getTestProjectDO());
    }

    @Test
    void testDefaultToScienceEuropeTemplate() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        dmpDO.getProject().getFunding().getFunderId().setIdentifier("Any Identifier");
        dmpService.update(dmpDO);

        XWPFDocument document = exportTemplateBroker.exportTemplate(dmpDO.getId());
        Assertions.assertNotNull(document);
        Mockito.verify(exportScienceEuropeTemplate).exportTemplate(dmpDO.getId());
    }

    @Test
    void testLoadFWFTemplate() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        dmpDO.getProject().getFunding().getFunderId().setIdentifier("501100002428");
        dmpService.update(dmpDO);

        XWPFDocument document = exportTemplateBroker.exportTemplate(dmpDO.getId());
        Assertions.assertNotNull(document);
        Mockito.verify(exportFWFTemplate).exportTemplate(dmpDO.getId());
    }
}

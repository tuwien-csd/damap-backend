package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.TestSetup;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@QuarkusTest
class ExportHorizonEuropeTemplateTest extends TestSetup {

    @Inject
    ExportHorizonEuropeTemplate exportHorizonEuropeTemplate;

    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    MockProjectServiceImpl mockProjectService;

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testTemplateDmp() {
        final DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();

        XWPFDocument document = null;
        try {
            document = exportHorizonEuropeTemplate.exportTemplate(dmpDO.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(document);
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testEmptyTemplateDmp() {
        final DmpDO emptyDmpDO = testDOFactory.getOrCreateTestDmpDOEmpty();

        // testing the export document return not a null document
        XWPFDocument document = null;
        try {
            document = exportHorizonEuropeTemplate.exportTemplate(emptyDmpDO.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(document);
    }
}

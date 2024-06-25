package org.damap.base.conversion;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.projects.MockProjectServiceImpl;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class ExportScienceEuropeTemplateTest {

  @Inject ExportScienceEuropeTemplate exportScienceEuropeTemplate;

  @Inject TestDOFactory testDOFactory;

  @InjectMock MockProjectServiceImpl mockProjectService;

  String projectId = "-1";

  @BeforeEach
  public void setup() {
    Mockito.when(mockProjectService.read(projectId)).thenReturn(testDOFactory.getTestProjectDO());
  }

  @Test
  @TestSecurity(authorizationEnabled = false)
  void testFWFTemplateDmp() {
    final DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();

    XWPFDocument document = null;
    try {
      document = exportScienceEuropeTemplate.exportTemplate(dmpDO.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    Assertions.assertNotNull(document);
  }

  @Test
  @TestSecurity(authorizationEnabled = false)
  void testEmptyFWFTemplateDmp() {
    final DmpDO emptyDmpDO = testDOFactory.getOrCreateTestDmpDOEmpty();

    // testing the export document return not a null document
    XWPFDocument document = null;
    try {
      document = exportScienceEuropeTemplate.exportTemplate(emptyDmpDO.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    Assertions.assertNotNull(document);
  }
}

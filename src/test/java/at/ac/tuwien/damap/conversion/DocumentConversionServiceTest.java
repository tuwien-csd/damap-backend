package at.ac.tuwien.damap.conversion;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.enterprise.inject.Instance;

@QuarkusTest

public class DocumentConversionServiceTest {

    @Inject
    Instance<DocumentConversionService> documentConversionServices;

    @Test
    public void testEmptyDmp() throws Exception {
        for (DocumentConversionService documentConversionService : documentConversionServices) {

            System.out.println("Test passed");
            XWPFDocument document = documentConversionService.loadTemplate("template/template.docx");
        }
    }
}

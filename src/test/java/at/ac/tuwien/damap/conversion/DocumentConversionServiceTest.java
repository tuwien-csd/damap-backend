package at.ac.tuwien.damap.conversion;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.enterprise.inject.Instance;
import java.util.*;

@QuarkusTest

public class DocumentConversionServiceTest {

    @Inject
    Instance<DocumentConversionService> documentConversionServices;

    @Test
    public void testLoadTemplate() throws Exception {
        for (DocumentConversionService documentConversionService : documentConversionServices) {
            //testing load template
            //TODO: generate blank document as a test
            //TODO: notify when there is no template file available
            XWPFDocument document = documentConversionService.loadTemplate("template/template.docx", "[", "]");
            Assertions.assertNotNull(document);

            //testing multiple variable handling
            String result1 = documentConversionService.multipleVariable(testTwoVariable());
            String result2 = documentConversionService.multipleVariable(testThreeVariable());
            Assertions.assertEquals(twoVariables(), result1);
            Assertions.assertEquals(threeVariables(), result2);
        }
    }

    private List<String> testTwoVariable() {
        List<String> variables = Arrays.asList("var1", "var2");
        return variables;
    }

    private List<String> testThreeVariable() {
        List<String> variables = Arrays.asList("var1", "var2", "var3");
        return variables;
    }

    private String twoVariables() {
        return "var1 and var2";
    }

    private String threeVariables() {
        return "var1, var2, var3";
    }
}

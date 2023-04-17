package at.ac.tuwien.damap.conversion;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.enterprise.inject.Instance;
import java.util.*;

@QuarkusTest
public class AbstractTemplateExportFunctionsTest {

    @Inject
    Instance<AbstractTemplateExportFunctions> documentConversionServices;

    @Inject
    Instance<TemplateFileBrokerService> templateFileBrokerServices;

    @Test
    public void testLoadTemplate() throws Exception {
        for (AbstractTemplateExportFunctions documentConversionService : documentConversionServices) {
            XWPFDocument document = documentConversionService.loadTemplate(templateFileBrokerServices.get().loadScienceEuropeTemplate(), "[", "]");
            Assertions.assertNotNull(document);

            //testing multiple variable handling
            String result1 = documentConversionService.multipleVariable(testTwoVariable());
            String result2 = documentConversionService.multipleVariable(testThreeVariable());
            String result3 = documentConversionService.multipleVariableAnd(testTwoVariable());
            String result4 = documentConversionService.multipleVariableAnd(testThreeVariable());
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
        return "var1, var2";
    }

    private String threeVariables() {
        return "var1, var2, var3";
    }

    private String twoVariablesAnd() {
        return "var1 and var2";
    }

    private String threeVariablesAnd() {
        return "var1, var2 and var3";
    }
}

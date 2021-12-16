package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.repo.DmpRepo;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;

import javax.inject.Inject;

@QuarkusTest

public class ExportFWFTemplateTest {

    @Inject
    ExportFWFTemplate exportFWFTemplate;

    @InjectMock
    DmpRepo dmpRepo;

    @BeforeEach
    public void setup() {
        Mockito.when(dmpRepo.findById(anyLong())).thenReturn(this.createDmp());
    }


    @Test
    public void testEmptyDmp() throws Exception {
        Long id = 123L;

        //testing the export document return not a null document
        XWPFDocument document = exportFWFTemplate.exportTemplate(id);
        Assertions.assertNotNull(document);
    }

    private Dmp createDmp() {
        Dmp dmp = new Dmp();
        dmp.setTitle("Mock Dmp");

        return dmp;
    }
}

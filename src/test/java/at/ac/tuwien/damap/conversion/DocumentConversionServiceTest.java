package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.conversion.DocumentConversionService;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectMemberDO;
import at.ac.tuwien.damap.rest.dmp.service.SaveDmpWrapper;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.repo.DmpRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest

@JBossLog
public class DocumentConversionServiceTest {

    @Inject
    DocumentConversionService documentConversionService;

    @InjectMock
    DmpService dmpService;

    @InjectMock
    DmpRepo dmpRepo;

    @BeforeEach
    public void setup() {
        Mockito.when(dmpService.getDmpById(anyLong())).thenReturn(this.createDmpDO());
        Mockito.when(dmpRepo.findById(anyLong())).thenReturn(this.createDmp());
    }

    @Test
    public void testEmptyDmp() throws Exception{
        DmpDO dmp = this.createDmpDO();
        Long id = dmp.getId();
        log.info("Mock DMP title: " + dmp.getTitle());
        log.info("Mock DMP ID: " + dmp.getId());

        XWPFDocument document = documentConversionService.getFWFTemplate(id);
    }

    private DmpDO createDmpDO() {
        DmpDO dmpDO = new DmpDO();
        dmpDO.setTitle("Mock Dmp Empty");
        dmpDO.setTitle("Mock Dmp");
        dmpDO.setId(123L);

        return dmpDO;
    }

    private Dmp createDmp() {
        Dmp dmp = new Dmp();
        dmp.setTitle("Mock Dmp");

        return dmp;
    }
}

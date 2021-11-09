package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.conversion.DocumentConversionService;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectMemberDO;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@QuarkusTest

public class DocumentConversionServiceTest {

    @Test
    public void testPreSection() {
        System.out.println("test");
    }
}

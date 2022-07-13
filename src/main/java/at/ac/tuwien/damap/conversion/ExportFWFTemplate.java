package at.ac.tuwien.damap.conversion;

import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
@JBossLog
public class ExportFWFTemplate extends ScienceEuropeComponents{

    @Inject
    TemplateFileBrokerService templateFileBrokerService;

    public XWPFDocument exportTemplate(long dmpId) {

        exportSetup(dmpId);
        determinteDatasetIDs();
        //load template and properties
        String startChar = "[";
        String endChar = "]";
        prop = templateFileBrokerService.getFWFTemplateResource();
        XWPFDocument document = null;
        try {
            document = loadTemplate(templateFileBrokerService.loadFWFTemplate(), startChar, endChar);
        } catch (Exception e) {
            log.error("Template file not found!");
            return null;
        }
        xwpfParagraphs = document.getParagraphs();
        xwpfTables = document.getTables();
        // get template table containing all information that needs to be replaced.
        XWPFTable templateTable = xwpfTables.get(3);
        //get tables within template table
        List<XWPFTable> templateXwpfTables = parseContentTables(templateTable);

        titlePage();
        contributorInformation();
        costInformation();
        datasetsInformation();

        //section IV
        storageInformation();
        sensitiveDataInformation();
        repoinfoAndToolsInformation();

        //section V
        legalEthicalInformation();

        //in FWF template this replaces paragraphs within the template table
        replaceTableVariables(templateTable, replacements);
        //this replaces the tables with the main template table
        tableContent(templateXwpfTables);

        return document;
    }

    private List<XWPFTable> parseContentTables(XWPFTable templateTable) {
        List<XWPFTable> templateXwpfTables = new ArrayList<>();
        for (XWPFTableRow row : templateTable.getRows()){
            if (row.getTableCells().size() > 1){
                templateXwpfTables.addAll(row.getCell(1).getTables());
            }
        }
        return templateXwpfTables;
    }
}

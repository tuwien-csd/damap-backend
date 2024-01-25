package at.ac.tuwien.damap.conversion;

import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@JBossLog
public class ExportScienceEuropeTemplate extends AbstractTemplateExportScienceEuropeComponents {

    public XWPFDocument exportTemplate(long dmpId) {
        log.info("Exporting Science Europe document for DMP with ID: " + dmpId);
        //load project
        exportSetup(dmpId);
        //load template and properties
        String startChar = "[";
        String endChar = "]";
        prop = templateFileBrokerService.getScienceEuropeTemplateResource();
        XWPFDocument document = null;
        try {
            document = loadTemplate(templateFileBrokerService.loadScienceEuropeTemplate(), startChar, endChar);
        } catch (Exception e) {
            log.error("Template file not found!");
            return null;
        }
        xwpfParagraphs = document.getParagraphs();
        xwpfTables = document.getTables();

        //First step of the export: create a mapping of variables and its desired replacement values
        //load replacements values for science europe sections
        loadScienceEuropeContent();

        //Second step of the export: variables replacement with a mapping reference that has been defined
        log.debug("Export steps: Replace in paragraph");
        replaceInParagraphs(xwpfParagraphs, replacements);

        //Third step of the export: dynamic table in all sections will be added from row number two until the end of data list.
        //TO DO: combine the function with the first row generation to avoid double code of similar modification.
        log.debug("Export steps: Replace in table");
        tableContent(document, xwpfTables);

        //Fourth step of the export: modify the content of the document's footer
        log.debug("Export steps: Replace in footer");
        replaceTextInFooter(document, footerMap);

        log.debug("Export steps: Export finished");
        return document;
    }
}

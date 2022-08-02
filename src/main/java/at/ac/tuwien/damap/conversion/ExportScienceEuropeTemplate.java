package at.ac.tuwien.damap.conversion;

import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@JBossLog
public class ExportScienceEuropeTemplate extends AbstractTemplateExportScienceEuropeComponents {

    public XWPFDocument exportTemplate(long dmpId) {
        //load project
        exportSetup(dmpId);
        determinteDatasetIDs();
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

        //Pre Section including general information from the project,
        // e.g. project title, coordinator, contact person, project and grant number.
        log.debug("Export steps: Pre section");
        titlePage();
        contributorInformation();

        //Section 1 contains the dataset information table and how data is generated or used
        log.debug("Export steps: Section 1");
        datasetsInformation();

        //Section 2 contains about the documentation and data quality including versioning and used metadata.
        log.debug("Export steps: Section 2");
        sectionTwo();

        //Section 3 contains storage and backup that will be used for the data in the research
        // including the data access and sensitive aspect.
        log.debug("Export steps: Section 3");
        storageInformation();
        sensitiveDataInformation();

        //Section 4 contains legal and ethical requirements.
        log.debug("Export steps: Section 4");
        legalEthicalInformation();

        //Section 5 contains information about data publication and long term preservation.
        log.debug("Export steps: Section 5");
        repoinfoAndToolsInformation();

        //Section 6 contains resources and cost information if necessary.
        log.debug("Export steps: Section 6");
        costInformation();

        //Second step of the export: variables replacement with a mapping reference that has been defined
        log.debug("Export steps: Replace in paragraph");
        replaceInParagraphs(xwpfParagraphs, replacements);

        //Third step of the export: dynamic table in all sections will be added from row number two until the end of data list.
        //TO DO: combine the function with the first row generation to avoid double code of similar modification.
        log.debug("Export steps: Replace in table");
        tableContent(xwpfTables);

        //Fourth step of the export: modify the content of the document's footer
        log.debug("Export steps: Replace in footer");
        replaceTextInFooter(document, footerMap);

        log.debug("Export steps: Export finished");
        return document;
    }

    //Section 2 variables replacement
    private void sectionTwo() {
        String metadata = "";

        if (dmp.getMetadata() == null) {
            addReplacement(replacements, "[metadata]", loadResourceService.loadVariableFromResource(prop, "metadata.no"));
        }
        else {
            if (dmp.getMetadata().equals("")) {
                addReplacement(replacements,"[metadata]", loadResourceService.loadVariableFromResource(prop, "metadata.no"));
            }
            else {
                metadata = dmp.getMetadata();
                if (metadata.charAt(metadata.length()-1)!='.') {
                    metadata = metadata + '.';
                }
                addReplacement(replacements,"[metadata]", metadata + " " + loadResourceService.loadVariableFromResource(prop, "metadata.avail"));
            }
        }

        if (dmp.getStructure() == null) {
            addReplacement(replacements,"[dataorganisation]", loadResourceService.loadVariableFromResource(prop, "dataOrganisation.no"));
        }
        else {
            if (dmp.getStructure().equals("")) {
                addReplacement(replacements,"[dataorganisation]", loadResourceService.loadVariableFromResource(prop, "dataOrganisation.no"));
            }
            else {
                addReplacement(replacements,"[dataorganisation]", dmp.getStructure());
            }
        }
    }
}

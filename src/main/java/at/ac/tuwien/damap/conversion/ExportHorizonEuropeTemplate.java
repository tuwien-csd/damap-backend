package at.ac.tuwien.damap.conversion;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.domain.Distribution;
import at.ac.tuwien.damap.domain.Repository;
import at.ac.tuwien.damap.enums.EContributorRole;
import lombok.extern.jbosslog.JBossLog;

@RequestScoped
@JBossLog
public class ExportHorizonEuropeTemplate extends AbstractTemplateExportScienceEuropeComponents {
    public XWPFDocument exportTemplate(long dmpId) {
        log.info("Exporting Horzion Europe document for DMP with ID: " + dmpId);
        // load project
        exportSetup(dmpId);
        // load template and properties
        String startChar = "[";
        String endChar = "]";
        prop = templateFileBrokerService.getHorizonEuropeTemplateResource();
        XWPFDocument document = null;
        try {
            document = loadTemplate(templateFileBrokerService.loadHorizonEuropeTemplate(), startChar, endChar);
        } catch (Exception e) {
            log.error("Template file not found!");
            return null;
        }
        xwpfParagraphs = document.getParagraphs();
        xwpfTables = document.getTables();

        // First step of the export: create a mapping of variables and its desired
        // replacement values
        // load replacements values for science europe sections
        loadHorizonEuropeContent();

        // Second step of the export: variables replacement with a mapping reference
        // that has been defined
        log.debug("Export steps: Replace in paragraph");
        replaceInParagraphs(xwpfParagraphs, replacements);

        // Third step of the export: dynamic table in all sections will be added from
        // row number two until the end of data list.
        // TO DO: combine the function with the first row generation to avoid double
        // code of similar modification.
        log.debug("Export steps: Replace in table");
        tableContent(xwpfTables);

        // Fourth step of the export: modify the content of the document's footer
        log.debug("Export steps: Replace in footer");
        replaceTextInFooter(document, footerMap);

        return document;
    }

    public void loadHorizonEuropeContent() {
        super.loadScienceEuropeContent();
        workPackageLeadersInformation();
    }

    @Override
    public void datasetsInformation() {
        super.datasetsInformation();

        var dataManagers = getContributorsByRole(dmp.getContributorList(), EContributorRole.DATA_MANAGER);

        String dataManagerInfo = loadResourceService.loadVariableFromResource(prop, "datamanagerInfo.none");

        if (!dataManagers.isEmpty()) {
            dataManagerInfo = getContributorsText(dataManagers);

            dataManagerInfo = String.format("%s %s", dataManagerInfo,
                    loadResourceService.loadVariableFromResource(prop, "datamanagerInfo"));
        }

        addReplacement(replacements, "[datamanagerInfo]", dataManagerInfo);
    }

    public void workPackageLeadersInformation() {
        var workPackageLeaders = getContributorsByRole(dmp.getContributorList(), EContributorRole.WORK_PACKAGE_LEADER);
        addReplacement(replacements, "[workPackageLeaders]", getContributorsText(workPackageLeaders));
    }

    @Override
    public void composeTableDatasetRepository(XWPFTable xwpfTable) {
        log.debug("Export steps: Dataset Repository Table");

        List<Dataset> newDatasets = getNewDatasets().stream().filter(dataset -> !dataset.getDelete()).toList();
        if (!newDatasets.isEmpty()) {
            for (int i = 0; i < newDatasets.size(); i++) {

                XWPFTableRow sourceTableRow = xwpfTable.getRow(2);
                XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                try {
                    newRow = insertNewTableRow(sourceTableRow, i + 2);
                } catch (Exception e) {
                    // new row could not be inserted. Silently fail for now.
                    log.error("Could not insert row into dataset table", e);
                    continue;
                }

                ArrayList<String> docVar = new ArrayList<>();
                docVar.add(datasetTableIDs.get(newDatasets.get(i).id));
                if (newDatasets.get(i).getDistributionList() != null) {
                    List<Distribution> distributions = newDatasets.get(i).getDistributionList();
                    List<String> repositories = new ArrayList<>();
                    for (Distribution distribution : distributions) {
                        if (Repository.class.isAssignableFrom(distribution.getHost().getClass()))
                            repositories.add(distribution.getHost().getTitle());
                    }
                    if (!repositories.isEmpty()) {
                        docVar.add(multipleVariable(repositories));
                    } else {
                        docVar.add("");
                    }
                } else {
                    docVar.add("");
                }

                if (newDatasets.get(i).getRetentionPeriod() != null)
                    docVar.add(newDatasets.get(i).getRetentionPeriod() + " years");
                else
                    docVar.add("");

                insertTableCells(xwpfTable, newRow, docVar);
            }
            xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
        } else {
            // clean row
            ArrayList<String> emptyContent = new ArrayList<>(List.of("", "", ""));
            insertTableCells(xwpfTable, xwpfTable.getRows().get(xwpfTable.getRows().size() - 1), emptyContent);
        }
        xwpfTable.removeRow(1);
        commitTableRows(xwpfTable);
    }
}

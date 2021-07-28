package at.ac.tuwien.conversion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.SimpleDateFormat;

import at.ac.tuwien.damap.domain.Cost;
import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.domain.Contributor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DocumentConversionService {

    @Inject
    DmpRepo dmpRepo;

    public XWPFDocument getFWFTemplate(long dmpId) throws Exception {

        Dmp dmp = dmpRepo.findById(dmpId);

        String template = "template/template.docx";
        ClassLoader classLoader = getClass().getClassLoader();

        XWPFDocument document = new XWPFDocument(classLoader.getResourceAsStream(template));

        //mapping general information
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<>();
        if (dmp.getProject() != null) {
            if (dmp.getProject().getTitle() != null)
                map.put("[projectname]", dmp.getProject().getTitle());
            if (dmp.getProject().getStart() != null)
                map.put("[startdate]", formatter.format(dmp.getProject().getStart()));
            if (dmp.getProject().getEnd() != null)
                map.put("[enddate]", formatter.format(dmp.getProject().getEnd()));
            if (dmp.getProject().getFunding().getGrantIdentifier() != null)
                map.put("[grantid]", dmp.getProject().getFunding().getGrantIdentifier().getIdentifier());
        }

        if (dmp.getCreated() != null) {
            map.put("[datever1]", formatter.format(dmp.getCreated()));
        }

        //mapping contact information
        if (dmp.getContact() != null) {
            //TO DO: add affiliation and ROR (currently not stored in TISS)
            String contactName = "[contactname]";
            String contactMail = "[contactmail]";
            String contactId = "[contactid]";

            if (dmp.getContact().getFirstName() != null && dmp.getContact().getFirstName() != null)
                contactName = dmp.getContact().getFirstName() + " " + dmp.getContact().getLastName();
            if (dmp.getContact().getMbox() != null)
                contactMail = dmp.getContact().getMbox();
            //TO DO: Check if the identifier is ORCID or not
            if (dmp.getContact().getPersonIdentifier() != null)
                contactId = dmp.getContact().getPersonIdentifier().getIdentifier();

            map.put("[contactname]", contactName);
            map.put("[contactmail]", contactMail);
            map.put("[contactid]", contactId);
        }

        //mapping contributor information
        if (dmp.getContributorList() != null) {
            String contributorValue = "";
            List<Contributor> contributors = dmp.getContributorList();
            for(Contributor contributor : contributors) {
                //TO DO: add affiliation and ROR (currently not stored in TISS)

                if (contributors.indexOf(contributor) != 0) {
                    contributorValue = contributorValue.concat("\r\n");
                }

                String contributorName = "";
                String contributorMail = "";
                String contributorId = "";

                if (contributor.getContributor().getFirstName() != null && contributor.getContributor().getLastName() != null)
                    contributorName = contributor.getContributor().getFirstName() + " " + contributor.getContributor().getLastName();
                if (contributor.getContributor().getMbox() != null)
                    contributorMail = contributor.getContributor().getMbox();
                if (contributor.getContributor().getPersonIdentifier() != null)
                    contributorId = contributor.getContributor().getPersonIdentifier().getIdentifier();

                contributorValue = contributorValue.concat(contributorName + ", " + contributorMail + ", " + contributorId + ", " + "[contributoraffiliation]" + ", " + "[contributorror]");
            }
            map.put("[contributors]", contributorValue);
        }
        else {
            map.put("[contributors]", "");
        }

        //mapping dataset information
        List<Dataset> datasets = dmp.getDatasetList();
        for (Dataset dataset : datasets) {
            int idx = datasets.indexOf(dataset) + 1;
            String docVar1 = "[dataset" + idx + "name]";
            String docVar2 = "[dataset" + idx + "type]";
            String docVar3 = "[dataset" + idx + "format]";
            String docVar4 = "[dataset" + idx + "vol]";
            String docVar5 = "[dataset" + idx + "license]";
            String docVar6 = "[dataset" + idx + "pubdate]";
            String docVar7 = "[dataset" + idx + "repo]";
            String docVar8 = "[dataset" + idx + "access]";

            String datasetName = "";
            String datasetType = "";
            String datasetFormat = "";
            String datasetVol = "";
            String datasetLicense = "";
            String datasetPubdate = "";
            String datasetRepo = "";
            String datasetAccess = "";

            if (dataset.getTitle() != null)
                datasetName = dataset.getTitle();
            if (dataset.getType() != null)
                datasetType = dataset.getType();
            if (dataset.getSize() != null && !dataset.getSize().equals("")) {
                datasetVol = format(Long.parseLong(dataset.getSize()))+"B";
            }
            if (dataset.getLicense() != null)
                datasetLicense = dataset.getLicense();
            if (dataset.getStart() != null)
                datasetPubdate = formatter.format(dataset.getStart());
            if (dataset.getHost() != null)
                datasetRepo = dataset.getHost().getTitle();
            if (dataset.getDataAccess() != null)
                datasetAccess = dataset.getDataAccess().toString();

            map.put(docVar1, datasetName);
            map.put(docVar2, datasetType);
            map.put(docVar3, datasetFormat);
            map.put(docVar4, datasetVol);
            map.put(docVar5, datasetLicense);
            map.put(docVar6, datasetPubdate);
            map.put(docVar7, datasetRepo);
            map.put(docVar8, datasetAccess);
        }

        if (datasets.size() == 0) {
            map.put("P1", "");
        }

        //mapping cost information
        Long totalCost = 0L;

        List<Cost> costList = dmp.getCosts();
        for (Cost cost : costList) {
            int idx = costList.indexOf(cost) + 1;
            String docVar1 = "[cost" + idx + "title]";
            String docVar2 = "[cost" + idx + "type]";
            String docVar3 = "[cost" + idx + "desc]";
            String docVar4 = "[cost" + idx + "currency]";
            String docVar5 = "[cost" + idx + "value]";
            String costTitle = "";
            String costType = "";
            String costDescription = "";
            String costCurrency = "";
            String costValue = "";
            String costCurrencyTotal = "";

            if (cost.getTitle() != null)
                costTitle = cost.getTitle();
            if (cost.getType() != null)
                costType = cost.getType().toString();
            if (cost.getDescription() != null )
                costDescription = cost.getDescription();
            if (cost.getCurrencyCode() != null) {
                costCurrency = cost.getCurrencyCode();
                if (costCurrencyTotal.equals("")) {
                    costCurrencyTotal = costCurrency;
                    map.put("[costcurrency]", costCurrencyTotal);
                }
            }
            if (cost.getValue() != null) {
                costValue = cost.getValue().toString();
                totalCost = totalCost + cost.getValue();
            }

            map.put(docVar1, costTitle);
            map.put(docVar2, costType);
            map.put(docVar3, costDescription);
            map.put(docVar4, costCurrency);
            map.put(docVar5, costValue);
        }

        map.put("[costtotal]", totalCost.toString());

        //variables replacement
        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();

        replaceInParagraphs(xwpfParagraphs, map);

        List<XWPFTable> tables = document.getTables();
        for (XWPFTable xwpfTable : tables) {
            if (xwpfTable.getRow(1) != null) {

                //dynamic table rows code for dataset
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1name]")
                        && datasets.size() > 1) {

                    for (int i = 2; i < datasets.size() + 1; i++) {

                        XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                        XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                        ArrayList<String> docVar = new ArrayList<String>();
                        docVar.add("P" + i);
                        docVar.add(datasets.get(i - 1).getTitle());
                        docVar.add(datasets.get(i - 1).getType());
                        docVar.add("");
                        if (!datasets.get(i-1).getSize().equals("")) {
                            docVar.add(format(Long.parseLong(datasets.get(i - 1).getSize())) + "B");
                        }
                        else {
                            docVar.add("");
                        }
                        docVar.add("");

                        List<XWPFTableCell> cells = newRow.getTableCells();

                        for (XWPFTableCell cell : cells) {

                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                for (XWPFRun run : paragraph.getRuns()) {
                                    run.setText(docVar.get(cells.indexOf(cell)), 0);
                                }
                            }
                        }

                        boolean weMustCommitTableRows = true;

                        if (weMustCommitTableRows) commitTableRows(xwpfTable);
                    }
                    //end of dynamic table rows code
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }


                //dynamic table rows code for data sharing
                //notes: cost number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1access]")
                        && datasets.size() > 1) {

                    for (int i = 2; i < datasets.size() + 1; i++) {

                        XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                        XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                        ArrayList<String> docVar = new ArrayList<String>();
                        docVar.add("" + i);
                        docVar.add(datasets.get(i - 1).getDataAccess().toString());
                        docVar.add("");
                        if (datasets.get(i - 1).getStart() != null) {
                            docVar.add(formatter.format(datasets.get(i - 1).getStart()));
                        }
                        else {
                            docVar.add("");
                        }
                        if (datasets.get(i - 1).getHost() != null) {
                            docVar.add(datasets.get(i - 1).getHost().getTitle());
                        }
                        else {
                            docVar.add("");
                        }
                        docVar.add("");
                        if (datasets.get(i - 1).getLicense() != null) {
                            docVar.add(datasets.get(i - 1).getLicense());
                        }
                        else {
                            docVar.add("");
                        }

                        List<XWPFTableCell> cells = newRow.getTableCells();

                        for (XWPFTableCell cell : cells) {

                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                for (XWPFRun run : paragraph.getRuns()) {
                                    run.setText(docVar.get(cells.indexOf(cell)), 0);
                                }
                            }
                        }

                        boolean weMustCommitTableRows = true;

                        if (weMustCommitTableRows) commitTableRows(xwpfTable);
                    }
                    //end of dynamic table rows code
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }

                //dynamic table rows code for cost
                //notes: cost number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(0).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[cost1title]")
                        && costList.size() > 1) {

                    for (int i = 2; i < costList.size() + 1; i++) {

                        XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                        XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                        ArrayList<String> docVar = new ArrayList<String>();
                        docVar.add(costList.get(i - 1).getTitle());
                        docVar.add(costList.get(i - 1).getType().toString());
                        docVar.add(costList.get(i - 1).getDescription());
                        docVar.add(costList.get(i - 1).getCurrencyCode());
                        docVar.add(costList.get(i - 1).getValue().toString());

                        List<XWPFTableCell> cells = newRow.getTableCells();

                        for (XWPFTableCell cell : cells) {

                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                for (XWPFRun run : paragraph.getRuns()) {
                                    run.setText(docVar.get(cells.indexOf(cell)), 0);
                                }
                            }
                        }

                        boolean weMustCommitTableRows = true;

                        if (weMustCommitTableRows) commitTableRows(xwpfTable);
                    }
                    //end of dynamic table rows code
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 2);
                }
            }

            List<XWPFTableRow> tableRows = xwpfTable.getRows();
            for (XWPFTableRow xwpfTableRow : tableRows) {
                List<XWPFTableCell> tableCells = xwpfTableRow
                        .getTableCells();
                for (XWPFTableCell xwpfTableCell : tableCells) {
                    xwpfParagraphs = xwpfTableCell.getParagraphs();
                    replaceInParagraphs(xwpfParagraphs, map);
                }
            }
        }

        return document;
    }

    private XWPFTableRow insertNewTableRow(XWPFTableRow sourceTableRow, int pos) throws Exception {
        XWPFTable table = sourceTableRow.getTable();
        CTRow newCTRrow = CTRow.Factory.parse(sourceTableRow.getCtRow().newInputStream());
        XWPFTableRow tableRow = new XWPFTableRow(newCTRrow, table);
        table.addRow(tableRow, pos);
        return tableRow;
    }

    static void commitTableRows(XWPFTable table) {
        int rowNr = 0;
        for (XWPFTableRow tableRow : table.getRows()) {
            table.getCTTbl().setTrArray(rowNr++, tableRow.getCtRow());
        }
    }

    private void replaceInParagraphs(List<XWPFParagraph> xwpfParagraphs, Map<String, String> replacements) {

        /*
            Each XWPFRun will contain part of a text. These are split weirdly (by Word?).
            Special characters will usually be separated from strings, but might be connected if several words are within that textblock.
            Also capitalized letters seem to behave differently and are sometimes separated from the characters following them.
         */

        for (XWPFParagraph xwpfParagraph : xwpfParagraphs) {
            List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
            for (XWPFRun xwpfRun : xwpfRuns) {
                String xwpfRunText = xwpfRun.getText(xwpfRun
                        .getTextPosition());
                for (Map.Entry<String, String> entry : replacements
                        .entrySet()) {
                    if (xwpfRunText != null
                            && xwpfRunText.contains(entry.getKey())) {
                        xwpfRunText = xwpfRunText.replace(
                                entry.getKey(), entry.getValue());
                    }
                }
                xwpfRun.setText(xwpfRunText, 0);
            }
        }
    }

    //Number conversion for data size
    private static final char[] SUFFIXES = {'K', 'M', 'G', 'T', 'P', 'E' };

    private static String format(long number) {
        if(number < 1000) {
            // No need to format this
            return String.valueOf(number);
        }
        // Convert to a string
        final String string = String.valueOf(number);
        // The suffix we're using, 1-based
        final int magnitude = (string.length() - 1) / 3;
        // The number of digits we must show before the prefix
        final int digits = (string.length() - 1) % 3 + 1;

        // Build the string
        char[] value = new char[4];
        for(int i = 0; i < digits; i++) {
            value[i] = string.charAt(i);
        }
        int valueLength = digits;
        // Can and should we add a decimal point and an additional number?
        if(digits == 1 && string.charAt(1) != '0') {
            value[valueLength++] = '.';
            value[valueLength++] = string.charAt(1);
        }
        value[valueLength++] = SUFFIXES[magnitude - 1];
        return new String(value, 0, valueLength);
    }
}

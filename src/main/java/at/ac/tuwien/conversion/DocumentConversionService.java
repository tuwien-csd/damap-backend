package at.ac.tuwien.conversion;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.text.SimpleDateFormat;

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

        String template = "template.docx";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(template)).getFile());

        XWPFDocument document = new XWPFDocument(Files.newInputStream(file.toPath()));

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

        List<Dataset> datasets = dmp.getDatasetList();
        for (Dataset dataset : datasets) {
            int idx = datasets.indexOf(dataset) + 1;
            String docVar1 = "[dataset" + idx + "name]";
            String docVar2 = "[dataset" + idx + "type]";
            String docVar3 = "[dataset" + idx + "format]";
            String docVar4 = "[dataset" + idx + "vol]";
            String datasetName = "";
            String datasetType = "";
            String datasetFormat = "";
            String datasetVol = "";

            if (dataset.getTitle() != null)
                datasetName = dataset.getTitle();
            if (dataset.getType() != null)
                datasetType = dataset.getType();
            if (dataset.getSize() != null && !dataset.getSize().equals("")) {
                datasetVol = format(Long.parseLong(dataset.getSize()))+"B";
            }

            map.put(docVar1, datasetName);
            map.put(docVar2, datasetType);
            map.put(docVar3, datasetFormat);
            map.put(docVar4, datasetVol);
        }

        if (datasets.size() == 0) {
            map.put("P1", "");
        }

        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();

        replaceInParagraphs(xwpfParagraphs, map);

        List<XWPFTable> tables = document.getTables();
        for (XWPFTable xwpfTable : tables) {
            if (xwpfTable.getRow(1) != null) {
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1name]")
                        && datasets.size() > 1) {

                    //dynamic table rows code
                    //notes: dataset number 2 until the end will be written directly to the table
                    for (int i = 2; i < datasets.size() + 1; i++) {

                        XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                        XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                        ArrayList<String> docVar = new ArrayList<String>();
                        docVar.add("P" + i);
                        docVar.add(datasets.get(i - 1).getTitle());
                        docVar.add(datasets.get(i - 1).getType());
                        docVar.add("");
                        docVar.add(format(Long.parseLong(datasets.get(i-1).getSize()))+"B");
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

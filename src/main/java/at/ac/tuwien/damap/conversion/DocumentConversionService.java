package at.ac.tuwien.damap.conversion;

import java.io.InputStream;
import java.util.*;
import java.text.SimpleDateFormat;

import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.repo.InternalStorageTranslationRepo;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.*;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.inject.Inject;

@JBossLog
public class DocumentConversionService {

    @Inject
    DmpRepo dmpRepo;

    @Inject
    InternalStorageTranslationRepo internalStorageTranslationRepo;

    //Convert the date for readable format for the document
    protected final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    //Method to load the export template
    public XWPFDocument loadTemplate (InputStream template, String startChar, String endChar) throws Exception{
        //Extract document using Apache POI https://poi.apache.org/
        XWPFDocument document = new XWPFDocument(template);

        return templateFormatting(document, startChar, endChar);
    }

    //Method to replace variable in the document's paragraphs
    static void replaceInParagraphs(List<XWPFParagraph> xwpfParagraphs, Map<String, String> replacements) {

        /*
            Each XWPFRun will contain part of a text. These are split weirdly (by Word?).
            Special characters will usually be separated from strings, but might be connected if several words are within that textblock.
            Also capitalized letters seem to behave differently and are sometimes separated from the characters following them.
         */

        for (XWPFParagraph xwpfParagraph : xwpfParagraphs) {
            List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
            for (XWPFRun xwpfRun : xwpfRuns) {
                String xwpfRunText = xwpfRun.getText(xwpfRun.getTextPosition());
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    if (xwpfRunText != null && xwpfRunText.contains(entry.getKey())) {
                        //handle new line for contributor list and storage information
                        if (entry.getValue().contains(";")){
                            String[] value=entry.getValue().split(";");
                            for(String text : value){
                                xwpfParagraph.setAlignment(ParagraphAlignment.LEFT);
                                xwpfRun.setText(text.trim());
                                xwpfRun.addBreak();
                                xwpfRun.addBreak();
                            }
                            xwpfRunText = "";
                        }
                        //general case for non contributor list
                        else {
                            if (entry.getKey().equals("[projectname]") && entry.getValue().contains("#oversize")) { //resize title to be smaller
                                xwpfRun.setFontSize(xwpfRun.getFontSize()-4);
                                xwpfRunText = xwpfRunText.replace(entry.getKey(), entry.getValue().replace("#oversize", ""));
                            }
                            else {
                                xwpfRunText = xwpfRunText.replace(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
                xwpfRun.setText(xwpfRunText, 0);
            }
        }
    }

    //Method to do a replacement of variable with desired value
    public void addReplacement(Map<String, String> replacements, String var, Object dmpContent) {
        //null case handling
        String content = (dmpContent == null) ? "" : String.valueOf(dmpContent);
        if (!Objects.equals(content, "")) {
            if (dmpContent.getClass() == java.sql.Timestamp.class || dmpContent.getClass() == Date.class) {
                content = formatter.format(dmpContent);
            }
        }
        replacements.put(var, content);
    }

    //Method to add new row in a table
    public XWPFTableRow insertNewTableRow(XWPFTableRow sourceTableRow, int pos) throws Exception {
        XWPFTable table = sourceTableRow.getTable();
        CTRow newCTRrow = CTRow.Factory.parse(sourceTableRow.getCtRow().newInputStream());
        XWPFTableRow tableRow = new XWPFTableRow(newCTRrow, table);
        table.addRow(tableRow, pos);
        return tableRow;
    }

    //Method that is required to be executed for table content modification
    static void insertTableCells(XWPFTable table, XWPFTableRow newRow, ArrayList<String> cellContent) {
        List<XWPFTableCell> cells = newRow.getTableCells();
        for (XWPFTableCell cell : cells) {
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setText(cellContent.get(cells.indexOf(cell)), 0);
                }
            }
        }
        commitTableRows(table);
    }

    static void replaceTableVariables(XWPFTable table, Map<String, String> replacements){
        //this replaces variables in tables (e.g. costcurrency)
        List<XWPFTableRow> tableRows = table.getRows();
        for (XWPFTableRow xwpfTableRow : tableRows) {
            List<XWPFTableCell> tableCells = xwpfTableRow
                    .getTableCells();
            for (XWPFTableCell xwpfTableCell : tableCells) {
                List<XWPFParagraph> xwpfParagraphs = xwpfTableCell.getParagraphs();
                replaceInParagraphs(xwpfParagraphs, replacements);
            }
        }
    }

    //Method that is required to be executed for table content modification
    static void commitTableRows(XWPFTable table) {
        int rowNr = 0;
        for (XWPFTableRow tableRow : table.getRows()) {
            table.getCTTbl().setTrArray(rowNr++, tableRow.getCtRow());
        }
    }

    //Method to do a replacement specific in document's footer area
    public void replaceTextInFooter(XWPFDocument doc, Map<String, String> replacements) {
        for (XWPFFooter footer : doc.getFooterList()) {
            for (XWPFParagraph xwpfParagraph : footer.getParagraphs()) {
                for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
                    String xwpfRunText = xwpfRun.getText(xwpfRun.getTextPosition());
                    for (Map.Entry<String, String> entry : replacements.entrySet()) {
                        if (xwpfRunText != null && xwpfRunText.contains(entry.getKey())) {
                            xwpfRunText = xwpfRunText.replace(entry.getKey(), entry.getValue());
                        }
                    }
                    xwpfRun.setText(xwpfRunText, 0);
                }
            }
        }
    }

    //Method to combine multiple values become one string. Can be used for generate values from a list of object or replace one variable with multiple values in one execution.
    public String multipleVariable(List<String> variableList) {
        switch (variableList.size()) {
            case 0:
                return "";
            case 2:
                return String.join(" and ", variableList);
            default:
                return String.join(", ", variableList);
        }
    }

    //Method to format the template. Parsing all text inside the document and preprocessing the variables (currently, text started with "[" and end with "]") that are seperated to multiple runs.
    public XWPFDocument templateFormatting(XWPFDocument document, String startChar, String endChar) throws Exception {

        log.info("Formatting template document");
        //handling all paragraphs in the document except those inside the tables
        formattingParagraph(document.getParagraphs(), startChar, endChar);

        //tables need to handled in independent loop since the paragraph list not return the paragraph inside the tables
        formattingTable(document.getTables(), startChar, endChar);

        //footer need to handled in independent loop since the paragraph list not return the paragraph inside the footer
        if (document.getFooterList() != null) {
            for (XWPFFooter footer : document.getFooterList()) {
                formattingParagraph(footer.getParagraphs(), startChar, endChar);
            }
        }

        return document;
    }

    public void formattingTable(List<XWPFTable> xwpfTables, String startChar, String endChar){
        if (xwpfTables != null) {
            for (XWPFTable xwpfTable : xwpfTables) {
                for (XWPFTableRow row : xwpfTable.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        formattingParagraph(cell.getParagraphs(), startChar, endChar);
                        formattingTable(cell.getTables(), startChar, endChar);
                    }
                }
            }
        }
    }

    //Method to preprocessing format of the paragraph list
    public void formattingParagraph(List<XWPFParagraph> xwpfParagraphs, String startChar, String endChar) {
        StringBuilder sb = new StringBuilder("");
        boolean mergeRun = false;
        List<Integer> removeRunIndex = new ArrayList<>();

        for (XWPFParagraph xwpfParagraph : xwpfParagraphs) {

            List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();

            for (XWPFRun xwpfRun : xwpfRuns) {
                String xwpfRunText = xwpfRun.getText(xwpfRun.getTextPosition());

                if (xwpfRunText != null) {
                    if (xwpfRunText.contains(startChar)) {
                        if (!xwpfRunText.contains(endChar)) {
                            removeRunIndex.add(xwpfRuns.indexOf(xwpfRun));
                            mergeRun = true;
                            if (sb.length()>0) {
                                sb.delete(0, sb.length());
                            }
                            sb.append(xwpfRunText);
                        }
                    }
                    else {
                        if (mergeRun) {
                            sb.append(xwpfRunText);
                            if (xwpfRunText.contains(endChar)) {
                                mergeRun = false;
                                xwpfRun.setText(sb.toString(),0);
                            }
                            else {
                                removeRunIndex.add(xwpfRuns.indexOf(xwpfRun));
                            }
                        }
                    }
                }
            }
            if (!removeRunIndex.isEmpty()) {
                Collections.sort(removeRunIndex, Collections.reverseOrder()); //reverse sort index to avoid missing index while deleting the run
                for (Integer runIndex : removeRunIndex) {
                    xwpfParagraphs.get(xwpfParagraphs.indexOf(xwpfParagraph)).removeRun(runIndex);
                }
                removeRunIndex.clear();
            }
        }
    }
}




package at.ac.tuwien.damap.conversion;

import java.util.*;
import java.text.SimpleDateFormat;

import at.ac.tuwien.damap.repo.DmpRepo;
import lombok.ToString;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFFooter;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@JBossLog
public class DocumentConversionService {

    @Inject
    DmpRepo dmpRepo;

    //Convert the date for readable format for the document
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public String setTemplate (String template) {
        return template;
    }

    public XWPFDocument loadTemplate (String template) throws Exception{
        //Loading a template file in resources folder
        ClassLoader classLoader = getClass().getClassLoader();

        //Extract document using Apache POI https://poi.apache.org/
        XWPFDocument document = new XWPFDocument(classLoader.getResourceAsStream(template));

        return document;
    }

    public void replaceInParagraphs(List<XWPFParagraph> xwpfParagraphs, Map<String, String> replacements) {

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

    public void addReplacement(Map<String, String> replacements, String var, Object dmpContent) {
        //null case handling
        String content = (dmpContent == null) ? "" : String.valueOf(dmpContent);
        if (content != "") {
            if (dmpContent.getClass() == java.sql.Timestamp.class || dmpContent.getClass() == Date.class) {
                content = formatter.format(dmpContent);
            }
        }
        replacements.put(var, content);
    }

    public XWPFTableRow insertNewTableRow(XWPFTableRow sourceTableRow, int pos) throws Exception {
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
}

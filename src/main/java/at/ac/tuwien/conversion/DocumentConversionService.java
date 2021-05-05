package at.ac.tuwien.conversion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DocumentConversionService {

    @Inject
    DmpRepo dmpRepo;

    public XWPFDocument getFWFTemplate(long dmpId) throws IOException {

        Dmp dmp = dmpRepo.findById(dmpId);

        String template = "..\\src\\template\\template.docx";
        XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get(template)));

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
            List<Contributor> contributors = dmp.getContributorList();
            for(Contributor contributor : contributors) {
                //TO DO: add affiliation and ROR (currently not stored in TISS)
                int idx = contributors.indexOf(contributor)+1;
                String docVar1 = "[contributor"+idx+"name]";
                String docVar2 = "[contributor"+idx+"mail]";
                String docVar3 = "[contributor"+idx+"id]";
                String contributorName = "";
                String contributorMail = "";
                String contributorId = "";

                if (contributor.getContributor().getFirstName() != null && contributor.getContributor().getLastName() != null)
                    contributorName = contributor.getContributor().getFirstName() + " " + contributor.getContributor().getLastName();
                if (contributor.getContributor().getMbox() != null)
                    contributorMail = contributor.getContributor().getMbox();
                if (contributor.getContributor().getPersonIdentifier() != null)
                    contributorId = contributor.getContributor().getPersonIdentifier().getIdentifier();

                map.put(docVar1, contributorName);
                map.put(docVar2, contributorMail);
                map.put(docVar3, contributorId);
            }

            //Delete unused contributor variables
            for (int i = contributors.size()+1; i < 6; i++) {
                String docVar1 = "[contributor"+i+"name], ";
                String docVar2 = "[contributor"+i+"mail], ";
                String docVar3 = "[contributor"+i+"id], ";
                String docVar4 = "[contributor"+i+"affiliation], ";
                String docVar5 = "[contributor"+i+"ror]";

                map.put(docVar1, "");
                map.put(docVar2, "");
                map.put(docVar3, "");
                map.put(docVar4, "");
                map.put(docVar5, "");
            }
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

        //Delete unused contributor variables
        for (int i = datasets.size() + 1; i < 5; i++) {
            String docVar1 = "[dataset" + i + "name]";
            String docVar2 = "[dataset" + i + "type]";
            String docVar3 = "[dataset" + i + "format]";
            String docVar4 = "[dataset" + i + "vol]";

            map.put(docVar1, "");
            map.put(docVar2, "");
            map.put(docVar3, "");
            map.put(docVar4, "");
        }

        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
        replaceInParagraphs(xwpfParagraphs, map);

        List<XWPFTable> tables = document.getTables();
        for (XWPFTable xwpfTable : tables) {
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

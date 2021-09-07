package at.ac.tuwien.conversion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.SimpleDateFormat;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.repo.DmpRepo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

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
            if (dmp.getProject().getFunding().getGrantIdentifier() != null &&
                    dmp.getProject().getFunding().getGrantIdentifier().getIdentifier() != null)
                map.put("[grantid]", dmp.getProject().getFunding().getGrantIdentifier().getIdentifier());
        }
        
        if (dmp.getCreated() != null) {
            map.put("[datever1]", formatter.format(dmp.getCreated()));
        }

        //mapping contact information
        if (dmp.getContact() != null) {
            //TO DO: add affiliation and ROR (currently not stored in TISS)
            //Now affiliation assigned manually with TU Wien
            String contactName = "";
            String contactMail = "";
            String contactId = "";
            String contactaff = "TU Wien";
            String identifierType = "";
            String identifierID = "";

            if (dmp.getContact().getFirstName() != null && dmp.getContact().getFirstName() != null)
                contactName = dmp.getContact().getFirstName() + " " + dmp.getContact().getLastName();
            if (dmp.getContact().getMbox() != null)
                contactMail = dmp.getContact().getMbox();
            //TO DO: Check if the identifier is ORCID or not
            if (dmp.getContact().getPersonIdentifier() != null) {
                identifierID = dmp.getContact().getPersonIdentifier().getIdentifier();
                if (dmp.getContact().getPersonIdentifier().getIdentifierType().toString().equals("orcid")) {
                    identifierType = "ORCID iD: ";
                }
                contactId = identifierType + identifierID;
            }

            map.put("[contactname]", contactName);
            map.put("[contactmail]", contactMail);
            map.put("[contactid]", contactId);
            map.put("[contactaffiliation]", contactaff);
            map.put("[contactror]", "");
        }

        //mapping project coordinator and contributor information
        String coordinatorName = "";
        String coordinatorMail = "";
        String coordinatorId = "";
        String coordinatorAffiliation = "TU Wien";
        String coordinatorRor = "";

        if (dmp.getContributorList() != null) {
            String contributorPerson = "";

            List<Contributor> contributors = dmp.getContributorList();
            List<String> contributorList = new ArrayList<>();
            for(Contributor contributor : contributors) {
                //TO DO: add affiliation and ROR (currently not stored in TISS)

                String contributorName = "";
                String contributorMail = "";
                String contributorId = "";
                String contributorRole = "";
                String identifierID = "";
                String identifierType = "";
                String leaderName = "";
                String leaderMail = "";
                String leaderId = "";

                if (contributor.getContributor().getFirstName() != null && contributor.getContributor().getLastName() != null)
                    contributorName = contributor.getContributor().getFirstName() + " " + contributor.getContributor().getLastName();
                if (contributor.getContributor().getMbox() != null)
                    contributorMail = contributor.getContributor().getMbox();
                if (contributor.getContributor().getPersonIdentifier() != null) {
                    identifierID = contributor.getContributor().getPersonIdentifier().getIdentifier();
                    if (contributor.getContributor().getPersonIdentifier().getIdentifierType().toString().equals("orcid")) {
                        identifierType = "ORCID iD: ";
                    }
                    contributorId = identifierType + identifierID;
                }
                if (contributor.getContributorRole().getRole() != null) {
                    contributorRole = contributor.getContributorRole().getRole();
                    if ((contributorRole.equals("Project Leader") || contributorRole.equals("Project Manager")) && coordinatorName.equals("")) {
                        coordinatorName = contributorName;
                        coordinatorMail = contributorMail;
                        coordinatorId = contributorId;
                    }
                }
                contributorPerson = contributorName + ", " + contributorMail + ", " + contributorId + ", " + "TU Wien" + ", " + contributorRole;
                contributorList.add(contributorPerson);
            }
            String contributorValue = String.join(";", contributorList);
            map.put("[contributors]", contributorValue);
        }
        else {
            map.put("[contributors]", "");
        }

        map.put("[coordinatorname]", coordinatorName);
        map.put("[coordinatormail]", coordinatorMail);
        map.put("[coordinatorid]", coordinatorId);
        map.put("[coordinatoraffiliation]", coordinatorAffiliation);
        map.put("[coordinatorror]", coordinatorRor);

        //Section 1

        //TODO datasets and hosts are now connected by Distribution objects
        // the following table (5a) should only list datasets with distributions on a repository for long term storage
        // (each distribution should be listed, therefore there can be multiple entries for the same dataset)
        //mapping dataset information to published data table (5a) and long term storage (5b) (only datasets with distributions on repositories)
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
            String docVar9 = "[dataset" + idx + "sensitive]";
            String docVar10 = "[dataset" + idx + "restriction]";
            String docVar11 = "[dataset" + idx + "storage]";
            String docVar12 =  "[dataset" + idx + "period]";

            String datasetName = "";
            String datasetType = "";
            String datasetFormat = "";
            String datasetVol = "";
            String datasetLicense = "";
            String datasetPubdate = "";
            String datasetRepo = "";
            String datasetAccess = "";
            String datasetSensitive = "";
            String datasetRestriction = "";
            String datasetStorage = "";
            String datasetPeriod = "";

            if (dataset.getTitle() != null)
                datasetName = dataset.getTitle();
            if (dataset.getType() != null)
                datasetType = dataset.getType();
            if (dataset.getSize() != null)
                datasetVol = format(dataset.getSize())+"B";
            if (dataset.getLicense() != null)
                datasetLicense = dataset.getLicense();
            if (dataset.getStart() != null)
                datasetPubdate = formatter.format(dataset.getStart());
            if (dataset.getDistributionList() != null){
                List<Distribution> distributions = dataset.getDistributionList();
                List<String> repositories = new ArrayList<>();
                List<String> storage = new ArrayList<>();

                for (Distribution distribution: distributions) {
                    if (distribution.getHost().getHostId() != null)
                        if (distribution.getHost().getHostId().contains("r3")) { //respository
                            repositories.add(distribution.getHost().getTitle());
                        } else { //storage
                            storage.add(distribution.getHost().getTitle());
                        }
                }
                if (repositories.size() > 0)
                    datasetRepo = String.join(", ", repositories);
                if (storage.size() > 0)
                    datasetStorage = String.join(", ", storage);
            }
            if (dataset.getDataAccess() != null)
                datasetAccess = dataset.getDataAccess().toString();
            if (dataset.getSensitiveData() != null) {
                if (dataset.getSensitiveData()) {
                    datasetSensitive = "yes";
                }
                else {
                    datasetSensitive = "true";
                }
            }
            if (dataset.getLegalRestrictions() != null) {
                if (dataset.getLegalRestrictions())
                    datasetRestriction = dataset.getDmp().getLegalRestrictionsComment();
            }

            map.put(docVar1, datasetName);
            map.put(docVar2, datasetType);
            map.put(docVar3, datasetFormat);
            map.put(docVar4, datasetVol);
            map.put(docVar5, datasetLicense);
            map.put(docVar6, datasetPubdate);
            map.put(docVar7, datasetRepo);
            map.put(docVar8, datasetAccess);
            map.put(docVar9, datasetSensitive);
            map.put(docVar10, datasetRestriction);
            map.put(docVar11, datasetStorage);
            map.put(docVar12, datasetPeriod);
        }

        if (datasets.size() == 0) {
            map.put("P1", "");
        }

        if (dmp.getDataGeneration() != null)
            map.put("[datageneration]", dmp.getDataGeneration());

        //Section 2

        if (dmp.getMetadata() != null) {
            if (dmp.getMetadata().equals("")) {
                map.put("[metadata]", "As there are no domain specific metadata standards applicable, we will provide a README file with an explanation of all values and terms used next to each file with data.");
            } else {
                map.put("[metadata]", "To help others identify, discover and reuse the data, " + dmp.getMetadata() + " will be used.");
            }
        }

        //Section 3

        List<Host> hostList = dmp.getHostList();
        String storageVar = "";

        for (Host host: hostList) {
            List<Distribution> distributions = host.getDistributionList();
            String hostVar = host.getTitle();

            String distVar = "";
            for (Distribution dist: distributions) {

                int idx = datasets.indexOf(dist.getDataset())+1;
                distVar = distVar + "P" + idx + " (" + dist.getDataset().getTitle() + ")";
                if (distributions.indexOf(dist)+1 < distributions.size())
                    distVar = distVar + ", ";
            }

            if (host.getHostId() != null) {
                if (host.getHostId().contains("r3")) {
                    storageVar = storageVar.concat(distVar + " will use " + hostVar + " for the data repository.");
                }
                else {
                    if (distVar != "")
                        storageVar = storageVar.concat(distVar + " will be stored in " + hostVar + ".");
                }
            }
            else { //case for external storage, will have null host Id
                storageVar = storageVar.concat(distVar + " will be stored in " + hostVar + ".");
            }

            if (hostList.indexOf(host)+1 < hostList.size())
                storageVar = storageVar.concat(";");

            System.out.println(storageVar);
        }

        if (dmp.getExternalStorageInfo() != null)
            storageVar = storageVar.concat(";");
        storageVar = storageVar.concat("External storage will be used " + dmp.getExternalStorageInfo().toLowerCase(Locale.ROOT));

        map.put("[storage]", storageVar);

        //Section 4

        //Section 4a: personal data
        String personalData = "";
        if (dmp.getPersonalData()) {
            String personalDataSentence = "Personal data will be collected/used as part of the project. ";
            String personalDataset = "";
            String datasetSentence = "";
            List<String> datasetList = new ArrayList<>();

            for (Dataset dataset: datasets) {

                int idx = datasets.indexOf(dataset)+1;
                if (dataset.getPersonalData()) {
                    personalDataset = personalDataset + "P" + idx + " (" + dataset.getTitle() + ")";
                    datasetList.add(personalDataset);
                }
            }

            if (datasetList.size()>0) {
                personalDataset = String.join(",", datasetList);
                datasetSentence = " will containing personal data. ";
            }

            personalData = personalDataSentence + " " + personalDataset + datasetSentence + "To ensure that only authorised users can access personal data, " + dmp.getPersonalDataAccess() + " will be used.";

        } else {
            personalData = "At this stage, it is not foreseen to process any personal data in the project. If this changes, advice will be sought from the data protection specialist at TU Wien (Verena Dolovai), and the DMP will be updated.";
        }
        map.put("[personaldata]", personalData);

        //Section 4a: sensitive data

        String sensitiveData = "";
        if (dmp.getSensitiveData()) {
            String sensitiveDataSentence = "";
            String sensitiveDataset = "";
            String datasetSentence = "";
            List<String> datasetList = new ArrayList<>();

            for (Dataset dataset: datasets) {

                int idx = datasets.indexOf(dataset)+1;
                if (dataset.getSensitiveData()) {
                    sensitiveDataset = sensitiveDataset + "P" + idx + " (" + dataset.getTitle() + ")";
                    datasetList.add(sensitiveDataset);
                }
            }

            if (datasetList.size()>0) {
                sensitiveDataset = String.join(",", datasetList);
                datasetSentence = " will containing sensitive data. ";
            }

            sensitiveData = sensitiveDataSentence + sensitiveDataset + datasetSentence + "To ensure that the dataset containing sensitive data stored and transfered safe, " + dmp.getSensitiveDataSecurity() + " will be taken.";

        } else {
            sensitiveData = "At this stage, it is not foreseen to process any sensitive data in the project. If this changes, advice will be sought from the data protection specialist at TU Wien (Verena Dolovai), and the DMP will be updated.";
        }
        map.put("[sensitivedata]", sensitiveData);

        //Section 4b: legal restriction

        String legalRestriction = "";
        if (dmp.getLegalRestrictions()) {
            String legalRestrictionSentence = "";
            String legalRestrictionDataset = "";
            List<String> datasetList = new ArrayList<>();

            for (Dataset dataset: datasets) {

                int idx = datasets.indexOf(dataset)+1;
                if (dataset.getLegalRestrictions()) {
                    legalRestrictionDataset = legalRestrictionDataset + "P" + idx + " (" + dataset.getTitle() + ")";
                    datasetList.add(legalRestrictionDataset);
                }
            }

            if (datasetList.size()>0) {
                legalRestrictionDataset = String.join(",", datasetList);
                legalRestrictionSentence = "There is a concern of legal restriction for dataset ";
            }

            legalRestriction = legalRestrictionSentence + legalRestrictionDataset + ". " + dmp.getLegalRestrictionsComment() + ".";

        }

        map.put("[legalrestriction]", legalRestriction);

        //Section 4c: ethical issues

        String ethicalIssues = "";
        if (dmp.getEthicalIssuesExist()) {
            String ethicalSentence = "Ethical issues in the project have been identified and discussed with the Research Ethics Coordinator at TU Wien (https://www.tuwien.at/en/research/rti-support/research-ethics/).";
            ethicalIssues = ethicalSentence + " " + dmp.getEthicalComplianceStatement() + "Relevant ethical guidelines in this project are " + dmp.getEthicsReport() + ".";
        } else {
            ethicalIssues = "No particular ethical issue is foreseen with the data to be used or produced by the project. This section will be updated if issues arise.";
        }
        map.put("[ethicalissues]", ethicalIssues);

        //Section 5

        String targetAudience = "";
        String tools = "";

        if (dmp.getTargetAudience() != null)
            targetAudience = dmp.getTargetAudience();

        if (dmp.getTools() != null)
            tools = dmp.getTools();

        map.put("[targetaudience]", targetAudience);
        map.put("[tools]", tools);

        //Section 6

        String costs = "";

        if (dmp.getCostsExist() != null) {
            if (dmp.getCostsExist()) {
                costs = "There are costs dedicated to data management and ensuring that data will be FAIR as outlined below.";
            } else {
                costs = "There are no costs dedicated to data management and ensuring that data will be FAIR.";
            }
        }

        map.put("[costs]", costs);

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

                //dynamic table rows code for dataset (1a)
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1name]")) {

                    if (datasets.size() > 1) {
                        for (int i = 2; i < datasets.size() + 1; i++) {

                            XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                            XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                            ArrayList<String> docVar = new ArrayList<String>();
                            docVar.add("P" + i);
                            docVar.add(datasets.get(i - 1).getTitle());
                            docVar.add(datasets.get(i - 1).getType());
                            docVar.add("");

                            if (datasets.get(i-1).getSize() != null) {
                                docVar.add(format(datasets.get(i - 1).getSize()) + "B");
                            }
                            else {
                                docVar.add("");
                            }

                            if (datasets.get(i-1).getSensitiveData()) {
                                docVar.add("yes");
                            }
                            else {
                                docVar.add("no");
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
                    }
                    //end of dynamic table rows code
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }

                //dynamic table rows code for data sharing
                //table 5a
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1access]")) {

                    if (datasets.size() > 1) {
                        for (int i = 2; i < datasets.size() + 1; i++) {

                            XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                            XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                            ArrayList<String> docVar = new ArrayList<String>();
                            docVar.add("P" + i);
                            docVar.add(datasets.get(i - 1).getDataAccess().toString());

                            if (datasets.get(i - 1).getLegalRestrictions() != null) {
                                if (datasets.get(i - 1).getLegalRestrictions()) {
                                    docVar.add(datasets.get(i - 1).getDmp().getLegalRestrictionsComment());
                                } else {
                                    docVar.add("");
                                }
                            } else {
                                docVar.add("");
                            }

                            if (datasets.get(i - 1).getStart() != null) {
                                docVar.add(formatter.format(datasets.get(i - 1).getStart()));
                            }
                            else {
                                docVar.add("");
                            }
                            //TODO datasets and hosts are now connected by Distribution objects
                            if (datasets.get(i - 1).getDistributionList() != null){
                                List<Distribution> distributions = datasets.get(i - 1).getDistributionList();
                                List<String> repositories = new ArrayList<>();
                                for (Distribution distribution: distributions) {
                                    if (distribution.getHost().getHostId() != null)
                                        if (distribution.getHost().getHostId().contains("r3")) {
                                            repositories.add(distribution.getHost().getTitle());
                                        }
                                }
                                if (repositories.size() > 0) {
                                    docVar.add(String.join(", ", repositories));
                                }
                                else {
                                    docVar.add("");
                                }
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
                    }
                    //end of dynamic table rows code
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }

                //table 5b
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1storage]")) {

                    if (datasets.size() > 1) {
                        for (int i = 2; i < datasets.size() + 1; i++) {

                            XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                            XWPFTableRow newRow = insertNewTableRow(sourceTableRow, i);

                            ArrayList<String> docVar = new ArrayList<String>();
                            docVar.add("P" + i);
                            //TODO datasets and hosts are now connected by Distribution objects
                            if (datasets.get(i - 1).getDistributionList() != null){
                                List<Distribution> distributions = datasets.get(i - 1).getDistributionList();
                                List<String> storage = new ArrayList<>();
                                for (Distribution distribution: distributions) {
                                    if (distribution.getHost().getHostId() != null)
                                        if (!distribution.getHost().getHostId().contains("r3")) {
                                            storage.add(distribution.getHost().getTitle());
                                        }
                                }
                                if (storage.size() > 0) {
                                    docVar.add(String.join(", ", storage));
                                }
                                else {
                                    docVar.add("");
                                }
                            }
                            docVar.add("");
                            if (datasets.get(i - 1).getDmp().getTargetAudience() != null) {
                                docVar.add(datasets.get(i - 1).getDmp().getTargetAudience());
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
                    }
                    //end of dynamic table rows code
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }

                //dynamic table rows code for cost
                //notes: cost number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(0).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[cost1title]")) {
                    if (costList.size() > 1) {
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
                String xwpfRunText = xwpfRun.getText(xwpfRun.getTextPosition());
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    if (xwpfRunText != null && xwpfRunText.contains(entry.getKey())) {
                        //handle new line for contributor list and storage information
                        if (entry.getKey().equals("[contributors]") || entry.getKey().equals("[storage]")){
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
                            xwpfRunText = xwpfRunText.replace(entry.getKey(), entry.getValue());
                        }
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

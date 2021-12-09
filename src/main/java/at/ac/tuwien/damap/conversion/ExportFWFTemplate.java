package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.enums.ESecurityMeasure;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.NumberFormat;

import org.apache.poi.xwpf.usermodel.*;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@JBossLog
public class ExportFWFTemplate extends DocumentConversionService{

    public XWPFDocument exportTemplate(long dmpId) throws Exception {
        // TODO: replace template link with template uploaded from frontend
        String template = setTemplate("template/template.docx");

        Map<String, String> replacements = new HashMap<>();
        Map<String, String> footerMap = new HashMap<>();

        XWPFDocument document = loadTemplate(template);
        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
        List<XWPFTable> tables = document.getTables();

        //Loading data related to the project from database
        Dmp dmp = dmpRepo.findById(dmpId);
        List<Dataset> datasets = dmp.getDatasetList();
        List<Cost> costList = dmp.getCosts();

        //Convert the date for readable format for the document
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //Pre Section including general information from the project,
        // e.g. project title, coordinator, contact person, project and grant number.
        log.info("Export steps: Pre section");
        preSection(dmp, replacements, formatter);

        //Section 1 contains the dataset information table and how data is generated or used
        log.info("Export steps: Section 1");
        sectionOne(dmp, replacements, datasets, formatter);

        //Section 2 contains about the documentation and data quality including versioning and used metadata.
        log.info("Export steps: Section 2");
        sectionTwo(dmp, replacements);

        //Section 3 contains storage and backup that will be used for the data in the research
        // including the data access and sensitive aspect.
        log.info("Export steps: Section 3");
        sectionThree(dmp, replacements, datasets);

        //Section 4 contains legal and ethical requirements.
        log.info("Export steps: Section 4");
        sectionFour(dmp, replacements, datasets);

        //Section 5 contains information about data publication and long term preservation.
        log.info("Export steps: Section 5");
        sectionFive(dmp, replacements);

        //Section 6 contains resources and cost information if necessary.
        log.info("Export steps: Section 6");
        sectionSix(dmp, replacements, costList);

        //variables replacement
        log.info("Export steps: Replace in paragraph");
        replaceInParagraphs(xwpfParagraphs, replacements);

        //Dynamic table in all sections will be added from row number two until the end of data list.
        //TO DO: combine the function with the first row generation to avoid double code of similar modification.
        log.info("Export steps: Replace in table");
        tableContent(xwpfParagraphs, replacements, tables, datasets, costList, formatter);

        replaceTextInFooter(document, footerMap);

        log.info("Export steps: Export finished");
        return document;
    }

    private void preSection(Dmp dmp, Map<String, String> replacements, SimpleDateFormat formatter) {
/*
        //mapping general information
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
            String contactAffiliation = "TU Wien";
            String identifierType = "";
            String identifierID = "";

            if (dmp.getContact().getFirstName() != null && dmp.getContact().getFirstName() != null)
                contactName = dmp.getContact().getFirstName() + " " + dmp.getContact().getLastName();
            if (dmp.getContact().getMbox() != null)
                contactMail = dmp.getContact().getMbox();
            //TO DO: Create mapping for multiple identifier type
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
            map.put("[contactaffiliation]", contactAffiliation);
            map.put("[contactror]", "");
        }
        */

        //mapping general information
        System.out.println("test1");
        System.out.println(replacements);
        addReplacement(replacements,"[projectname]", dmp.getProject().getTitle());
        System.out.println("test2");
        addReplacement(replacements,"[startdate]", formatter.format(dmp.getProject().getStart()));
        addReplacement(replacements,"[enddate]", formatter.format(dmp.getProject().getEnd()));
        addReplacement(replacements,"[grantid]", dmp.getProject().getFunding().getGrantIdentifier().getIdentifier());
        addReplacement(replacements,"[datever1]", formatter.format(dmp.getCreated()));

        //mapping contact information
        //TO DO: add affiliation and ROR (currently not stored in TISS)
        //Now affiliation assigned manually with TU Wien

        System.out.println("test2");

        String contactName = "";
        String contactMail = "";
        String contactId = "";
        String contactAffiliation = "TU Wien";
        String identifierType = "";
        String identifierID = "";



        if (dmp.getContact() != null)
        {
            contactName = dmp.getContact().getFirstName() + " " + dmp.getContact().getLastName();
            contactMail = dmp.getContact().getMbox();
            //TO DO: Create mapping for multiple identifier type
            contactId = "";
            if (dmp.getContact().getPersonIdentifier() != null) {
                identifierID = dmp.getContact().getPersonIdentifier().getIdentifier();
                identifierType = "";
                if (dmp.getContact().getPersonIdentifier().getIdentifierType().toString().equals("orcid")) {
                    identifierType = "ORCID iD: ";
                }
                contactId = identifierType + identifierID;
            }

        }

        System.out.println("test3");

        addReplacement(replacements,"[contactname]", contactName);
        addReplacement(replacements,"[contactmail]", contactMail);
        addReplacement(replacements,"[contactid]", contactId);
        addReplacement(replacements,"[contactaffiliation]", contactAffiliation);
        addReplacement(replacements,"[contactror]", "");

        System.out.println("test3");

        //checkline

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
            replacements.put("[contributors]", contributorValue);
        }
        else {
            replacements.put("[contributors]", "");
        }

        replacements.put("[coordinatorname]", coordinatorName);
        replacements.put("[coordinatormail]", coordinatorMail);
        replacements.put("[coordinatorid]", coordinatorId);
        replacements.put("[coordinatoraffiliation]", coordinatorAffiliation);
        replacements.put("[coordinatorror]", coordinatorRor);
    }

    //Number conversion for data size in section 1
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

    private void sectionOne(Dmp dmp, Map<String, String> map, List<Dataset> datasets, SimpleDateFormat formatter) {
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
                        if (distribution.getHost().getHostId().contains("r3")) { //repository
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
    }

    private void sectionTwo(Dmp dmp, Map<String, String> map) {

        if (dmp.getMetadata() != null) {
            if (dmp.getMetadata().equals("")) {
                map.put("[metadata]", "As there are no domain specific metadata standards applicable, we will provide a README file with an explanation of all values and terms used next to each file with data.");
            } else {
                map.put("[metadata]", "To help others identify, discover and reuse the data, " + dmp.getMetadata() + " will be used.");
            }
        }
    }

    private void sectionThree(Dmp dmp, Map<String, String> map, List<Dataset> datasets) {

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
        }

        if (dmp.getExternalStorageInfo() != null) {
            storageVar = storageVar.concat(";");
            storageVar = storageVar.concat("External storage will be used " + dmp.getExternalStorageInfo().toLowerCase(Locale.ROOT));
        }

        map.put("[storage]", storageVar);
    }

    private void sectionFour(Dmp dmp, Map<String, String> map, List<Dataset> datasets) {
//Section 4a: personal data
        log.info("personal data part");
        String personalData = "";
        if (dmp.getPersonalData() != null) {
            if (dmp.getPersonalData()) {
                String personalDataSentence = "In this project, we will process personal data (see section 1a). ";
                String personalDataset = "";
                String datasetSentence = "";
                List<String> personalDatasetList = new ArrayList<>();

                for (Dataset dataset: datasets) {
                    int idx = datasets.indexOf(dataset)+1;
                    if (dataset.getPersonalData()) {
                        personalDatasetList.add("P" + idx + " (" + dataset.getTitle() + ")");
                    }
                }

                if (personalDatasetList.size()>0) {
                    personalDataset = multipleVariable(personalDatasetList);
                    datasetSentence = " will containing personal data. ";
                }

                List<String> dataComplianceList = new ArrayList<>();
                String personalDataCompliance = "";

                if (!dmp.getPersonalDataCompliance().isEmpty()) {
                    for (EComplianceType personalCompliance : dmp.getPersonalDataCompliance()) {
                        dataComplianceList.add(personalCompliance.toString().replace("by ", ""));
                    }

                    personalDataCompliance = multipleVariable(dataComplianceList);
                }

                if (!personalDataCompliance.equals("")) {
                    personalData = personalDataSentence + personalDataset + datasetSentence + "To ensure compliance with data protection laws, " + personalDataCompliance + " will be used.";
                }
                else {
                    personalData = personalDataSentence + personalDataset + datasetSentence;
                }

            } else {
                personalData = "At this stage, it is not foreseen to process any personal data in the project. If this changes, advice will be sought from the data protection specialist at TU Wien (Verena Dolovai), and the DMP will be updated.";
            }
        }

        map.put("[personaldata]", personalData);

        //Section 4a: sensitive data
        log.info("sensitive data part");

        String sensitiveData = "";
        if (dmp.getSensitiveData() != null) {
            if (dmp.getSensitiveData()) {
                String sensitiveDataSentence = "In this project there will be sensitive data";
                String sensitiveDataset = "";
                String datasetSentence = "";
                String sensitiveDataMeasure = "";
                List<String> sensitiveDatasetList = new ArrayList<>();

                for (Dataset dataset: datasets) {
                    int idx = datasets.indexOf(dataset)+1;
                    if (dataset.getSensitiveData()) {
                        sensitiveDataset = "P" + idx + " (" + dataset.getTitle() + ")";
                        sensitiveDatasetList.add(sensitiveDataset);
                    }
                }

                if (sensitiveDatasetList.size() > 0) {
                    datasetSentence = " on dataset ";
                    sensitiveDataset = multipleVariable(sensitiveDatasetList) + ". ";
                }
                else {
                    datasetSentence = ". ";
                }

                List<String> dataSecurityList = new ArrayList<>();

                if (dmp.getSensitiveDataSecurity() != null) {
                    for (ESecurityMeasure securityMeasure : dmp.getSensitiveDataSecurity()) {
                        dataSecurityList.add(securityMeasure.toString());
                    }
                }

                if (dataSecurityList.size() > 1) {
                    sensitiveDataMeasure = "Additional security measures that will be used are " + multipleVariable(dataSecurityList) + ".";
                }

                if (dataSecurityList.size() == 1) {
                    sensitiveDataMeasure = "Additional security measures that will be used is " + multipleVariable(dataSecurityList) + ".";
                }

                if (dataSecurityList.size() == 0) {
                    sensitiveDataMeasure = "There are no additional security measures defined at the moment.";
                }

                sensitiveData = sensitiveDataSentence + datasetSentence + sensitiveDataset + sensitiveDataMeasure;

            } else {
                sensitiveData = "At this stage, it is not foreseen to process any sensitive data in the project. If this changes, advice will be sought from the data protection specialist at TU Wien (Verena Dolovai), and the DMP will be updated.";
            }
        }

        map.put("[sensitivedata]", sensitiveData);

        //Section 4b: legal restriction

        log.info("legal restriction part");

        String legalRestrictionComplete = "";
        String legalRestriction = "";
        List<String> legalRestrictionList = new ArrayList<>();

        if (dmp.getLegalRestrictions() != null) {
            if (dmp.getLegalRestrictions()) {
                String legalRestrictionSentence = "";
                String legalRestrictionDataset = "";
                List<String> datasetList = new ArrayList<>();

                for (Dataset dataset : datasets) {

                    int idx = datasets.indexOf(dataset) + 1;
                    if (dataset.getLegalRestrictions()) {
                        legalRestrictionDataset = "P" + idx + " (" + dataset.getTitle() + ")";
                        datasetList.add(legalRestrictionDataset);
                    }
                }

                if (datasetList.size() > 0) {
                    legalRestrictionDataset = ". The restrictions relate to datasets ";
                    legalRestrictionDataset = legalRestrictionDataset + multipleVariable(datasetList);
                }

                legalRestrictionSentence = "Legal restrictions on how data is processed and shared are specified in the data processing agreement";

                if (dmp.getLegalRestrictionsComment() == null) {
                    legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " and are based on trade secrets.";
                } else {
                    if (dmp.getLegalRestrictionsComment().equals("")) {
                        legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " and are based on trade secrets.";
                    } else {
                        legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " and are based on trade secrets. " + dmp.getLegalRestrictionsComment();
                    }
                }

                String affiliationRights = "";

                if (dmp.getContact().getAffiliation() != null) {
                    affiliationRights = dmp.getContact().getAffiliation() + " has rights to the produced data and controls access.";
                } else { //manually assign the organization
                    affiliationRights = "TU Wien has rights to the produced data and controls access.";
                }

                legalRestrictionList.add(legalRestriction);
                legalRestrictionList.add(affiliationRights);

                legalRestrictionComplete = String.join(";", legalRestrictionList);

                if (legalRestrictionComplete.charAt(legalRestrictionComplete.length() - 1) != '.')
                    legalRestrictionComplete = legalRestrictionComplete + ".";

                if (legalRestrictionComplete.charAt(legalRestrictionComplete.length()-1)!='.')
                    legalRestrictionComplete = legalRestrictionComplete + ".";
            }
            else {
                legalRestrictionComplete = "There are no legal restrictions on the processing and disclosure of our data.";
            }
        }

        map.put("[legalrestriction]", legalRestrictionComplete);

        //Section 4c: ethical issues

        log.info("ethical part");

        String ethicalIssues = "";
        String ethicalStatement = "";
        String otherEthicalIssues = "";
        String committeeReviewed = "";

        if (dmp.getHumanParticipants() != null) {
            if (dmp.getHumanParticipants()) {
                ethicalStatement = "This project will involve human participants. ";
            }
        }

        if (dmp.getEthicalIssuesExist() != null) {
            if (dmp.getEthicalIssuesExist()) {
                otherEthicalIssues = "There are other ethical issues associated with this research. ";
            }
        }

        if (dmp.getCommitteeReviewed() != null) {
            if (dmp.getCommitteeReviewed()) {
                committeeReviewed = "The research plan of the project was reviewed by an ethics committee / the TU Wien Pilot Research Ethics Committee / a similar body. ";
            }
            else {
                committeeReviewed = "The research has not been reviewed yet by any ethics committee. ";
            }
        }

        String ethicalSentence = "Ethical issues in the project have been identified and discussed with the Research Ethics Coordinator at TU Wien (https://www.tuwien.at/en/research/rti-support/research-ethics/). " +
                "They are described in detail in separate documents.";

        ethicalIssues = ethicalStatement + otherEthicalIssues + committeeReviewed;

        if (ethicalIssues != "") {

            ethicalIssues = ethicalSentence + ethicalIssues;

            if (ethicalIssues.charAt(ethicalIssues.length()-1) == ' ')
                ethicalIssues = ethicalIssues.substring(0,ethicalIssues.length()-1);

            if (ethicalIssues.charAt(ethicalIssues.length()-1) != '.')
                ethicalIssues = ethicalIssues + ".";
        } else {
            ethicalIssues = "No particular ethical issue is foreseen with the data to be used or produced by the project. This section will be updated if issues arise.";
        }

        map.put("[ethicalissues]", ethicalIssues);    }

    private void sectionFive(Dmp dmp, Map<String, String> map) {

        String targetAudience = "";
        String tools = "";

        if (dmp.getTargetAudience() != null)
            targetAudience = dmp.getTargetAudience();

        if (dmp.getTools() != null)
            tools = dmp.getTools();

        map.put("[targetaudience]", targetAudience);
        map.put("[tools]", tools);

    }

    private void sectionSix(Dmp dmp, Map<String, String> map, List<Cost> costList) {

        String costs = "";
        String costTitle = "";
        String costType = "";
        String costDescription = "";
        String costCurrency = "";
        String costValue = "";
        String costCurrencyTotal = "";

        if (dmp.getCostsExist() != null && dmp.getCostsExist()) {
            costs = "There are costs dedicated to data management and ensuring that data will be FAIR as outlined below.";
        }
        else {
            costs = "There are no costs dedicated to data management and ensuring that data will be FAIR.";
            map.put("[cost1title]", costTitle);
            map.put("[cost1type]", costType);
            map.put("[cost1desc]", costDescription);
            map.put("[cost1currency]", costCurrency);
            map.put("[cost1value]", costValue);
            map.put("[costcurrency]", "");
            map.put("[costtotal]", "");
        }

        map.put("[costs]", costs);

        //mapping cost information
        Float totalCost = 0f;

        for (Cost cost : costList) {
            int idx = costList.indexOf(cost) + 1;
            String docVar1 = "[cost" + idx + "title]";
            String docVar2 = "[cost" + idx + "type]";
            String docVar3 = "[cost" + idx + "desc]";
            String docVar4 = "[cost" + idx + "currency]";
            String docVar5 = "[cost" + idx + "value]";

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
                costValue = NumberFormat.getNumberInstance(Locale.GERMAN).format(cost.getValue());
                totalCost = totalCost + cost.getValue();
            }

            map.put(docVar1, costTitle);
            map.put(docVar2, costType);
            map.put(docVar3, costDescription);
            map.put(docVar4, costCurrency);
            map.put(docVar5, costValue);
        }

        map.put("[costtotal]", NumberFormat.getNumberInstance(Locale.GERMAN).format(totalCost));
    }

    private void tableContent(List<XWPFParagraph> xwpfParagraphs, Map<String, String> map, List<XWPFTable> tables, List<Dataset> datasets, List<Cost> costList, SimpleDateFormat formatter) {
        for (XWPFTable xwpfTable : tables) {
            if (xwpfTable.getRow(1) != null) {

                //dynamic table rows code for dataset (1a)
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1name]")) {

                    if (datasets.size() > 1) {
                        for (int i = 2; i < datasets.size() + 1; i++) {

                            XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                            XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                            try {
                                newRow = insertNewTableRow(sourceTableRow, i);
                            }
                            catch (Exception e) {
                            }

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
                            XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                            try {
                                newRow = insertNewTableRow(sourceTableRow, i);
                            }
                            catch (Exception e) {
                            }

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
                            XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                            try {
                                newRow = insertNewTableRow(sourceTableRow, i);
                            }
                            catch (Exception e) {
                            }

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
                            XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                            try {
                                newRow = insertNewTableRow(sourceTableRow, i);
                            }
                            catch (Exception e) {
                            }

                            ArrayList<String> docVar = new ArrayList<String>();
                            docVar.add(costList.get(i - 1).getTitle());
                            if (costList.get(i - 1).getType() != null)
                                docVar.add(costList.get(i - 1).getType().toString());
                            else
                                docVar.add("");
                            docVar.add(costList.get(i - 1).getDescription());
                            docVar.add(costList.get(i - 1).getCurrencyCode());
                            if (costList.get(i - 1).getValue() != null)
                                docVar.add(costList.get(i - 1).getValue().toString());
                            else
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
    }

}

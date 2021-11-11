package at.ac.tuwien.damap.conversion;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectMemberDO;
import at.ac.tuwien.damap.rest.projects.ProjectService;
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

    @Inject
    ProjectService projectService;

    public XWPFDocument getFWFTemplate(long dmpId) throws Exception {

        //Loading a template file in resources folder
        String template = "template/template.docx";
        ClassLoader classLoader = getClass().getClassLoader();

        //Extract document using Apache POI https://poi.apache.org/
        XWPFDocument document = new XWPFDocument(classLoader.getResourceAsStream(template));
        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
        List<XWPFTable> tables = document.getTables();

        //Loading data related to the project from database
        Dmp dmp = dmpRepo.findById(dmpId);
        List<Dataset> datasets = dmp.getDatasetList();
        List<Cost> costList = dmp.getCosts();

        //Convert the date for readable format for the document
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //List of document variables mapping
        Map<String, String> map = new HashMap<>();

        //Pre Section including general information from the project,
        // e.g. project title, coordinator, contact person, project and grant number.
        preSection(dmp, map, formatter);

        //Section 1 contains the dataset information table and how data is generated or used
        sectionOne(dmp, map, datasets, formatter);

        //Section 2 contains about the documentation and data quality including versioning and used metadata.
        sectionTwo(dmp, map);

        //Section 3 contains storage and backup that will be used for the data in the research
        // including the data access and sensitive aspect.
        sectionThree(dmp, map, datasets);

        //Section 4 contains legal and ethical requirements.
        sectionFour(dmp, map, datasets);

        //Section 5 contains information about data publication and long term preservation.
        sectionFive(dmp, map);

        //Section 6 contains resources and cost information if necessary.
        sectionSix(dmp, map, costList);

        //variables replacement
        replaceInParagraphs(xwpfParagraphs, map);

        //Dynamic table in all sections will be added from row number two until the end of data list.
        //TO DO: combine the function with the first row generation to avoid double code of similar modification.
        tableContent(dmp, xwpfParagraphs, map, tables, datasets, costList, formatter);

        return document;
    }

    private void preSection(Dmp dmp, Map<String, String> map, SimpleDateFormat formatter) {
        //mapping general information
        if (dmp.getProject() != null) {
            List<String> fundingItems = new ArrayList<>();

            //variable project name
            if (dmp.getProject().getTitle() != null)
                map.put("[projectname]", dmp.getProject().getTitle());
            //variable project acronym from API
            if (projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym() != null)
                map.put("[acronym]", projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym());
            //variable project start date
            if (dmp.getProject().getStart() != null)
                map.put("[startdate]", formatter.format(dmp.getProject().getStart()));
            //variable project end date
            if (dmp.getProject().getEnd() != null)
                map.put("[enddate]", formatter.format(dmp.getProject().getEnd()));
            //add funding name to funding item variables
            if (projectService.getProjectDetails(dmp.getProject().getUniversityId()).getFunding().getFundingName() != null)
                fundingItems.add(projectService.getProjectDetails(dmp.getProject().getUniversityId()).getFunding().getFundingName());
            //add funding program to funding item variables
            if (projectService.getProjectDetails(dmp.getProject().getUniversityId()).getFunding().getFundingProgram() != null)
                fundingItems.add(projectService.getProjectDetails(dmp.getProject().getUniversityId()).getFunding().getFundingProgram());
            //add grant number to funding item variables
            if (dmp.getProject().getFunding().getGrantIdentifier().getIdentifier() != null)
                fundingItems.add(dmp.getProject().getFunding().getGrantIdentifier().getIdentifier());
            //variable project funding, combination from funding item variables
            if (!fundingItems.isEmpty()) {
                map.put("[grantid]", multipleVariable(fundingItems));
            }
            else {
                map.put("[grantid]", "");
            }
            //variable project ID
            if (dmp.getProject().getUniversityId() != null)
                map.put("[projectid]", dmp.getProject().getUniversityId());
        }

        //variable dmp date version
        if (dmp.getCreated() != null) {
            map.put("[datever1]", formatter.format(dmp.getCreated()));
        }

        //mapping contact information
        if (dmp.getContact() != null) {
            List<String> contactItems = new ArrayList<>();
            String contactName = "";
            String contactMail = "";
            String contactId = "";
            String contactIdentifierType = "";
            String contactIdentifierId = "";
            String contactAffiliation = "";
            String contactAffiliationId = "";
            String contactAffiliationIdentifierType = "";
            String contactAffiliationIdentifierId = "";

            if (dmp.getContact().getFirstName() != null && dmp.getContact().getLastName() != null) {
                contactName = dmp.getContact().getFirstName() + " " + dmp.getContact().getLastName();
                contactItems.add(contactName);
            }

            if (dmp.getContact().getMbox() != null) {
                contactMail = dmp.getContact().getMbox();
                contactItems.add(contactMail);
            }

            if (dmp.getContact().getPersonIdentifier() != null) {
                contactIdentifierId = dmp.getContact().getPersonIdentifier().getIdentifier();
                if (dmp.getContact().getPersonIdentifier().getIdentifierType().toString().equals("orcid")) {
                    contactIdentifierType = "ORCID iD: ";
                    contactId = contactIdentifierType + contactIdentifierId;
                    contactItems.add(contactId);
                }
                if (dmp.getContact().getPersonIdentifier().getIdentifierType().toString().equals("ror")) {
                    contactIdentifierType = "ROR: ";
                    contactId = contactIdentifierType + contactIdentifierId;
                    contactItems.add(contactId);
                }
            }

            if (dmp.getContact().getAffiliation() != null) {
                contactAffiliation = dmp.getContact().getAffiliation();
                contactItems.add(contactAffiliation);
            }

            if (dmp.getContact().getAffiliationId() != null) {
                contactAffiliationIdentifierId = dmp.getContact().getAffiliationId().getIdentifier();
                if (dmp.getContact().getAffiliationId().getIdentifierType().toString().equals("ror")) {
                    contactAffiliationIdentifierType = "ROR: ";
                    contactAffiliationId = contactAffiliationIdentifierType + contactAffiliationIdentifierId;
                    contactItems.add(contactAffiliationId);
                }
            }

            if (!contactItems.isEmpty()) {
                map.put("[contact]", multipleVariable(contactItems));
            }
            else {
                map.put("[contact]", "");
            }
        }

        //mapping project coordinator information
        List<String> coordinatorProperties = new ArrayList<>();
        String coordinatorIdentifierId = "";
        String coordinatorAffiliationIdentifierId = "";

        List<ProjectMemberDO> projectMember = projectService.getProjectStaff(dmp.getProject().getUniversityId());

        if (!projectMember.isEmpty()) {
            for (ProjectMemberDO member : projectMember) {
                if (member.isProjectLeader()) {
                    if (member.getPerson().getFirstName() != null && member.getPerson().getLastName() != null)
                        coordinatorProperties.add(member.getPerson().getFirstName() + " " + member.getPerson().getLastName());

                    if (member.getPerson().getMbox() != null)
                        coordinatorProperties.add(member.getPerson().getMbox());

                    if (member.getPerson().getPersonId() != null) {
                        coordinatorIdentifierId = member.getPerson().getPersonId().getIdentifier();

                        if (member.getPerson().getPersonId().getType().toString().equals("orcid")) {
                            String coordinatorIdentifierType = "ORCID iD: ";
                            String coordinatorId = coordinatorIdentifierType + coordinatorIdentifierId;
                            coordinatorProperties.add(coordinatorId);
                        }

                        if (member.getPerson().getPersonId().getType().toString().equals("ror")) {
                            String coordinatorIdentifierType = "ROR: ";
                            String coordinatorId = coordinatorIdentifierType + coordinatorIdentifierId;
                            coordinatorProperties.add(coordinatorId);
                        }
                    }

                    if (member.getPerson().getAffiliation() != null)
                        coordinatorProperties.add(member.getPerson().getAffiliation());

                    if (member.getPerson().getAffiliationId() != null) {
                        coordinatorAffiliationIdentifierId = member.getPerson().getAffiliationId().getIdentifier();

                        if (member.getPerson().getAffiliationId().getType().toString().equals("ror")) {
                            String coordinatorAffiliationIdentifierType = "ROR: ";
                            String coordinatorAffiliationId = coordinatorAffiliationIdentifierType + coordinatorAffiliationIdentifierId;
                            coordinatorProperties.add(coordinatorAffiliationId);
                        }
                    }
                }
            }
        }

        //mapping contributor information

        if (dmp.getContributorList() != null) {
            String contributorPerson = "";

            List<Contributor> contributors = dmp.getContributorList();
            List<String> contributorList = new ArrayList<>();

            if (!contributors.isEmpty()) {
                for(Contributor contributor : contributors) {
                    List<String> contributorProperties = new ArrayList<>();
                    String contributorName = "";
                    String contributorMail = "";
                    String contributorId = "";
                    String contributorIdentifierType = "";
                    String contributorIdentifierId = "";
                    String contributorRole = "";
                    String contributorAffiliation = "";
                    String contributorAffiliationId = "";
                    String contributorAffiliationIdentifierType = "";
                    String contributorAffiliationIdentifierId = "";

                    if (contributor.getContributor().getFirstName() != null && contributor.getContributor().getLastName() != null) {
                        contributorName = contributor.getContributor().getFirstName() + " " + contributor.getContributor().getLastName();
                        contributorProperties.add(contributorName);
                    }

                    if (contributor.getContributor().getMbox() != null) {
                        contributorMail = contributor.getContributor().getMbox();
                        contributorProperties.add(contributorMail);
                    }

                    if (contributor.getContributor().getPersonIdentifier() != null) {
                        contributorIdentifierId = contributor.getContributor().getPersonIdentifier().getIdentifier();
                        if (contributor.getContributor().getPersonIdentifier().getIdentifierType().toString().equals("orcid")) {
                            contributorIdentifierType = "ORCID iD: ";
                            contributorId = contributorIdentifierType + contributorIdentifierId;
                            contributorProperties.add(contributorId);
                        }
                        if (contributor.getContributor().getPersonIdentifier().getIdentifierType().toString().equals("ror")) {
                            contributorIdentifierType = "ROR: ";
                            contributorId = contributorIdentifierType + contributorIdentifierId;
                            contributorProperties.add(contributorId);
                        }
                    }

                    if (contributor.getContributor().getAffiliation() != null) {
                        contributorAffiliation = contributor.getContributor().getAffiliation();
                        contributorProperties.add(contributorAffiliation);
                    }

                    if (contributor.getContributor().getAffiliationId() != null) {
                        contributorAffiliationIdentifierId = contributor.getContributor().getAffiliationId().getIdentifier();
                        if (contributor.getContributor().getAffiliationId().getIdentifierType().toString().equals("ror")) {
                            contributorAffiliationIdentifierType = "ROR: ";
                            contributorAffiliationId = contributorAffiliationIdentifierType + contributorAffiliationIdentifierId;
                            contributorProperties.add(contributorAffiliationId);
                        }
                    }

                    if (contributor.getContributorRole() != null) {
                        contributorRole = contributor.getContributorRole().getRole();
                        contributorProperties.add(contributorRole);
                    }

                    contributorPerson = multipleVariable(contributorProperties);
                    contributorList.add(contributorPerson);
                }
            }

            map.put("[contributors]", String.join(";", contributorList));
        }
        else {
            map.put("[contributors]", "");
        }

        map.put("[coordinator]", multipleVariable(coordinatorProperties));
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
        char[] value = new char[digits + 4];

        for(int i = 0; i < digits; i++) {
            value[i] = string.charAt(i);
        }
        int valueLength = digits;
        // Can and should we add a decimal point and an additional number?
        if(digits == 1 && string.charAt(1) != '0') {
            value[valueLength++] = '.';
            value[valueLength++] = string.charAt(1);
        }
        value[valueLength++] = ' ';
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
            String docVar11 =  "[dataset" + idx + "period]";
            String docVar12 = "[dataset" + idx + "selectedaccess]";
            String docVar13 = "[dataset" + idx + "allaccess]";
            String docVar14 = "[dataset" + idx + "publicaccess]";

            String datasetName = "";
            String datasetType = "";
            String datasetFormat = "";
            String datasetVol = "";
            String datasetLicense = "";
            String datasetPubdate = "";
            String datasetRepo = "";
            String datasetStorage = "";
            String datasetAccess = "";
            String datasetSensitive = "";
            String datasetRestriction = "";
            String datasetPeriod = "";
            String datasetSelectedAccess = "";
            String datasetAllAccess = "";
            String datasetPublicAccess = "";

            if (dataset.getTitle() != null)
                datasetName = dataset.getTitle();
            if (dataset.getType() != null)
                datasetType = String.format(dataset.getType()).toLowerCase().replace('_',' ');
            if (dataset.getSize() != null)
                datasetVol = format(dataset.getSize())+"B";
            if (dataset.getLicense() != null) {
                switch (dataset.getLicense()) {
                    case "https://creativecommons.org/licenses/by/4.0/":
                        datasetLicense = "CC BY 4.0";
                        break;
                    case "https://creativecommons.org/publicdomain/zero/1.0/":
                        datasetLicense = "CC ZERO 1.0";
                        break;
                    case "https://opendatacommons.org/licenses/pddl/summary/":
                        datasetLicense = "PDDL";
                        break;
                    case "https://opendatacommons.org/licenses/by/summary/":
                        datasetLicense = "ODC BY";
                        break;
                    case "https://creativecommons.org/publicdomain/mark/1.0/":
                        datasetLicense = "PD";
                        break;
                    default:
                        datasetLicense = "CC BY";
                        break;
                }
            }
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
                    datasetSensitive = "no";
                }
            }
            if (dataset.getLegalRestrictions() != null) {
                if (dataset.getLegalRestrictions()) {
                    datasetRestriction = dmp.getLegalRestrictionsComment();
                }
            }

            if (dataset.getSelectedProjectMembersAccess() != null) {
                datasetSelectedAccess = dataset.getSelectedProjectMembersAccess().toString().toLowerCase();
            }

            if (dataset.getOtherProjectMembersAccess() != null) {
                datasetAllAccess = dataset.getSelectedProjectMembersAccess().toString().toLowerCase();
            }

            if (dataset.getPublicAccess() != null) {
                datasetPublicAccess = dataset.getSelectedProjectMembersAccess().toString().toLowerCase();
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
            map.put(docVar11, datasetPeriod);
            map.put(docVar12, datasetSelectedAccess);
            map.put(docVar13, datasetAllAccess);
            map.put(docVar14, datasetPublicAccess);
        }

        if (datasets.size() == 0) {
            map.put("P1", "");
            map.put("[dataset1name]", "");
            map.put("[dataset1type]", "");
            map.put("[dataset1format]", "");
            map.put("[dataset1vol]", "");
            map.put("[dataset1license]", "");
            map.put("[dataset1pubdate]", "");
            map.put("[dataset1repo]", "");
            map.put("[dataset1access]", "");
            map.put("[dataset1sensitive]", "");
            map.put("[dataset1restriction]", "");
            map.put("[dataset1period]", "");
            map.put("[dataset1selectedaccess]", "");
            map.put("[dataset1allaccess]", "");
            map.put("[dataset1publicaccess]", "");
        }

        if (dmp.getDataGeneration() != null)
            map.put("[datageneration]", dmp.getDataGeneration());
    }

    private void sectionTwo(Dmp dmp, Map<String, String> map) {

        String metadata = "";

        if (dmp.getMetadata() == null) {
            map.put("[metadata]", "As there are no domain specific metadata standards applicable, we will provide a README file with an explanation of all values and terms used next to each file with data.");
        }
        else {
            if (dmp.getMetadata().equals("")) {
                map.put("[metadata]", "As there are no domain specific metadata standards applicable, we will provide a README file with an explanation of all values and terms used next to each file with data.");
            }
            else {
                metadata = dmp.getMetadata().toLowerCase();
                if (metadata.charAt(metadata.length()-1)!='.') {
                    metadata = metadata + '.';
                }
                map.put("[metadata]", "To help others identify, discover and reuse the data, " + metadata);
            }
        }
    }

    private void sectionThree(Dmp dmp, Map<String, String> map, List<Dataset> datasets) {

        List<Host> hostList = dmp.getHostList();
        String storageVar = "";
        String storageDescription = "";

        if (!hostList.isEmpty()) {
            for (Host host: hostList) {
                List<Distribution> distributions = host.getDistributionList();
                String hostVar = "";
                String distVar = "";

                if (host.getTitle() != null) {
                    hostVar = host.getTitle();
                    if (host.getTitle().equals("TUfiles")) {
                        storageDescription = ", a central and readily available network drive with daily backups and regular snapshots provided by TU.it. " +
                                "TUfiles is suitable for storing data with moderate access requirements, but high availability demands that allows full control of allocating authorisations. " +
                                "Only authorized staff members will have access.";
                    }
                    if (host.getTitle().equals("Server Housing")) {
                        storageDescription = ". The server is housed in a dedicated TU.it server room with limited access and operated by our institute.";
                    }
                    if (host.getTitle().equals("TUproCloud")) {
                        storageDescription = ", a sync&share service for projects provided by TU.it. " +
                                "Only authorized staff members and project partners will have access to the TUproCloud folders. " +
                                "Deleted files can be recovered within 180 days by using the bin function.";
                    }
                    if (host.getTitle().equals("TUhost")) {
                        storageDescription = ", the central and highly available TU.it virtualisation platform, hosted on VMware ESXi. Hardware. " +
                                "Storage and backup will be provided by TU.it and our institute will be responsible for the server operation.";
                    }
                    if (host.getTitle().equals("TUgitLab")) {
                        storageDescription = ", an application for managing repositories based on Git provided and managed by TU.it. " +
                                "Our institute’s administrators will manage GitLab groups, assign project permissions, and assign external project partners as additional GitLab users. " +
                                "This service is highly available and scalable on the Kubernetes platform.";
                    }
                }

                for (Distribution dist: distributions) {
                    int idx = datasets.indexOf(dist.getDataset())+1;
                    distVar = distVar + "P" + idx + " (" + dist.getDataset().getTitle() + ")";
                    if (distributions.indexOf(dist)+1 < distributions.size())
                        distVar = distVar + ", ";
                }

                if (host.getHostId() != null) {
                    if (!host.getHostId().contains("r3")) { //only write information related to the storage, repository will be written in section 5
                        if (!distVar.equals(""))
                            storageVar = storageVar.concat(distVar + " will be stored in " + hostVar + storageDescription);
                    }
                }
                else { //case for external storage, will have null host Id
                    if (!distVar.equals("")) {
                        storageVar = storageVar.concat(distVar + " will be stored in " + hostVar + ".");
                        if (dmp.getExternalStorageInfo() != null && !dmp.getExternalStorageInfo().equals("")) {
                            storageVar = storageVar.concat(" External storage will be used because " + dmp.getExternalStorageInfo().toLowerCase());
                        }
                    }
                }

                if (hostList.indexOf(host)+1 < hostList.size() && !host.getHostId().contains("r3"))
                    storageVar = storageVar.concat(";");
            }
        }

        map.put("[storage]", storageVar);
    }

    private void sectionFour(Dmp dmp, Map<String, String> map, List<Dataset> datasets) {
        //Section 4a: personal data
        String personalData;
        if (dmp.getPersonalData()) {
            String personalDataSentence = "In this project, we will process personal data (see section 1a). ";
            String personalDataset = "";
            String datasetSentence = "";
            List<String> datasetList = new ArrayList<>();

            for (Dataset dataset: datasets) {

                int idx = datasets.indexOf(dataset)+1;
                if (dataset.getPersonalData()) {
                    datasetList.add("P" + idx + " (" + dataset.getTitle() + ")");
                }
            }

            if (datasetList.size()>0) {
                personalDataset = multipleVariable(datasetList);
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
                    sensitiveDataset = "P" + idx + " (" + dataset.getTitle() + ")";
                    datasetList.add(sensitiveDataset);
                }
            }

            if (datasetList.size()>0) {
                sensitiveDataset = String.join(", ", datasetList);
                datasetSentence = " will containing sensitive data. ";
            }

            if (dmp.getSensitiveDataSecurity() != null && !dmp.getSensitiveDataSecurity().equals(""))
                sensitiveData = sensitiveDataSentence + sensitiveDataset + datasetSentence + "To ensure that the dataset containing sensitive data stored and transferred safe, " + dmp.getSensitiveDataSecurity().toLowerCase() + " will be taken.";
            else
                sensitiveData = sensitiveDataSentence + sensitiveDataset + datasetSentence;

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
                    legalRestrictionDataset = "P" + idx + " (" + dataset.getTitle() + ")";
                    datasetList.add(legalRestrictionDataset);
                }
            }

            if (datasetList.size()>0) {
                if (datasetList.size()==2) {
                    legalRestrictionDataset = String.join(" and ", datasetList);
                }
                else {
                    legalRestrictionDataset = multipleVariable(datasetList);
                }
                legalRestrictionSentence = "Legal restrictions on how data is processed and shared are specified in the data processing agreement. The restrictions relate to datasets ";
            }

            if (dmp.getLegalRestrictionsComment() == null) {
                legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " and are based on trade secrets.";
            }
            else {
                if (dmp.getLegalRestrictionsComment().equals("")) {
                legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " and are based on trade secrets.";
                }
                else {
                    legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " and are based on trade secrets. " + dmp.getLegalRestrictionsComment();
                }
            }

            legalRestriction.concat(";");
            if (dmp.getContact().getAffiliation() != null) {
                legalRestriction.concat(dmp.getContact().getAffiliation() + " has rights to the produced data and controls access.");
            }
            else { //manually assign the organization
                legalRestriction.concat("TU Wien has rights to the produced data and controls access.");
            }

            if (legalRestriction.charAt(legalRestriction.length()-1)!='.')
                legalRestriction = legalRestriction + ".";
        }
        else {
            legalRestriction = "There are no legal restrictions on the processing and disclosure of our data.";
        }

        map.put("[legalrestriction]", legalRestriction);

        //Section 4c: ethical issues

        String ethicalIssues = "";
        if (dmp.getEthicalIssuesExist()) {
            String ethicalSentence = "Ethical issues in the project have been identified and discussed with the Research Ethics Coordinator at TU Wien (https://www.tuwien.at/en/research/rti-support/research-ethics/). " +
                    "They are described in detail in separate documents.";
            String ethicalComplianceStatement = "";

            if (dmp.getEthicalComplianceStatement() != null) {
                if (!dmp.getEthicalComplianceStatement().equals("")) {
                    ethicalComplianceStatement = " " + dmp.getEthicalComplianceStatement();
                }
            }

            if (dmp.getEthicsReport() == null) {
                ethicalIssues = ethicalSentence + ethicalComplianceStatement;
            }
            else {
                if (dmp.getEthicsReport().equals("")) {
                    ethicalIssues = ethicalSentence + ethicalComplianceStatement;
                }
                else {
                    ethicalIssues = ethicalSentence + ethicalComplianceStatement + " Relevant ethical guidelines in this project are " + dmp.getEthicsReport() + ".";
                }
            }

            if (ethicalIssues.charAt(ethicalIssues.length()-1) == ' ')
                ethicalIssues = ethicalIssues.substring(0,ethicalIssues.length()-1);

            if (ethicalIssues.charAt(ethicalIssues.length()-1) != '.')
                ethicalIssues = ethicalIssues + ".";

            ethicalIssues.concat(";");
            ethicalIssues.concat("The research plan of the project was reviewed by an ethics committee / the TU Wien Pilot Research Ethics Committee / a similar body.");
        } else {
            ethicalIssues = "No particular ethical issue is foreseen with the data to be used or produced by the project. This section will be updated if issues arise.";
        }
        map.put("[ethicalissues]", ethicalIssues);
    }

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

    private void tableContent(Dmp dmp, List<XWPFParagraph> xwpfParagraphs, Map<String, String> map, List<XWPFTable> tables, List<Dataset> datasets, List<Cost> costList, SimpleDateFormat formatter) {
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

                            if (datasets.get(i - 1).getTitle() != null) {
                                docVar.add(datasets.get(i - 1).getTitle());
                            }
                            else {
                                docVar.add("");
                            }

                            if (datasets.get(i-1).getType() != null) {
                                docVar.add(String.format(datasets.get(i - 1).getType()).toLowerCase().replace('_', ' '));
                            }
                            else {
                                docVar.add("");
                            }

                            //TODO: dataset format still not available
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

                //dynamic table for data access
                //table 3b
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1selectedaccess]")) {

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

                            if (datasets.get(i - 1).getSelectedProjectMembersAccess() != null) {
                                docVar.add(datasets.get(i - 1).getSelectedProjectMembersAccess().toString().toLowerCase());
                            }
                            else {
                                docVar.add("");
                            }

                            if (datasets.get(i - 1).getOtherProjectMembersAccess() != null) {
                                docVar.add(datasets.get(i - 1).getOtherProjectMembersAccess().toString().toLowerCase());
                            }
                            else {
                                docVar.add("");
                            }

                            if (datasets.get(i - 1).getPublicAccess() != null) {
                                docVar.add(datasets.get(i - 1).getPublicAccess().toString().toLowerCase());
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

                            if (datasets.get(i - 1).getDataAccess() != null) {
                                docVar.add(datasets.get(i - 1).getDataAccess().toString());
                            }
                            else {
                                docVar.add("");
                            }

                            if (datasets.get(i - 1).getLegalRestrictions() != null && datasets.get(i - 1).getLegalRestrictions()) {
                                docVar.add(dmp.getLegalRestrictionsComment());
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
                                switch (datasets.get(i - 1).getLicense()) {
                                    case "https://creativecommons.org/licenses/by/4.0/":
                                        docVar.add("CC BY 4.0");
                                        break;
                                    case "https://creativecommons.org/publicdomain/zero/1.0/":
                                        docVar.add("CC ZERO 1.0");
                                        break;
                                    case "https://opendatacommons.org/licenses/pddl/summary/":
                                        docVar.add("PDDL");
                                        break;
                                    case "https://opendatacommons.org/licenses/by/summary/":
                                        docVar.add("ODC BY");
                                        break;
                                    case "https://creativecommons.org/publicdomain/mark/1.0/":
                                        docVar.add("PD");
                                        break;
                                    default:
                                        docVar.add("");
                                        break;
                                }
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
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1repo]")) {

                    if (datasets.size() > 1) {
                        for (int i = 2; i < datasets.size() + 1; i++) {

                            XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                            XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                            try {
                                newRow = insertNewTableRow(sourceTableRow, i);
                            }
                            catch (Exception e) {
                            }

                            ArrayList<String> docVar = new ArrayList<>();
                            docVar.add("P" + i);
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
                                    docVar.add(multipleVariable(repositories));
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

                            ArrayList<String> docVar = new ArrayList<>();
                            docVar.add(costList.get(i - 1).getTitle());
                            if (costList.get(i - 1).getType() != null)
                                docVar.add(costList.get(i - 1).getType().toString());
                            else
                                docVar.add("");
                            docVar.add(costList.get(i - 1).getDescription());
                            docVar.add(costList.get(i - 1).getCurrencyCode());
                            if (costList.get(i - 1).getValue() != null)
                                docVar.add(NumberFormat.getNumberInstance(Locale.GERMAN).format(costList.get(i - 1).getValue()));
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

    private String multipleVariable(List<String> variableList) {
        return String.join(", ", variableList);
    }
}

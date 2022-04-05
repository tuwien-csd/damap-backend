package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.enums.ESecurityMeasure;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import at.ac.tuwien.damap.r3data.RepositoriesService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.NumberFormat;

import org.apache.poi.xwpf.usermodel.*;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@JBossLog
public class ExportScienceEuropeTemplate extends DocumentConversionService{
    @Inject
    ProjectService projectService;

    @Inject
    RepositoriesService repositoriesService;

    @Inject
    LoadResourceService loadResourceService;

    @Inject
    TemplateFileBrokerService templateFileBrokerService;

    public XWPFDocument exportTemplate(long dmpId) throws Exception {

        String startChar = "[";
        String endChar = "]";
        XWPFDocument document = loadTemplate(templateFileBrokerService.loadScienceEuropeTemplate(), startChar, endChar);

        Properties prop = templateFileBrokerService.getScienceEuropeTemplateResource();

        Map<String, String> map = new HashMap<>();
        Map<String, String> footerMap = new HashMap<>();

        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
        List<XWPFTable> tables = document.getTables();

        //Loading data related to the project from database
        Dmp dmp = dmpRepo.findById(dmpId);
        List<Dataset> datasets = dmp.getDatasetList();
        List<Cost> costList = dmp.getCosts();
        List<Dataset> closedDatasets = getClosedDatasets(datasets);

        //Convert the date for readable format for the document
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //First step of the export: create a mapping of variables and its desired replacement values

        //Pre Section including general information from the project,
        // e.g. project title, coordinator, contact person, project and grant number.
        log.info("Export steps: Pre section");
        preSection(dmp, map, footerMap, formatter);

        //Section 1 contains the dataset information table and how data is generated or used
        log.info("Export steps: Section 1");
        sectionOne(dmp, map, datasets, formatter);

        //Section 2 contains about the documentation and data quality including versioning and used metadata.
        log.info("Export steps: Section 2");
        sectionTwo(dmp, map, prop);

        //Section 3 contains storage and backup that will be used for the data in the research
        // including the data access and sensitive aspect.
        log.info("Export steps: Section 3");
        sectionThree(dmp, map, datasets, prop);

        //Section 4 contains legal and ethical requirements.
        log.info("Export steps: Section 4");
        sectionFour(dmp, map, datasets, prop);

        //Section 5 contains information about data publication and long term preservation.
        log.info("Export steps: Section 5");
        sectionFive(dmp, map, datasets, closedDatasets, prop);

        //Section 6 contains resources and cost information if necessary.
        log.info("Export steps: Section 6");
        sectionSix(dmp, map, costList, prop);

        //Second step of the export: variables replacement with a mapping reference that has been defined
        log.info("Export steps: Replace in paragraph");
        replaceInParagraphs(xwpfParagraphs, map);

        //Third step of the export: dynamic table in all sections will be added from row number two until the end of data list.
        //TO DO: combine the function with the first row generation to avoid double code of similar modification.
        log.info("Export steps: Replace in table");
        tableContent(dmp, xwpfParagraphs, map, tables, datasets, closedDatasets, costList, formatter);

        //Fourth step of the export: modify the content of the document's footer
        log.info("Export steps: Replace in footer");
        replaceTextInFooter(document, footerMap);

        log.info("Export steps: Export finished");
        return document;
    }

    //Variable replacements flow for each section start from here

    //Pre section variables replacement
    private void preSection(Dmp dmp, Map<String, String> replacements, Map<String, String> footerMap, SimpleDateFormat formatter) {

        //project member list
        ContributorDO projectCoordinator = null;

        //mapping general information
        if (dmp.getProject() != null) {
            Integer titleLength = (dmp.getProject().getTitle() == null) ? 0 : dmp.getProject().getTitle().length();
            Integer coverSpace = 0;
            String coverSpaceVar = "";

            //variable project name
            if (titleLength/25 > 2) //Title too long, need to be resized
                addReplacement(replacements, "[projectname]", dmp.getProject().getTitle() + "#oversize");
            else
                addReplacement(replacements, "[projectname]", dmp.getProject().getTitle());

            //handling space in the cover depends on the title length
            switch (titleLength/25) {
                case 0:
                case 4:
                    coverSpace = 2;
                    break;
                default:
                    coverSpace = 1;
                    break;
            }

            if (titleLength/25 < 6) {
                for (int i = 0; i < coverSpace; i++) {
                    coverSpaceVar = coverSpaceVar.concat(" ;");
                }
            }
            if (titleLength/25 < 3)
                coverSpaceVar = coverSpaceVar.concat(" ");

            addReplacement(replacements, "[coverspace]", coverSpaceVar);

            //variable project acronym from API
            addReplacement(replacements, "[acronym]", projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym());
            addReplacement(footerMap, "[acronym]", projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym());

            //variable project start date and end date
            addReplacement(replacements, "[startdate]", formatter.format(dmp.getProject().getStart()));
            addReplacement(replacements, "[enddate]", formatter.format(dmp.getProject().getEnd()));

            List<String> fundingItems = new ArrayList<>();

            //add funding program to funding item variables
            if (projectService.getProjectDetails(dmp.getProject().getUniversityId()).getFunding().getFundingProgram() != null)
                fundingItems.add(projectService.getProjectDetails(dmp.getProject().getUniversityId()).getFunding().getFundingProgram());
            //add grant number to funding item variables
            if (dmp.getProject().getFunding().getGrantIdentifier().getIdentifier() != null)
                fundingItems.add(dmp.getProject().getFunding().getGrantIdentifier().getIdentifier());
            //variable project funding, combination from funding item variables
            if (!fundingItems.isEmpty()) {
                addReplacement(replacements, "[grantid]", String.join(", ", fundingItems));
            }
            else {
                addReplacement(replacements, "[grantid]", "");
            }

            //variable project ID
            addReplacement(replacements, "[projectid]", dmp.getProject().getUniversityId());
            //get project member from the project ID
            if (dmp.getProject().getUniversityId() != null)
                projectCoordinator = projectService.getProjectLeader(dmp.getProject().getUniversityId());

            //variable dmp version
            Long dmpVersion = dmp.getVersion();
            addReplacement(footerMap, "[version]", "" + dmpVersion);
        }

        addReplacement(replacements,"[datever1]", dmp.getCreated());

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

            addReplacement(replacements, "[contact]", multipleVariable(contactItems));
        }

        //mapping project coordinator information
        List<String> coordinatorProperties = new ArrayList<>();
        String coordinatorIdentifierId = "";
        String coordinatorAffiliationIdentifierId = "";

        //mapping project coordinator information
        if (projectCoordinator != null) {
            if (projectCoordinator.getFirstName() != null && projectCoordinator.getLastName() != null)
                coordinatorProperties.add(projectCoordinator.getFirstName() + " " + projectCoordinator.getLastName());

            if (projectCoordinator.getMbox() != null)
                coordinatorProperties.add(projectCoordinator.getMbox());

            if (projectCoordinator.getPersonId() != null) {
                coordinatorIdentifierId = projectCoordinator.getPersonId().getIdentifier();

                if (projectCoordinator.getPersonId().getType().toString().equals("orcid")) {
                    String coordinatorIdentifierType = "ORCID iD: ";
                    String coordinatorId = coordinatorIdentifierType + coordinatorIdentifierId;
                    coordinatorProperties.add(coordinatorId);
                }
            }

            if (projectCoordinator.getAffiliation() != null)
                coordinatorProperties.add(projectCoordinator.getAffiliation());

            if (projectCoordinator.getAffiliationId() != null) {
                coordinatorAffiliationIdentifierId = projectCoordinator.getAffiliationId().getIdentifier();

                if (projectCoordinator.getAffiliationId().getType().toString().equals("ror")) {
                    String coordinatorAffiliationIdentifierType = "ROR: ";
                    String coordinatorAffiliationId = coordinatorAffiliationIdentifierType + coordinatorAffiliationIdentifierId;
                    coordinatorProperties.add(coordinatorAffiliationId);
                }
            }
        }

        addReplacement(replacements, "[coordinator]", multipleVariable(coordinatorProperties));

        //mapping contributor information
        List<String> contributorList = new ArrayList<>();

        if (dmp.getContributorList() != null) {
            String contributorPerson = "";

            List<Contributor> contributors = dmp.getContributorList();

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

                    if (contributor.getFirstName() != null && contributor.getLastName() != null) {
                        contributorName = contributor.getFirstName() + " " + contributor.getLastName();
                        contributorProperties.add(contributorName);
                    }

                    if (contributor.getMbox() != null) {
                        contributorMail = contributor.getMbox();
                        contributorProperties.add(contributorMail);
                    }

                    if (contributor.getPersonIdentifier() != null) {
                        contributorIdentifierId = contributor.getPersonIdentifier().getIdentifier();
                        if (contributor.getPersonIdentifier().getIdentifierType().toString().equals("orcid")) {
                            contributorIdentifierType = "ORCID iD: ";
                            contributorId = contributorIdentifierType + contributorIdentifierId;
                            contributorProperties.add(contributorId);
                        }
                    }

                    if (contributor.getAffiliation() != null) {
                        contributorAffiliation = contributor.getAffiliation();
                        contributorProperties.add(contributorAffiliation);
                    }

                    if (contributor.getAffiliationId() != null) {
                        contributorAffiliationIdentifierId = contributor.getAffiliationId().getIdentifier();
                        if (contributor.getAffiliationId().getIdentifierType().toString().equals("ror")) {
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
        }

        addReplacement(replacements, "[contributors]", String.join(";", contributorList));
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

    //Section 1 variables replacement
    private void sectionOne(Dmp dmp, Map<String, String> replacements, List<Dataset> datasets, SimpleDateFormat formatter) {
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

            addReplacement(replacements, docVar1, dataset.getTitle());

            //TODO: Move formatting (format, lowercase) to varargs for more efficiency and flexibility
            if (dataset.getType() != null)
                datasetType = String.format(dataset.getType()).toLowerCase().replace('_',' ');

            addReplacement(replacements, docVar2, datasetType);

            //TODO: Add format file in frontend
            addReplacement(replacements, docVar3, datasetFormat);

            if (dataset.getSize() != null)
                datasetVol = format(dataset.getSize())+"B";

            addReplacement(replacements, docVar4, datasetVol);

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

            addReplacement(replacements, docVar5, datasetLicense);

            addReplacement(replacements, docVar6, dataset.getStart());

            if (dataset.getDistributionList() != null){
                List<Distribution> distributions = dataset.getDistributionList();
                List<String> repositories = new ArrayList<>();
                List<String> storage = new ArrayList<>();

                for (Distribution distribution: distributions) {
                    if (Repository.class.isAssignableFrom(distribution.getHost().getClass())) { //repository
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

            addReplacement(replacements, docVar7, datasetRepo);

            addReplacement(replacements, docVar8, dataset.getDataAccess());

            if (dataset.getSensitiveData() != null) {
                if (dataset.getSensitiveData()) {
                    datasetSensitive = "yes";
                }
                else {
                    datasetSensitive = "no";
                }
            }

            addReplacement(replacements, docVar9, datasetSensitive);

            if (dataset.getLegalRestrictions() != null) {
                if (dataset.getLegalRestrictions()) {
                    if (dmp.getLegalRestrictionsComment() != null) {
                        datasetRestriction = dmp.getLegalRestrictionsComment();
                    }
                }
            }

            addReplacement(replacements, docVar10, datasetRestriction);

            if (dataset.getRetentionPeriod() != null) {
                datasetPeriod = dataset.getRetentionPeriod() + " years";
            }

            addReplacement(replacements, docVar11, datasetPeriod);

            if (dataset.getSelectedProjectMembersAccess() != null) {
                datasetSelectedAccess = dataset.getSelectedProjectMembersAccess().toString().toLowerCase();
            }

            addReplacement(replacements, docVar12, datasetSelectedAccess);

            if (dataset.getOtherProjectMembersAccess() != null) {
                datasetAllAccess = dataset.getOtherProjectMembersAccess().toString().toLowerCase();
            }

            addReplacement(replacements, docVar13, datasetAllAccess);

            if (dataset.getPublicAccess() != null) {
                datasetPublicAccess = dataset.getPublicAccess().toString().toLowerCase();
            }

            addReplacement(replacements, docVar14, datasetPublicAccess);
        }

        if (datasets.size() == 0) {
            addReplacement(replacements, "P1", "");
            addReplacement(replacements, "[dataset1name]", "");
            addReplacement(replacements, "[dataset1type]", "");
            addReplacement(replacements, "[dataset1format]", "");
            addReplacement(replacements, "[dataset1vol]", "");
            addReplacement(replacements, "[dataset1license]", "");
            addReplacement(replacements, "[dataset1pubdate]", "");
            addReplacement(replacements, "[dataset1repo]", "");
            addReplacement(replacements, "[dataset1access]", "");
            addReplacement(replacements, "[dataset1sensitive]", "");
            addReplacement(replacements, "[dataset1restriction]", "");
            addReplacement(replacements, "[dataset1period]", "");
            addReplacement(replacements, "[dataset1selectedaccess]", "");
            addReplacement(replacements, "[dataset1allaccess]", "");
            addReplacement(replacements, "[dataset1publicaccess]", "");
        }

        addReplacement(replacements, "[datageneration]", dmp.getDataGeneration());
    }

    //Section 2 variables replacement
    private void sectionTwo(Dmp dmp, Map<String, String> replacements, Properties prop) {

        String metadata = "";

        //TODO: alternative replacement in addReplacement method

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

    //Section 3 variables replacement
    private void sectionThree(Dmp dmp, Map<String, String> replacements, List<Dataset> datasets, Properties prop) {

        List<Host> hostList = dmp.getHostList();
        String storageVar = "";
        String storageDescription = "";

        if (!hostList.isEmpty()) {
            for (Host host: hostList) {
                List<Distribution> distributions = host.getDistributionList();
                String hostVar = "";
                String distVar = "";

                //TODO: automatic description for all storages

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

                if (Storage.class.isAssignableFrom(host.getClass())) { //only write information related to the storage, repository will be written in section 5
                    if (!distVar.equals(""))
                        storageVar = storageVar.concat(distVar + " " + loadResourceService.loadVariableFromResource(prop,"distributionStorage") + " " + hostVar + storageDescription);
                }
                else if (ExternalStorage.class.isAssignableFrom(host.getClass())) { //case for external storage, will have null host Id
                    if (!distVar.equals("")) {
                        storageVar = storageVar.concat(distVar + " " + loadResourceService.loadVariableFromResource(prop,"distributionStorage") + " " + hostVar + ".");
                        if (dmp.getExternalStorageInfo() != null && !dmp.getExternalStorageInfo().equals("")) {
                            storageVar = storageVar.concat(" " + loadResourceService.loadVariableFromResource(prop,"distributionExternal") + " " + dmp.getExternalStorageInfo().toLowerCase());
                        }
                    }
                }

                if (hostList.indexOf(host)+1 < hostList.size())
                    if (Storage.class.isAssignableFrom(host.getClass())) { //only write information related to the storage, repository will be written in section 5)
                        storageVar = storageVar.concat(";");
                    }
                    else if (ExternalStorage.class.isAssignableFrom(host.getClass())) { //case for external storage, will have null host Id
                        storageVar = storageVar.concat(";");
                    }
            }
        }

        addReplacement(replacements,"[storage]", storageVar);


        //Section 3b: sensitive data
        log.info("sensitive data part");

        String sensitiveData = "";
        if (dmp.getSensitiveData() != null) {
            if (dmp.getSensitiveData()) {
                String sensitiveDataSentence = loadResourceService.loadVariableFromResource(prop,"sensitive.avail");
                String sensitiveDataset = "";
                String datasetSentence = "";
                String sensitiveDataMeasure = "";
                String authorisedAccess = "";
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

                if (dataSecurityList.isEmpty()) {
                    sensitiveDataMeasure = loadResourceService.loadVariableFromResource(prop,"sensitiveMeasure.no");
                }
                else {
                    //security measurement size defined is/or usage
                    if (dataSecurityList.size() == 1) {
                        sensitiveDataMeasure = loadResourceService.loadVariableFromResource(prop,"sensitiveMeasure.no") + " " + multipleVariable(dataSecurityList) + " " + loadResourceService.loadVariableFromResource(prop,"sensitiveMeasure.singular");
                    } else {
                        sensitiveDataMeasure = loadResourceService.loadVariableFromResource(prop,"sensitiveMeasure.no") + " " + multipleVariable(dataSecurityList) + " " + loadResourceService.loadVariableFromResource(prop,"sensitiveMeasure.multiple");
                    }
                }

                if (dmp.getSensitiveDataAccess() != null) {
                    if (!dmp.getSensitiveDataAccess().isEmpty()) {
                        authorisedAccess = " " + loadResourceService.loadVariableFromResource(prop, "sensitiveAccess") + " " + dmp.getSensitiveDataAccess() + " " + loadResourceService.loadVariableFromResource(prop,"sensitiveAccess.avail");
                    }
                }

                sensitiveData = sensitiveDataSentence + datasetSentence + sensitiveDataset + sensitiveDataMeasure + authorisedAccess;

            } else {
                sensitiveData = loadResourceService.loadVariableFromResource(prop,"sensitive.no");
            }
        }

        addReplacement(replacements, "[sensitivedata]", sensitiveData);
    }

    //Section 4 variables replacement
    private void sectionFour(Dmp dmp, Map<String, String> replacements, List<Dataset> datasets, Properties prop) {
        //Section 4a: personal data
        log.info("personal data part");
        String personalData = "";
        if (dmp.getPersonalData() != null) {
            if (dmp.getPersonalData()) {
                String personalDataSentence = loadResourceService.loadVariableFromResource(prop,"personal.avail") + " ";
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
                    datasetSentence = " " + loadResourceService.loadVariableFromResource(prop,"personalDataset") + " ";
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
                    personalData = personalDataSentence + personalDataset + datasetSentence + loadResourceService.loadVariableFromResource(prop,"personalCompliance") + " " + personalDataCompliance + " " + loadResourceService.loadVariableFromResource(prop,"personalComplianceUsed");
                }
                else {
                    personalData = personalDataSentence + personalDataset + datasetSentence;
                }

            } else {
                personalData = loadResourceService.loadVariableFromResource(prop,"personal.no");
            }
        }

        addReplacement(replacements, "[personaldata]", personalData);

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
                    legalRestrictionDataset = loadResourceService.loadVariableFromResource(prop,"legalDataset") + " ";
                    legalRestrictionDataset = legalRestrictionDataset + multipleVariable(datasetList);
                }

                legalRestrictionSentence = loadResourceService.loadVariableFromResource(prop,"legal.avail");

                if (dmp.getLegalRestrictionsComment() == null) {
                    legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " " + loadResourceService.loadVariableFromResource(prop,"legalComment");
                } else {
                    if (dmp.getLegalRestrictionsComment().equals("")) {
                        legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " " + loadResourceService.loadVariableFromResource(prop,"legalComment");
                    } else {
                        legalRestriction = legalRestrictionSentence + legalRestrictionDataset + " " + loadResourceService.loadVariableFromResource(prop,"legalComment");
                    }
                }

                String affiliationRights = "";

                if (dmp.getContact().getAffiliation() != null) {
                    affiliationRights = dmp.getContact().getAffiliation() + " " + loadResourceService.loadVariableFromResource(prop,"legalRights.contact");
                } else { //manually assign the organization
                    affiliationRights = loadResourceService.loadVariableFromResource(prop,"legalRights.affiliation");
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
                legalRestrictionComplete = loadResourceService.loadVariableFromResource(prop,"legal.no");
            }
        }

        addReplacement(replacements, "[legalrestriction]", legalRestrictionComplete);

        //Section 4c: ethical issues

        log.info("ethical part");

        String ethicalIssues = "";
        String ethicalStatement = "";
        String otherEthicalIssues = "";
        String committeeReviewed = "";

        if (dmp.getHumanParticipants() != null) {
            if (dmp.getHumanParticipants()) {
                ethicalStatement = " " + loadResourceService.loadVariableFromResource(prop,"ethical.avail");
            }
        }

        if (dmp.getEthicalIssuesExist() != null) {
            if (dmp.getEthicalIssuesExist()) {
                otherEthicalIssues = " " + loadResourceService.loadVariableFromResource(prop,"ethicalOther");
            }
        }

        if (dmp.getCommitteeReviewed() != null) {
            if (dmp.getCommitteeReviewed()) {
                committeeReviewed = " " + loadResourceService.loadVariableFromResource(prop,"ethicalReviewed.avail");
            }
            else {
                committeeReviewed = " " + loadResourceService.loadVariableFromResource(prop,"ethicalReviewed.no");
            }
        }

        String ethicalSentence = loadResourceService.loadVariableFromResource(prop,"ethicalStatement");

        ethicalIssues = ethicalStatement + otherEthicalIssues + committeeReviewed;

        if (ethicalIssues != "") {

            ethicalIssues = ethicalSentence + ethicalIssues;

            if (ethicalIssues.charAt(ethicalIssues.length()-1) == ' ')
                ethicalIssues = ethicalIssues.substring(0,ethicalIssues.length()-1);

            if (ethicalIssues.charAt(ethicalIssues.length()-1) != '.')
                ethicalIssues = ethicalIssues + ".";
        } else {
            ethicalIssues = loadResourceService.loadVariableFromResource(prop,"ethical.no");
        }

        addReplacement(replacements, "[ethicalissues]", ethicalIssues);
    }

    //Section 5 variables replacement
    private void sectionFive(Dmp dmp, Map<String, String> replacements, List<Dataset> datasets, List<Dataset> closedDatasets, Properties prop) {

        String repoSentence = "";
        String repoInformation = "";
        String deleteDatasetTitle = "";
        Date deleteDatasetDate = null;
        String deleteDatasetReason = "";

        //TODO: all datasets storage related information should be moved here
        for (Dataset dataset : datasets) {
            if (dataset.getDistributionList() != null){
                List<Distribution> distributions = dataset.getDistributionList();
                List<String> repositories = new ArrayList<>();

                for (Distribution distribution: distributions) {
                    if (Repository.class.isAssignableFrom(distribution.getHost().getClass()))
                        repositories.add(repositoriesService.getDescription(((Repository) distribution.getHost()).getRepositoryId()) + " " + repositoriesService.getRepositoryURL(((Repository) distribution.getHost()).getRepositoryId()));
                }
                if (repositories.size() > 0) {
                    repoSentence = loadResourceService.loadVariableFromResource(prop, "repositories.avail");
                    repositories.add(0,repoSentence);
                    repoInformation = String.join("; ", repositories);
                }
            }
        }

        addReplacement(replacements, "[repoinformation]", repoInformation);

        addReplacement(replacements, "[targetaudience]", dmp.getTargetAudience());

        if (closedDatasets.size() > 0) {
            deleteDatasetTitle = closedDatasets.get(0).getTitle();
            deleteDatasetDate = closedDatasets.get(0).getDateOfDeletion();
            deleteDatasetReason = closedDatasets.get(0).getReasonForDeletion();
        }

        addReplacement(replacements, "[dataset1delete]", deleteDatasetTitle);
        addReplacement(replacements, "[delete1date]", deleteDatasetDate);
        addReplacement(replacements, "[delete1reason]", deleteDatasetReason);

        if (dmp.getTools() != null) {
            if (dmp.getTools() != "") {
                addReplacement(replacements, "[tools]", loadResourceService.loadVariableFromResource(prop, "tools.avail") + dmp.getTools());
            }
            else {
                addReplacement(replacements, "[tools]", loadResourceService.loadVariableFromResource(prop, "tools.no"));
            }
        }
        else {
            addReplacement(replacements, "[tools]", loadResourceService.loadVariableFromResource(prop, "tools.no"));
        }
    }

    //Section 6 variables replacement
    private void sectionSix(Dmp dmp, Map<String, String> replacements, List<Cost> costList, Properties prop) {

        String costs = "";
        String costTitle = "";
        String costType = "";
        String costDescription = "";
        String costCurrency = "";
        String costValue = "";
        String costCurrencyTotal = "";

        if (dmp.getCostsExist() != null) {
            if (dmp.getCostsExist()) {
                costs = loadResourceService.loadVariableFromResource(prop, "costs.avail");
            }
            else {
                costs = loadResourceService.loadVariableFromResource(prop, "costs.no");
                addReplacement(replacements, "[cost1title]", costTitle);
                addReplacement(replacements, "[cost1type]", costType);
                addReplacement(replacements, "[cost1desc]", costDescription);
                addReplacement(replacements, "[cost1currency]", costCurrency);
                addReplacement(replacements, "[cost1value]", costValue);
                addReplacement(replacements, "[costcurrency]", "");
                addReplacement(replacements, "[costtotal]", "");
            }
        }
        else {
            costs = loadResourceService.loadVariableFromResource(prop, "costs.no");
            addReplacement(replacements, "[cost1title]", costTitle);
            addReplacement(replacements, "[cost1type]", costType);
            addReplacement(replacements, "[cost1desc]", costDescription);
            addReplacement(replacements, "[cost1currency]", costCurrency);
            addReplacement(replacements, "[cost1value]", costValue);
            addReplacement(replacements, "[costcurrency]", "");
            addReplacement(replacements, "[costtotal]", "");
        }

        addReplacement(replacements, "[costs]", costs);

        //mapping cost information
        Float totalCost = 0f;

        for (Cost cost : costList) {
            int idx = costList.indexOf(cost) + 1;
            String docVar1 = "[cost" + idx + "title]";
            String docVar2 = "[cost" + idx + "type]";
            String docVar3 = "[cost" + idx + "desc]";
            String docVar4 = "[cost" + idx + "currency]";
            String docVar5 = "[cost" + idx + "value]";

            addReplacement(replacements, docVar1, cost.getTitle());
            addReplacement(replacements, docVar2, cost.getType());
            addReplacement(replacements, docVar3, cost.getDescription());

            if (cost.getCurrencyCode() != null) {
                costCurrency = cost.getCurrencyCode();
                if (costCurrencyTotal.equals("")) {
                    costCurrencyTotal = costCurrency;
                    addReplacement(replacements, "[costcurrency]", costCurrencyTotal);
                }
            }
            if (cost.getValue() != null) {
                costValue = NumberFormat.getNumberInstance(Locale.GERMAN).format(cost.getValue());
                totalCost = totalCost + cost.getValue();
            }

            addReplacement(replacements, docVar4, costCurrency);
            addReplacement(replacements, docVar5, costValue);
        }

        addReplacement(replacements, "[costtotal]", NumberFormat.getNumberInstance(Locale.GERMAN).format(totalCost));
    }

    //All tables variables replacement
    private void tableContent(Dmp dmp, List<XWPFParagraph> xwpfParagraphs, Map<String, String> replacements, List<XWPFTable> tables, List<Dataset> datasets, List<Dataset> closedDatasets, List<Cost> costList, SimpleDateFormat formatter) {

        for (XWPFTable xwpfTable : tables) {
            if (xwpfTable.getRow(1) != null) {

                //dynamic table rows code for dataset (1a)
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1name]")) {

                    log.info("Export steps: Table 1a");

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

                            if (datasets.get(i-1).getSensitiveData() != null) {
                                if (datasets.get(i - 1).getSensitiveData()) {
                                    docVar.add("yes");
                                } else {
                                    docVar.add("no");
                                }
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

                    log.info("Export steps: Table 3b");

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

                    log.info("Export steps: Table 5a");

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

                            if (datasets.get(i - 1).getLegalRestrictions() != null) {
                                if (datasets.get(i - 1).getLegalRestrictions()) {
                                    if (dmp.getLegalRestrictionsComment() != null)
                                        docVar.add(dmp.getLegalRestrictionsComment());
                                    else
                                        docVar.add("");
                                }
                                else {
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
                                if (distributions.size() > 0) {
                                    for (Distribution distribution: distributions) {
                                        if (Repository.class.isAssignableFrom(distribution.getHost().getClass()))
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
                            else {
                                docVar.add("");
                            }

                            //TODO: PID not yet defined
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

                //table 5b: long term storage
                //notes: dataset number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1repo]")) {

                    log.info("Export steps: Table 5b: long term storage");

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
                                    if (Repository.class.isAssignableFrom(distribution.getHost().getClass()))
                                        repositories.add(distribution.getHost().getTitle());
                                }
                                if (repositories.size() > 0) {
                                    docVar.add(multipleVariable(repositories));
                                }
                                else {
                                    docVar.add("");
                                }
                            }
                            else {
                                docVar.add("");
                            }

                            if (datasets.get(i - 1).getRetentionPeriod() != null)
                                docVar.add(datasets.get(i - 1).getRetentionPeriod() + " years");
                            else
                                docVar.add("");

                            if (datasets.get(i - 1).getDmp().getTargetAudience() != null)
                                docVar.add(datasets.get(i - 1).getDmp().getTargetAudience());
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
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }

                //table 5b: delete datasets
                if (xwpfTable.getRow(1).getCell(0).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[dataset1delete]")) {

                    log.info("Export steps: Table 5b: dataset deletion");

                    if (closedDatasets.size() > 1) {
                        for (int i = 2; i < closedDatasets.size() + 1; i++) {

                            XWPFTableRow sourceTableRow = xwpfTable.getRow(i);
                            XWPFTableRow newRow = new XWPFTableRow(sourceTableRow.getCtRow(), xwpfTable);

                            try {
                                newRow = insertNewTableRow(sourceTableRow, i);
                            }
                            catch (Exception e) {
                            }

                            ArrayList<String> docVar = new ArrayList<>();
                            //TODO datasets and hosts are now connected by Distribution objects
                            if (closedDatasets.get(i-1).getTitle() != null)
                                docVar.add(closedDatasets.get(i-1).getTitle());
                            else
                                docVar.add("");

                            if (closedDatasets.get(i-1).getDateOfDeletion() != null)
                                docVar.add(formatter.format(closedDatasets.get(i-1).getDateOfDeletion()));
                            else
                                docVar.add("");

                            if (closedDatasets.get(i-1).getReasonForDeletion() != null)
                                docVar.add(closedDatasets.get(i-1).getReasonForDeletion());
                            else
                                docVar.add("");

                            //put blank on responsible person
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
                    xwpfTable.removeRow(xwpfTable.getRows().size() - 1);
                }

                //dynamic table rows code for cost
                //table 6b
                //notes: cost number 2 until the end will be written directly to the table
                if (xwpfTable.getRow(1).getCell(0).getParagraphs().get(0).getRuns().get(0).getText(0).equals("[cost1title]")) {

                    log.info("Export steps: Table 6b");

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
                    replaceInParagraphs(xwpfParagraphs, replacements);
                }
            }
        }
    }

    private List<Dataset> getClosedDatasets(List<Dataset> datasets) {

        List<Dataset> closedDatasets = new ArrayList<>();

        for (Dataset dataset : datasets) {
            if (dataset.getDelete() != null) {
                if (dataset.getDelete()) {
                    closedDatasets.add(dataset);
                }
            }
        }
        return closedDatasets;
    }
}

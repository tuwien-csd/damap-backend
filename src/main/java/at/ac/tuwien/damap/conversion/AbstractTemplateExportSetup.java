package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Cost;
import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.damap.r3data.RepositoriesService;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.mapper.ContributorDOMapper;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class describes necessary setup for all template export classes.
 */
@JBossLog
public abstract class AbstractTemplateExportSetup extends AbstractTemplateExportFunctions {

    @Inject
    RepositoriesService repositoriesService;

    @Inject
    ProjectService projectService;

    @Inject
    TemplateFileBrokerService templateFileBrokerService;

    @Inject
    LoadResourceService loadResourceService;

    protected Map<String, String> replacements = new HashMap<>();
    protected Map<String, String> footerMap = new HashMap<>();
    // Convert the date for readable format for the document
    protected Dmp dmp = null;
    protected List<Dataset> datasets = null;
    protected List<Dataset> deletedDatasets = null;
    protected List<Cost> costList = null;
    // elements of the document that need to be navigated through
    protected Properties prop = null;
    protected List<XWPFParagraph> xwpfParagraphs = null;
    protected List<XWPFTable> xwpfTables = null;
    protected ContributorDO projectCoordinator = null;

    protected void exportSetup(long dmpId) {
        // Loading data related to the project from database
        dmp = dmpRepo.findById(dmpId);
        datasets = dmp.getDatasetList();
        deletedDatasets = getDeletedDatasets(datasets);
        costList = dmp.getCosts();

        // determine project leader/coordinator/principal investigator
        Optional<Contributor> projectLeaderOpt = dmp.getContributorList().stream()
                .filter(contributor -> contributor.getContributorRole() == EContributorRole.PROJECT_LEADER).findFirst();
        if (projectLeaderOpt.isPresent())
            projectCoordinator = ContributorDOMapper.mapEntityToDO(projectLeaderOpt.get(), new ContributorDO());
        else
            try {
                if (dmp.getProject() != null && dmp.getProject().getUniversityId() != null)
                    projectCoordinator = projectService.getProjectLeader(dmp.getProject().getUniversityId());
            } catch (Exception e) {
                log.error("Project API not functioning");
            }
    }

    private List<Dataset> getDeletedDatasets(List<Dataset> datasets) {
        return datasets.stream().filter(Dataset::getDelete).collect(Collectors.toList());
    }

    protected List<Contributor> getContributorsByRole(List<Contributor> contributors, EContributorRole role) {
        return contributors.stream()
                .filter(c -> c.getContributorRole() == role).collect(Collectors.toList());

    }

    protected String getContributorsText(List<Contributor> contributors) {
        return String.join(",",
                contributors.stream()
                        .map(c -> String.format("%s %s (%s)", c.getFirstName(), c.getLastName(), c.getMbox()))
                        .collect(Collectors.toList()));
    }
}

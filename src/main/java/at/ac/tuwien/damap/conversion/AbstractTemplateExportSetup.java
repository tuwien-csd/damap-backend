package at.ac.tuwien.damap.conversion;

import at.ac.tuwien.damap.domain.Cost;
import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.r3data.RepositoriesService;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import lombok.extern.jbosslog.JBossLog;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import javax.inject.Inject;
import java.util.*;

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
    //Convert the date for readable format for the document
    protected Dmp dmp = null;
    protected List<Dataset> datasets = null;
    protected List<Dataset> deletedDatasets = null;
    protected List<Cost> costList = null;
    //elements of the document that need to be navigated through
    protected Properties prop = null;
    protected List<XWPFParagraph> xwpfParagraphs = null;
    protected List<XWPFTable> xwpfTables = null;
    protected ContributorDO projectCoordinator = null;

    protected void exportSetup(long dmpId) {
        //Loading data related to the project from database
        dmp = dmpRepo.findById(dmpId);
        datasets = dmp.getDatasetList();
        deletedDatasets = getDeletedDatasets(datasets);
        costList = dmp.getCosts();

        try {
            projectCoordinator = projectService.getProjectLeader(dmp.getProject().getUniversityId());
        } catch (Exception e) {
            log.error("Project API not functioning");
        }
    }

    private List<Dataset> getDeletedDatasets(List<Dataset> datasets) {
        List<Dataset> deletedDatasets = new ArrayList<>();
        for (Dataset dataset : datasets) {
            if (dataset.getDelete() != null) {
                if (dataset.getDelete()) {
                    deletedDatasets.add(dataset);
                }
            }
        }
        return deletedDatasets;
    }
}

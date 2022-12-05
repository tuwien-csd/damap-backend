package at.ac.tuwien.damap.rest.dmp.service;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.mapper.ContributorDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpListItemDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.MapperService;
import at.ac.tuwien.damap.rest.dmp.mapper.ProjectSupplementDOMapper;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import at.ac.tuwien.damap.rest.projects.ProjectSupplementDO;
import at.ac.tuwien.damap.rest.version.VersionDO;
import at.ac.tuwien.damap.rest.version.VersionService;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@JBossLog
public class DmpService {

    @Inject
    DmpRepo dmpRepo;

    @Inject
    AccessRepo accessRepo;

    @Inject
    ProjectService projectService;

    @Inject
    MapperService mapperService;

    @Inject
    VersionService versionService;

    public List<DmpListItemDO> getAll() {

        List<Dmp> dmpList = dmpRepo.getAll();
        List<DmpListItemDO> dmpListItemDOList = new ArrayList<>();
        dmpList.forEach(dmp -> {
            dmpListItemDOList.add(DmpListItemDOMapper.mapEntityToDO(null, dmp, new DmpListItemDO()));
        });
        return dmpListItemDOList;
    }

    public List<DmpListItemDO> getDmpListByPersonId(String personId) {

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        List<DmpListItemDO> dmpListItemDOS = new ArrayList<>();
        accessList.forEach(access -> {
            dmpListItemDOS.add(DmpListItemDOMapper.mapEntityToDO(access, access.getDmp(), new DmpListItemDO()));
        });
        return dmpListItemDOS;
    }

    public DmpDO getDmpById(long dmpId) {
        return DmpDOMapper.mapEntityToDO(dmpRepo.findById(dmpId), new DmpDO());
    }

    @Transactional
    public DmpDO create(@Valid DmpDO dmpDO, String editedBy) {
        log.info("Creating new DMP");
        DmpConsistencyUtility.enforceDmpConsistency(dmpDO);
        Dmp dmp = DmpDOMapper.mapDOtoEntity(dmpDO, new Dmp(), mapperService);
        dmp.setCreated(new Date());
        updateDmpSupplementalInfo(dmp);
        updateProjectLead(dmp);
        dmp.persistAndFlush();
        createAccess(dmp, editedBy);
        return getDmpById(dmp.id);
    }

    @Transactional
    public DmpDO update(@Valid DmpDO dmpDO) {
        log.info("Updating DMP with id " + dmpDO.getId());
        DmpConsistencyUtility.enforceDmpConsistency(dmpDO);
        Dmp dmp = dmpRepo.findById(dmpDO.getId());
        boolean projectSelectionChanged = projectSelectionChanged(dmp, dmpDO);
        DmpDOMapper.mapDOtoEntity(dmpDO, dmp, mapperService);
        dmp.setModified(new Date());
        if (projectSelectionChanged) {
            updateDmpSupplementalInfo(dmp);
            updateProjectLead(dmp);
        }
        dmp.persistAndFlush();
        return getDmpById(dmp.id);
    }

    @Transactional
    public void delete(long dmpId) {
        log.info("Deleting DMP with id " + dmpId);
        Dmp dmp = dmpRepo.findById(dmpId);
        this.removeAccess(dmp);
        this.removeVersions(dmp);
        dmpRepo.deleteById(dmpId);
    }

    public void createAccess(Dmp dmp, String editedById) {
        Access access = new Access();
        access.setUniversityId(editedById);
        access.setRole(EFunctionRole.OWNER);
        access.setDmp(dmp);
        access.setStart(new Date());
        access.persistAndFlush();
    }

    private void removeAccess(Dmp dmp) {
        List<Access> access = accessRepo.getAccessByDmp(dmp);
        access.forEach(Access::delete);
    }

    private void removeVersions(Dmp dmp) {
        List<VersionDO> versionDOs = versionService.getDmpVersions(dmp.id);
        for (VersionDO versionDO : versionDOs) {
            versionService.delete(versionDO.getId());
        }
    }

    public String getDefaultFileName(long id) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        String filename = "My Data Management Plan";

        Dmp dmp = dmpRepo.findById(id);
        if (dmp != null) {
            if (dmp.getProject() != null) {
                if (dmp.getProject().getUniversityId() != null && projectService.getProjectDetails(dmp.getProject().getUniversityId()) != null) {
                    filename = "DMP_" + projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym() + "_" + formatter.format(date);
                } else if (dmp.getProject().getTitle() != null)
                    filename = "DMP_" + dmp.getProject().getTitle() + "_" + formatter.format(date);
            } else {
                if (dmp.getTitle() != null)
                    filename = dmp.getTitle();
            }
        }

        filename = filename.replaceAll("[\"',\\s]+", "_");

        return filename;
    }

    public List<ProjectDO> checkExistingDmps(List<ProjectDO> projectDOList) {

        for (Dmp dmp : dmpRepo.getAll()) {
            for (ProjectDO projectDO : projectDOList) {
                if (dmp.getProject() != null &&
                        dmp.getProject().getUniversityId() != null &&
                        projectDO.getUniversityId() != null &&
                        dmp.getProject().getUniversityId().equals(projectDO.getUniversityId()))
                    projectDO.setDmpExists(true);
            }
        }
        return projectDOList;
    }

    // This method will retrieve the Project Supplement values from the connected CRIS System
    // and it will reset them to null in case the project is not from a connected system.
    private void updateDmpSupplementalInfo(Dmp dmp) {
        if (dmp.getProject() != null) {
            ProjectSupplementDO projectSupplementDO = null;
            if (dmp.getProject().getUniversityId() != null) {
                projectSupplementDO = projectService.getProjectSupplement(dmp.getProject().getUniversityId());
            }
            if (projectSupplementDO == null)
                projectSupplementDO = new ProjectSupplementDO();
            ProjectSupplementDOMapper.mapDOtoEntity(projectSupplementDO, dmp);
        }
    }

    /**
     * Retrieve the project leader of the DMPs project.
     * Set the project leader as contact, if there is no other contact selected.
     * Add the project leader as a contributor, if it is not already added.
     *
     * @param Dmp Data management plan
     */
    private void updateProjectLead(Dmp dmp) {
        if (dmp.getProject() == null || dmp.getProject().getUniversityId() == null)
            return;

        ContributorDO projectLeaderDO =
                projectService.getProjectLeader(dmp.getProject().getUniversityId());
        if (projectLeaderDO == null)
            return;

        List<Contributor> dmpContributors = dmp.getContributorList();

        Optional<Contributor> alreadyExistingContributorLeader =
                dmpContributors.stream()
                        .filter(c -> {
                            return c.getUniversityId().equals(
                                    projectLeaderDO.getUniversityId());
                        })
                        .findFirst();

        Contributor projectLeaderContributor =
                alreadyExistingContributorLeader.orElse(new Contributor());

        // Adding project leader as contributor if it was not there yet.
        if (alreadyExistingContributorLeader.isEmpty()) {
            ContributorDOMapper.mapDOtoEntity(
                    projectLeaderDO, projectLeaderContributor);
            projectLeaderContributor.setDmp(dmp);
            dmpContributors.add(projectLeaderContributor);
        }

        // If no role was defined, set contributor role to project leader
        if (projectLeaderContributor.getContributorRole() == null) {
            projectLeaderContributor.setContributorRole(
                    EContributorRole.PROJECT_LEADER);
        }

        // If no other contact was defined, set project leader as contact.
        if (dmpContributors.stream().noneMatch(c -> c.getContact())) {
            projectLeaderContributor.setContact(true);
        }
    }

    private boolean projectSelectionChanged(Dmp dmp, DmpDO dmpDO) {

        if (dmpDO.getProject() == null)
            return false;
        if (dmp.getProject() == null)
            return true;

        return !Objects.equals(dmp.getProject().id, dmpDO.getProject().getId()) ||
                !Objects.equals(dmp.getProject().getUniversityId(), dmpDO.getProject().getUniversityId());
    }

    public DmpDO getDmpByIdAndRevision(long dmpId, long revision) {
        AuditReader reader = AuditReaderFactory.get(dmpRepo.getEntityManager());
        Dmp dmpRevision = reader.find(Dmp.class, dmpId, revision);
        return DmpDOMapper.mapEntityToDO(dmpRevision, new DmpDO());
    }
}

package at.ac.tuwien.damap.rest.dmp.service;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpListItemDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.MapperService;
import at.ac.tuwien.damap.rest.dmp.mapper.ProjectSupplementDOMapper;
import at.ac.tuwien.damap.rest.projects.ProjectService;
import at.ac.tuwien.damap.rest.projects.ProjectSupplementDO;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public DmpDO create(SaveDmpWrapper dmpWrapper) {
        log.info("Creating new DMP");
        Dmp dmp = DmpDOMapper.mapDOtoEntity(dmpWrapper.getDmp(), new Dmp(), mapperService);
        dmp.setCreated(new Date());
        updateDmpSupplementalInfo(dmp);
        dmp.persistAndFlush();
        createAccess(dmp, dmpWrapper.getEdited_by());
        return getDmpById(dmp.id);
    }

    @Transactional
    public DmpDO update(SaveDmpWrapper dmpWrapper) {
        log.info("Updating DMP with id " + dmpWrapper.getDmp().getId());
        Dmp dmp = dmpRepo.findById(dmpWrapper.getDmp().getId());
        boolean projectSelectionChanged = projectSelectionChanged(dmp, dmpWrapper.getDmp());
        DmpDOMapper.mapDOtoEntity(dmpWrapper.getDmp(), dmp, mapperService);
        dmp.setModified(new Date());
        if (projectSelectionChanged)
            updateDmpSupplementalInfo(dmp);
        dmp.persistAndFlush();
        return getDmpById(dmp.id);
    }

    public void createAccess(Dmp dmp, String editedById){
        Access access = new Access();
        access.setUniversityId(editedById);
        access.setRole(EFunctionRole.OWNER);
        access.setDmp(dmp);
        access.setStart(new Date());
        access.persistAndFlush();
    }

    public String getDefaultFileName(long id){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        String filename = "My Data Management Plan";

        Dmp dmp = dmpRepo.findById(id);
        if (dmp != null){
            if (projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym() != null) {
                filename = "DMP_" + projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym() + "_" + formatter.format(date).toString();
            }
            else {
                if (dmp.getTitle() != null)
                    filename = dmp.getTitle();
                else if (dmp.getProject() != null){
                    if (dmp.getProject().getTitle() != null)
                        filename = "DMP_" + dmp.getProject().getTitle()  + "_" + formatter.format(date).toString();
                }
            }
        }

        filename = filename.replaceAll("[\"',\\s]+", "_");

        return filename;
    }

    public List<ProjectDO> checkExistingDmps(List<ProjectDO> projectDOList){

        for (Dmp dmp : dmpRepo.getAll()) {
            for (ProjectDO projectDO : projectDOList){
                if (dmp.getProject() != null &&
                        dmp.getProject().getUniversityId() != null &&
                        projectDO.getUniversityId() != null &&
                        dmp.getProject().getUniversityId().equals(projectDO.getUniversityId()))
                    projectDO.setDmpExists(true);
            }
        }
        return projectDOList;
    }

    private void updateDmpSupplementalInfo(Dmp dmp) {
        if (dmp.getProject() != null) {
            ProjectSupplementDO projectSupplementDO = projectService.getProjectSupplement(dmp.getProject().getUniversityId());
            if (projectSupplementDO != null)
                ProjectSupplementDOMapper.mapDOtoEntity(projectSupplementDO, dmp);
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
}

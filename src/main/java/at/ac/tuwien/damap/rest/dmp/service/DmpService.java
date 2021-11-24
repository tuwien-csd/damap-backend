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
import at.ac.tuwien.damap.rest.projects.ProjectService;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@JBossLog
public class DmpService {

    @Inject
    DmpRepo dmpRepo;

    @Inject
    AccessRepo accessRepo;

    @Inject
    ProjectService projectService;

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
        Dmp dmp = DmpDOMapper.mapDOtoEntity(dmpWrapper.getDmp(), new Dmp());
        dmp.setCreated(new Date());
        dmp.persist();
        createAccess(dmp, dmpWrapper.getEdited_by());
        return getDmpById(dmp.id);
    }

    @Transactional
    public DmpDO update(SaveDmpWrapper dmpWrapper) {
        log.info("Updating DMP with id " + dmpWrapper.getDmp().getId());
        // TODO: check privileges
        Dmp dmp = dmpRepo.findById(dmpWrapper.getDmp().getId());
        DmpDOMapper.mapDOtoEntity(dmpWrapper.getDmp(), dmp);
        dmp.setModified(new Date());
        dmp.persist();
        return getDmpById(dmp.id);
    }

    public void createAccess(Dmp dmp, String editedById){
        Access access = new Access();
        access.setUniversityId(editedById);
        access.setRole(EFunctionRole.OWNER);
        access.setDmp(dmp);
        access.setStart(new Date());
        access.persist();
    }

    public String getDefaultFileName(long id){
        String filename = "My Data Management Plan";

        Dmp dmp = dmpRepo.findById(id);
        if (dmp != null){
            if (projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym() != null) {
                filename = projectService.getProjectDetails(dmp.getProject().getUniversityId()).getAcronym();
            }
            else {
                if (dmp.getTitle() != null)
                    filename = dmp.getTitle();
                else if (dmp.getProject() != null){
                    if (dmp.getProject().getTitle() != null)
                        filename = dmp.getProject().getTitle();
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
}

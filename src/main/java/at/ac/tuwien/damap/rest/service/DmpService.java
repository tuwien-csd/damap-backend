package at.ac.tuwien.damap.rest.service;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.domain.DmpDO;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.mapper.ContributorDOMapper;
import at.ac.tuwien.damap.rest.mapper.DatasetDOMapper;
import at.ac.tuwien.damap.rest.mapper.DmpDOMapper;
import at.ac.tuwien.damap.rest.mapper.DmpListItemDOMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class DmpService {

    @Inject
    DmpRepo dmpRepo;

    @Inject
    AccessRepo accessRepo;

    public List<DmpDO> getAll() {

        List<Dmp> dmpList = dmpRepo.getAll();
        List<DmpDO> dmpDOList = new ArrayList<>();
        dmpList.forEach(dmp -> {
            DmpDO dmpDO = new DmpDO();
            DmpDOMapper.mapEntityToDO(dmp, dmpDO);
            dmpDOList.add(dmpDO);
        });
        return dmpDOList;
    }

    public List<DmpListItemDO> getDmpListByPersonId(String personId) {

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        List<DmpListItemDO> dmpListItemDOS = new ArrayList<>();
        accessList.forEach(access -> {
            DmpListItemDO dmpListItemDO = new DmpListItemDO();
            DmpListItemDOMapper.mapEntityToDO(access, access.getDmp(), dmpListItemDO);
            dmpListItemDOS.add(dmpListItemDO);
        });
        return dmpListItemDOS;
    }

    public DmpDO getDmpById(long dmpId) {
        Dmp dmp = dmpRepo.findById(dmpId);

        DmpDO dmpDO = new DmpDO();
        DmpDOMapper.mapEntityToDO(dmp, dmpDO);
        return dmpDO;
    }

    @Transactional
    public SaveDmpResponse save(SaveDmpWrapper dmpWrapper){
        long dmpId;
        if (dmpWrapper.getDmp().getId() == null)
            dmpId = create(dmpWrapper);
        else
            dmpId = update(dmpWrapper);
        return new SaveDmpResponse(dmpId);
    }

    public long create(SaveDmpWrapper dmpWrapper) {
        Dmp dmp = new Dmp();
        DmpDOMapper.mapDOtoEntity(dmpWrapper.getDmp(), dmp);
        dmp.persist();
        createAccess(dmp, dmpWrapper.getEdited_by());
        return dmp.id;
    }

    public long update(SaveDmpWrapper dmpWrapper) {
        Dmp dmp = dmpRepo.findById(dmpWrapper.getDmp().getId());
        DmpDOMapper.mapDOtoEntity(dmpWrapper.getDmp(), dmp);
        dmp.persist();
        return dmp.id;
    }

    public void createAccess(Dmp dmp, String editedById){
        Access access = new Access();
        access.setUniversityId(editedById);
        access.setRole(EFunctionRole.OWNER);
        access.setDmp(dmp);
        access.setStart(new Date());
        access.persist();
    }
}

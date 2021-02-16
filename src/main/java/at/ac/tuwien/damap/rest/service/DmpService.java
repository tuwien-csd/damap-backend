package at.ac.tuwien.damap.rest.service;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.domain.DmpDO;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.mapper.DmpDOMapper;
import at.ac.tuwien.damap.rest.mapper.DmpListItemDOMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
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
            DmpDOMapper.mapAtoB(dmp, dmpDO);
            dmpDOList.add(dmpDO);
        });
        return dmpDOList;
    }

    public List<DmpListItemDO> getDmpListByPersonId(String personId) {

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(Long.valueOf(personId));

        List<DmpListItemDO> dmpListItemDOS = new ArrayList<>();
        accessList.forEach(access -> {
            DmpListItemDO dmpListItemDO = new DmpListItemDO();
            DmpListItemDOMapper.mapAtoB(access, access.getDmp(), dmpListItemDO);
            dmpListItemDOS.add(dmpListItemDO);
        });
        return dmpListItemDOS;
    }

    public DmpDO getDmpById(long dmpId) {
        Dmp dmp = dmpRepo.findById(dmpId);

        DmpDO dmpDO = new DmpDO();
        DmpDOMapper.mapAtoB(dmp, dmpDO);
        return dmpDO;
    }
}

package at.ac.tuwien.damap.rest.access.service;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.access.domain.AccessDO;
import at.ac.tuwien.damap.rest.access.mapper.AccessMapper;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.mapper.ContributorDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.MapperService;
import at.ac.tuwien.damap.rest.persons.PersonService;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@JBossLog
public class AccessService {

    @Inject
    AccessRepo accessRepo;

    @Inject
    DmpRepo dmpRepo;

    @Inject
    PersonService personService;

    @Inject
    MapperService mapperService;

    public List<ContributorDO> getByDmpId(long dmpId) {
        Dmp dmp = dmpRepo.findById(dmpId);
        // Get access list (owner, editors)
        List<ContributorDO> accessDOList = new ArrayList<>();
        accessRepo.getAccessByDmp(dmp).forEach(access -> {
            AccessDO accessDO = AccessMapper.mapEntityToDO(access, new AccessDO());
            // Set owner/editor data. If they're not listed as a contributor, fields will be empty.
            if (accessDO.getMbox() == null) {
                // TODO: Handle case when owner is no longer at university
                ContributorDO owner = personService.getPersonById(access.getUniversityId());
                accessDO.setFirstName(owner.getFirstName());
                accessDO.setLastName(owner.getLastName());
                accessDO.setMbox(owner.getMbox());
            }
            accessDOList.add(accessDO);
        });

        // Get dmp contributors (viewers)
        dmp.getContributorList().forEach(contributor -> {
            // Only university members can be editors for now
            if (contributor.getUniversityId() != null &&
                accessDOList.stream().noneMatch(a -> a.getUniversityId().equals(contributor.getUniversityId()))) {
                ContributorDO contributorDO = new ContributorDO();
                ContributorDOMapper.mapEntityToDO(contributor, contributorDO);
                // Set id null, so it's not confused with access id
                contributorDO.setId(null);
                accessDOList.add(contributorDO);
            }
        });
        return accessDOList;
    }

    @Transactional
    public AccessDO create(AccessDO accessDO) {
        Access access = new Access();
        AccessMapper.mapDOtoEntity(accessDO, access, mapperService);
        if (access.getPersonIdentifier() != null) {
            access.getPersonIdentifier().persist();
        }
        access.setStart(new Date());
        access.persist();
        return AccessMapper.mapEntityToDO(access, new AccessDO());
    }

    @Transactional
    public void delete(long id) {
        accessRepo.deleteById(id);
    }

    // Can be overwritten if necessary
    public List<Contributor> canGetAccess(long dmpId) {
        Dmp dmp = dmpRepo.findById(dmpId);
        return dmp == null ? new ArrayList<>() : dmp.getContributorList();
    }
}


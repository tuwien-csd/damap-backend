package at.ac.tuwien.damap.rest.access.mapper;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.rest.access.domain.AccessDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.mapper.IdentifierDOMapper;
import at.ac.tuwien.damap.rest.dmp.mapper.MapperService;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class AccessMapper {

    public AccessDO mapEntityToDO(Access access, AccessDO accessDO) {
        accessDO.setId(access.id);
        accessDO.setDmpId(access.getDmp().id);
        accessDO.setUniversityId(access.getUniversityId());
        // Get name and mail for accesses from contributor list
        Optional<Contributor> contributor = access.getDmp().getContributorList().stream().filter(c ->
                c.getUniversityId().equals(access.getUniversityId())).findFirst();
        if (contributor.isPresent()) {
            Contributor c = contributor.get();
            accessDO.setFirstName(c.getFirstName());
            accessDO.setLastName(c.getLastName());
            accessDO.setMbox(c.getMbox());
        }
        if (access.getPersonIdentifier() != null) {
            IdentifierDO identifierDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(access.getPersonIdentifier(), identifierDO);
            accessDO.setPersonId(identifierDO);
        }
        accessDO.setAccess(access.getRole());
        accessDO.setStart(access.getStart());
        accessDO.setUntil(access.getUntil());
        return accessDO;
    }

    public Access mapDOtoEntity(AccessDO accessDO, Access access, MapperService mapperService) {
        Dmp dmp = mapperService.getDmpById(accessDO.getDmpId());
        access.setDmp(dmp);
        access.setRole(accessDO.getAccess());
        access.setUniversityId(accessDO.getUniversityId());
        if (accessDO.getPersonId() != null) {
            access.setPersonIdentifier(IdentifierDOMapper.mapDOtoEntity(accessDO.getPersonId(), new Identifier()));
        }
        access.setStart(accessDO.getStart());
        access.setUntil(accessDO.getUntil());
        return access;
    }
}

package org.damap.base.rest.access.mapper;

import org.damap.base.domain.Access;
import org.damap.base.domain.Contributor;
import org.damap.base.domain.Dmp;
import org.damap.base.domain.Identifier;
import org.damap.base.rest.access.domain.AccessDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import org.damap.base.rest.dmp.mapper.IdentifierDOMapper;
import org.damap.base.rest.dmp.mapper.MapperService;
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
                c.getUniversityId() != null &&
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

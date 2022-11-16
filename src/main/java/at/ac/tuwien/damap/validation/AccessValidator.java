package at.ac.tuwien.damap.validation;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.security.SecurityService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AccessValidator {

    @Inject
    AccessRepo accessRepo;

    @Inject
    SecurityService securityService;

    public boolean canViewDmp(long dmpId, String personId) {
        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        if (securityService.isAdmin()) {
            return true;
        }

        for (Access access : accessList) {
            if (access.getDmp().id.equals(dmpId)) {
                return true;
            }
        }

        return false;
    }

    public boolean canEditDmp(long dmpId, String personId) {
        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        if (securityService.isAdmin()) {
            return true;
        }

        for (Access access : accessList) {
            if (access.getDmp().id.equals(dmpId) &&
                    (access.getRole().equals(EFunctionRole.EDITOR)
                            || access.getRole().equals(EFunctionRole.OWNER))
            ) {
                return true;
            }
        }

        return false;
    }

    public boolean canExportDmp(long dmpId, String personId) {
        return this.canViewDmp(dmpId, personId);
    }

    public boolean canDeleteDmp(long dmpId, String personId) {
        if (securityService.isAdmin()) {
            return true;
        }

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        for (Access access : accessList) {
            if (access.getDmp().id.equals(dmpId) &&
                    access.getRole().equals(EFunctionRole.OWNER)
            ) {
                return true;
            }
        }

        return false;
    }
}

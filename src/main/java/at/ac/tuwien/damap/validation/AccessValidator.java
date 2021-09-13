package at.ac.tuwien.damap.validation;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AccessValidator {

    @Inject
    AccessRepo accessRepo;

    public boolean canViewDmp(long dmpId, String personId) {
        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        for (Access access : accessList) {
            if (access.getDmp().id.equals(dmpId)) {
                return true;
            }
        }

        return false;
    }

    public boolean canEditDmp(long dmpId, String personId) {
        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        for (Access access : accessList) {
            if (access.getDmp().id.equals(dmpId) &&
                    (access.getRole().equals(EFunctionRole.ADMIN)
                    || access.getRole().equals(EFunctionRole.EDITOR)
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
}

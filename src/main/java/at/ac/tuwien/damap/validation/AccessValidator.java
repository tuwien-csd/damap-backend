package at.ac.tuwien.damap.validation;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.security.SecurityService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccessValidator {

    @Inject
    AccessRepo accessRepo;

    @Inject
    SecurityService securityService;

    public boolean canViewDmp(long dmpId, String personId) {
        if (securityService.isAdmin()) {
            return true;
        }

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        Optional<Access> dmpAccess = accessList.stream().filter(access ->
                access.getDmp().id.equals(dmpId)).findAny();

        return dmpAccess.isPresent();
    }

    public boolean canEditDmp(long dmpId, String personId) {
        if (securityService.isAdmin()) {
            return true;
        }

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        Optional<Access> dmpAccess = accessList.stream().filter(access ->
                access.getDmp().id.equals(dmpId) &&
                (access.getRole().equals(EFunctionRole.EDITOR) ||
                 access.getRole().equals(EFunctionRole.OWNER))).findAny();

        return dmpAccess.isPresent();
    }

    public boolean canExportDmp(long dmpId, String personId) {
        return this.canViewDmp(dmpId, personId);
    }

    public boolean canDeleteDmp(long dmpId, String personId) {
        if (securityService.isAdmin()) {
            return true;
        }

        List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

        Optional<Access> dmpAccess = accessList.stream().filter(access ->
                access.getDmp().id.equals(dmpId) &&
                access.getRole().equals(EFunctionRole.OWNER)).findAny();

        return dmpAccess.isPresent();
    }
}

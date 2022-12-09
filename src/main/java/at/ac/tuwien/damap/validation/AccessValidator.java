package at.ac.tuwien.damap.validation;

import at.ac.tuwien.damap.domain.Access;
import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.enums.EFunctionRole;
import at.ac.tuwien.damap.repo.AccessRepo;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.access.domain.AccessDO;
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
    DmpRepo dmpRepo;

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

    public boolean canViewAccess(long dmpId) {
        if (securityService.isAdmin()) {
            return true;
        }

        Dmp dmp = dmpRepo.findById(dmpId);
        if (dmp == null) {
            return false;
        }

        List<Access> accessList = accessRepo.getAccessByDmp(dmp);
        Optional<Access> dmpAccess = accessList.stream().filter(access ->
                access.getUniversityId().equals(securityService.getUserId()) &&
                (access.getRole().equals(EFunctionRole.EDITOR) ||
                 access.getRole().equals(EFunctionRole.OWNER))).findAny();

        return dmpAccess.isPresent();
    }

    public boolean canCreateAccess(AccessDO accessDO) {
        if (accessDO.getAccess().equals(EFunctionRole.OWNER)) {
            return false;
        }

        Dmp dmp = dmpRepo.findById(accessDO.getDmpId());
        if (dmp == null) {
            return false;
        }

        // Check if new access is for a contributor
        List<Contributor> contributors = dmp.getContributorList();
        Optional<Contributor> contributor = contributors.stream().filter(c ->
                c.getUniversityId().equals(accessDO.getUniversityId())).findAny();

        // Check user permission to create new access
        boolean hasPermission = securityService.isAdmin();
        if (!hasPermission) {
            List<Access> accessList = accessRepo.getAccessByDmp(dmp);
            Optional<Access> dmpAccess = accessList.stream().filter(access ->
                    access.getUniversityId().equals(securityService.getUserId()) &&
                    (access.getRole().equals(EFunctionRole.EDITOR) ||
                     access.getRole().equals(EFunctionRole.OWNER))).findAny();
            hasPermission = dmpAccess.isPresent();
        }

        return contributor.isPresent() && hasPermission;
    }

    public boolean canDeleteAccess(long id) {
        Access access = accessRepo.findById(id);

        // Non-existing access can be deleted (idempotence)
        if (access == null) {
            return true;
        }

        // Can't delete owner access
        if (access.getRole().equals(EFunctionRole.OWNER)) {
            return false;
        }

        // Check if user has permission to delete access
        if (securityService.isAdmin()) {
            return true;
        }
        List<Access> accessList = accessRepo.getAllDmpByUniversityId(securityService.getUserId());
        Optional<Access> dmpAccess = accessList.stream().filter(a ->
                a.getDmp().id.equals(access.getDmp().id) &&
                (a.getRole().equals(EFunctionRole.EDITOR) ||
                 a.getRole().equals(EFunctionRole.OWNER))).findAny();

        return dmpAccess.isPresent();
    }
}

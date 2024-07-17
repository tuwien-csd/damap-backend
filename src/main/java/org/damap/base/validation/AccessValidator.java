package org.damap.base.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.damap.base.domain.Access;
import org.damap.base.domain.Contributor;
import org.damap.base.domain.Dmp;
import org.damap.base.enums.EFunctionRole;
import org.damap.base.repo.AccessRepo;
import org.damap.base.repo.DmpRepo;
import org.damap.base.rest.access.domain.AccessDO;
import org.damap.base.security.SecurityService;

/** AccessValidator class. */
@ApplicationScoped
public class AccessValidator {

  @Inject AccessRepo accessRepo;

  @Inject DmpRepo dmpRepo;

  @Inject SecurityService securityService;

  /**
   * canViewDmp.
   *
   * @param dmpId a long
   * @param personId a {@link java.lang.String} object
   * @return a boolean
   */
  public boolean canViewDmp(long dmpId, String personId) {
    if (securityService.isAdmin()) {
      return true;
    }

    List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

    Optional<Access> dmpAccess =
        accessList.stream().filter(access -> access.getDmp().id.equals(dmpId)).findAny();

    return dmpAccess.isPresent();
  }

  /**
   * canEditDmp.
   *
   * @param dmpId a long
   * @param personId a {@link java.lang.String} object
   * @return a boolean
   */
  public boolean canEditDmp(long dmpId, String personId) {
    if (securityService.isAdmin()) {
      return true;
    }

    List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

    Optional<Access> dmpAccess =
        accessList.stream()
            .filter(
                access ->
                    access.getDmp().id.equals(dmpId)
                        && (access.getRole().equals(EFunctionRole.EDITOR)
                            || access.getRole().equals(EFunctionRole.OWNER)))
            .findAny();

    return dmpAccess.isPresent();
  }

  /**
   * canExportDmp.
   *
   * @param dmpId a long
   * @param personId a {@link java.lang.String} object
   * @return a boolean
   */
  public boolean canExportDmp(long dmpId, String personId) {
    return this.canViewDmp(dmpId, personId);
  }

  /**
   * canDeleteDmp.
   *
   * @param dmpId a long
   * @param personId a {@link java.lang.String} object
   * @return a boolean
   */
  public boolean canDeleteDmp(long dmpId, String personId) {
    if (securityService.isAdmin()) {
      return true;
    }

    List<Access> accessList = accessRepo.getAllDmpByUniversityId(personId);

    Optional<Access> dmpAccess =
        accessList.stream()
            .filter(
                access ->
                    access.getDmp().id.equals(dmpId)
                        && access.getRole().equals(EFunctionRole.OWNER))
            .findAny();

    return dmpAccess.isPresent();
  }

  /**
   * canViewAccess.
   *
   * @param dmpId a long
   * @return a boolean
   */
  public boolean canViewAccess(long dmpId) {
    if (securityService.isAdmin()) {
      return true;
    }

    Dmp dmp = dmpRepo.findById(dmpId);
    if (dmp == null) {
      return false;
    }

    List<Access> accessList = accessRepo.getAccessByDmp(dmp);
    Optional<Access> dmpAccess =
        accessList.stream()
            .filter(
                access ->
                    access.getUniversityId().equals(securityService.getUserId())
                        && (access.getRole().equals(EFunctionRole.EDITOR)
                            || access.getRole().equals(EFunctionRole.OWNER)))
            .findAny();

    return dmpAccess.isPresent();
  }

  /**
   * canCreateAccess.
   *
   * @param accessDO a {@link org.damap.base.rest.access.domain.AccessDO} object
   * @return a boolean
   */
  public boolean canCreateAccess(AccessDO accessDO) {
    if (accessDO.getAccess().equals(EFunctionRole.OWNER)) {
      return false;
    }

    Dmp dmp = dmpRepo.findById(accessDO.getDmpId());
    if (dmp == null) {
      return false;
    }

    // Check user permission to create new access
    boolean hasPermission = securityService.isAdmin();
    if (!hasPermission) {
      List<Access> accessList = accessRepo.getAccessByDmp(dmp);
      Optional<Access> dmpAccess =
          accessList.stream()
              .filter(
                  access ->
                      access.getUniversityId().equals(securityService.getUserId())
                          && (access.getRole().equals(EFunctionRole.EDITOR)
                              || access.getRole().equals(EFunctionRole.OWNER)))
              .findAny();
      hasPermission = dmpAccess.isPresent();
    }

    return canGetAccess(accessDO) && hasPermission;
  }

  /**
   * canDeleteAccess.
   *
   * @param id a long
   * @return a boolean
   */
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
    Optional<Access> dmpAccess =
        accessList.stream()
            .filter(
                a ->
                    a.getDmp().id.equals(access.getDmp().id)
                        && (a.getRole().equals(EFunctionRole.EDITOR)
                            || a.getRole().equals(EFunctionRole.OWNER)))
            .findAny();

    return dmpAccess.isPresent();
  }

  // Can the selected user be given access to this dmp
  // Can be overwritten if necessary
  // current implementation allows only institutional personnel which are also contributors
  /**
   * canGetAccess.
   *
   * @param accessDO a {@link org.damap.base.rest.access.domain.AccessDO} object
   * @return a boolean
   */
  public boolean canGetAccess(AccessDO accessDO) {
    Dmp dmp = dmpRepo.findById(accessDO.getDmpId());
    List<Contributor> contributors = dmp == null ? new ArrayList<>() : dmp.getContributorList();
    // Check if new access is for a contributor
    Optional<Contributor> contributor =
        contributors.stream()
            .filter(
                c ->
                    c.getUniversityId() != null
                        && c.getUniversityId().equals(accessDO.getUniversityId()))
            .findAny();
    return contributor.isPresent();
  }
}

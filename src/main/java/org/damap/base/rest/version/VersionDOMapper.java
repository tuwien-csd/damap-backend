package org.damap.base.rest.version;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.DamapRevisionEntity;
import org.damap.base.domain.DmpVersion;
import org.damap.base.repo.DmpRepo;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

@UtilityClass
public class VersionDOMapper {

  public VersionDO mapEntityToDO(DmpVersion version, VersionDO versionDO) {
    versionDO.setId(version.id);
    versionDO.setDmpId(version.getDmp().id);
    versionDO.setRevisionNumber(version.getRevisionEntity().getId());
    versionDO.setVersionName(version.getVersionName());
    versionDO.setVersionDate(version.getVersionDate());
    versionDO.setEditor(version.getRevisionEntity().getChangedBy());

    return versionDO;
  }

  public DmpVersion mapDOtoEntity(VersionDO versionDO, DmpVersion version, DmpRepo dmpRepo) {
    if (versionDO.getId() != null) version.id = versionDO.getId();
    version.setDmp(dmpRepo.findById(versionDO.getDmpId()));
    version.setVersionDate(versionDO.getVersionDate());
    version.setVersionName(versionDO.getVersionName());
    if (versionDO.getRevisionNumber() != null) {
      version.setRevisionEntity(
          getRevisionByRevisionNumber(dmpRepo, versionDO.getRevisionNumber()));
    }
    return version;
  }

  private DamapRevisionEntity getRevisionByRevisionNumber(DmpRepo dmpRepo, long revisionNumber) {
    AuditReader reader = AuditReaderFactory.get(dmpRepo.getEntityManager());
    return reader.findRevision(DamapRevisionEntity.class, revisionNumber);
  }
}

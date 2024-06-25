package org.damap.base.rest.version;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.domain.DmpVersion;
import org.damap.base.repo.DmpRepo;
import org.damap.base.repo.DmpVersionRepo;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

@ApplicationScoped
@JBossLog
public class VersionService {

  @Inject DmpVersionRepo dmpVersionRepo;

  @Inject DmpRepo dmpRepo;

  public List<VersionDO> getDmpVersions(long dmpId) {
    List<VersionDO> versionDOList = new ArrayList<>();
    dmpVersionRepo
        .getAllByDmp(dmpId)
        .forEach(
            version -> versionDOList.add(VersionDOMapper.mapEntityToDO(version, new VersionDO())));
    return versionDOList;
  }

  @Transactional
  public VersionDO createOrUpdate(VersionDO versionDO) {
    if (versionDO.getId() != null) return update(versionDO);
    else return create(versionDO);
  }

  @Transactional
  public VersionDO create(VersionDO versionDO) {
    log.info("Creating new DMP Version");
    versionDO.setRevisionNumber(getCurrentRevisionNumber().longValue());
    DmpVersion version = VersionDOMapper.mapDOtoEntity(versionDO, new DmpVersion(), dmpRepo);
    version.setVersionDate(new Date());
    version.persist();
    return getVersionById(version.id);
  }

  @Transactional
  public VersionDO update(VersionDO versionDO) {
    log.info("Updating DMP Version with id " + versionDO.getId());
    DmpVersion version = dmpVersionRepo.findById(versionDO.getId());
    VersionDOMapper.mapDOtoEntity(versionDO, version, dmpRepo);
    version.persist();
    return getVersionById(version.id);
  }

  public void delete(long versionId) {
    log.info("Deleting DMP Version with id " + versionId);
    dmpVersionRepo.deleteById(versionId);
  }

  private VersionDO getVersionById(long versionId) {
    return VersionDOMapper.mapEntityToDO(dmpVersionRepo.findById(versionId), new VersionDO());
  }

  private Number getCurrentRevisionNumber() {
    AuditReader reader = AuditReaderFactory.get(dmpRepo.getEntityManager());
    return reader.getRevisionNumberForDate(new Date());
  }
}

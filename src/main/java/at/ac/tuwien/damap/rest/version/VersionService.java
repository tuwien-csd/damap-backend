package at.ac.tuwien.damap.rest.version;

import at.ac.tuwien.damap.domain.DmpVersion;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.repo.DmpVersionRepo;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@JBossLog
public class VersionService {

    @Inject
    DmpVersionRepo dmpVersionRepo;

    @Inject
    DmpRepo dmpRepo;

    public List<VersionDO> getDmpVersions(long dmpId) {
        List<VersionDO> versionDOList = new ArrayList<>();
        dmpVersionRepo.getAllByDmp(dmpId).forEach(version ->
                versionDOList.add(VersionDOMapper.mapEntityToDO(version, new VersionDO())));
        return versionDOList;
    }

    public VersionDO createOrUpdate(VersionDO versionDO){
        if (versionDO.getId() != null)
            return update(versionDO);
        else
            return create(versionDO);
    }

    @Transactional
    public VersionDO create(VersionDO versionDO) {
        log.info("Creating new DMP Version");
        DmpVersion version = VersionDOMapper.mapDOtoEntity(versionDO, new DmpVersion(), dmpRepo);
        version.setVersionDate(new Date());
        version.setRevisionNumber(getCurrentRevisionNumber().longValue());
        version.persistAndFlush();
        return getVersionById(version.id);
    }

    public VersionDO update(VersionDO versionDO) {
        log.info("Updating DMP Version with id " + versionDO.getId());
        DmpVersion version = dmpVersionRepo.findById(versionDO.getId());
        VersionDOMapper.mapDOtoEntity(versionDO, version, dmpRepo);
        return getVersionById(version.id);
    }

    public void delete(long versionId) {
        log.info("Deleting DMP Version with id " + versionId);
        dmpVersionRepo.deleteById(versionId);
    }

    private VersionDO getVersionById(long versionId) {
        return VersionDOMapper.mapEntityToDO(dmpVersionRepo.findById(versionId), new VersionDO());
    }

    private Number getCurrentRevisionNumber(){
        AuditReader reader = AuditReaderFactory.get(dmpRepo.getEntityManager());
        return reader.getRevisionNumberForDate(new Date());
    }
}

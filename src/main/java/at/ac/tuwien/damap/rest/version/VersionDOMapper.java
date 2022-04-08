package at.ac.tuwien.damap.rest.version;

import at.ac.tuwien.damap.domain.DmpVersion;
import at.ac.tuwien.damap.repo.DmpRepo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VersionDOMapper {

    public VersionDO mapEntityToDO(DmpVersion version, VersionDO versionDO) {
        versionDO.setId(version.id);
        versionDO.setDmpId(version.getDmp().id);
        versionDO.setRevisionNumber(version.getRevisionNumber());
        versionDO.setVersionName(version.getVersionName());
        versionDO.setVersionDate(version.getVersionDate());

        return versionDO;
    }

    public DmpVersion mapDOtoEntity(VersionDO versionDO, DmpVersion version, DmpRepo dmpRepo){
        if (versionDO.getId() != null)
            version.id = versionDO.getId();
        version.setDmp(dmpRepo.findById(versionDO.getDmpId()));
        if (versionDO.getRevisionNumber() != null)
            version.setRevisionNumber(versionDO.getRevisionNumber());
        version.setVersionName(versionDO.getVersionName());
        version.setVersionDate(versionDO.getVersionDate());

        return version;
    }
}

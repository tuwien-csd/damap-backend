package at.ac.tuwien.damap.rest.invenio_damap;

import java.text.MessageFormat;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.madmp.dto.Dataset;
import at.ac.tuwien.damap.rest.version.VersionDO;
import at.ac.tuwien.damap.rest.version.VersionService;

@ApplicationScoped
public class InvenioDAMAPService {

    protected DmpService dmpService;
    protected VersionService versionService;

    @Inject
    InvenioDAMAPService(DmpService dmpService, VersionService versionService) {
        this.dmpService = dmpService;
        this.versionService = versionService;
    }

    @Transactional
    public DmpDO addDataSetToDMP(long dmpId, Dataset dataset) {

        DmpDO dmpDO = dmpService.getDmpById(dmpId);
        var datasetDO = dmpDO.getDatasets().stream().filter(ds -> {
            var localIdentifier = ds.getDatasetId();
            var externalIdentifier = dataset.getDatasetId();

            if (localIdentifier == null || externalIdentifier == null) {
                return false;
            }

            return localIdentifier.getIdentifier() != null && externalIdentifier.getIdentifier() != null
                    && localIdentifier.getType() != null && externalIdentifier.getType() != null
                    && localIdentifier.getIdentifier().equals(externalIdentifier.getIdentifier())
                    && localIdentifier.getType().toString().equalsIgnoreCase(externalIdentifier.getType().name());
        }).findFirst().orElse(new DatasetDO());

        datasetDO = InvenioDamapResourceMapper.mapMaDMPDatasetToDatasetDO(dataset, datasetDO, dmpDO);

        dmpDO.getDatasets().add(datasetDO);
        dmpDO = dmpService.update(dmpDO);

        VersionDO version = new VersionDO();
        version.setDmpId(dmpId);
        version.setVersionName(MessageFormat.format("Added dataset `{0}` from remote datasource", dataset.getTitle()));
        version.setVersionDate(new Date());
        versionService.create(version);

        return dmpDO;
    }
}

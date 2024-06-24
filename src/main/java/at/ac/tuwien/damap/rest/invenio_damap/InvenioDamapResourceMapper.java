package at.ac.tuwien.damap.rest.invenio_damap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import at.ac.tuwien.damap.enums.EAccessRight;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.EDataKind;
import at.ac.tuwien.damap.enums.EDataSource;
import at.ac.tuwien.damap.enums.EDataType;
import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.enums.ELicense;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.ExternalStorageDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.madmp.dto.Dataset;
import at.ac.tuwien.damap.rest.madmp.dto.Host;
import lombok.experimental.UtilityClass;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@UtilityClass
public class InvenioDamapResourceMapper {
    public DatasetDO mapMaDMPDatasetToDatasetDO(Dataset madmpDataset, DatasetDO datasetDO, DmpDO dmpDO) {

        // Disclaimer: This is by no means complete. Not all fields of the
        // Dataset or DMP are set. Null value checks should also be performed.
        var datasetId = madmpDataset.getDatasetId();
        if (datasetId != null) {
            IdentifierDO newId = new IdentifierDO();
            newId.setIdentifier(datasetId.getIdentifier());
            newId.setType(EIdentifierType.valueOf(datasetId.getType().name().toUpperCase()));
            datasetDO.setDatasetId(newId);
        }

        datasetDO.setReferenceHash((new Date()).toString());
        datasetDO.setDateOfDeletion(null);
        datasetDO.setDelete(false);
        datasetDO.setDeletionPerson(null);
        datasetDO.setDescription(madmpDataset.getDescription());
        datasetDO.setLegalRestrictions(null);
        datasetDO.setLicense(null);
        datasetDO.setSize(0L);

        mapDistribution(madmpDataset, datasetDO, dmpDO);

        datasetDO.setOtherProjectMembersAccess(EAccessRight.READ);

        Boolean personalData = true;
        madmpDataset.setPersonalData(
                Objects.requireNonNullElse(madmpDataset.getPersonalData(), Dataset.PersonalData.UNKNOWN));
        switch (madmpDataset.getPersonalData()) {
            case NO:
                personalData = false;
                break;
            case UNKNOWN, YES:
            default:
                personalData = true;
                break;
        }
        datasetDO.setPersonalData(personalData);
        dmpDO.setPersonalData(dmpDO.getPersonalData() || personalData);

        datasetDO.setPublicAccess(EAccessRight.READ);
        datasetDO.setReasonForDeletion("");

        datasetDO.setRetentionPeriod(null);
        datasetDO.setSelectedProjectMembersAccess(EAccessRight.READ);

        Boolean sensitiveData = true;
        madmpDataset.setSensitiveData(
                Objects.requireNonNullElse(madmpDataset.getSensitiveData(), Dataset.SensitiveData.UNKNOWN));
        switch (madmpDataset.getSensitiveData()) {
            case NO:
                sensitiveData = false;
                break;
            case UNKNOWN, YES:
            default:
                sensitiveData = true;
                break;
        }
        datasetDO.setSensitiveData(sensitiveData);
        dmpDO.setSensitiveData(dmpDO.getSensitiveData() || sensitiveData);

        // TODO: Let user decide?
        datasetDO.setSource(EDataSource.NEW);
        // This should match the dataset. If new, setDataKind. else setReusedDataKind
        dmpDO.setDataKind(EDataKind.SPECIFY);
        dmpDO.setReusedDataKind(EDataKind.SPECIFY);
        datasetDO.setStartDate(null);
        datasetDO.setTitle(madmpDataset.getTitle());
        // Setting data type
        var types = new ArrayList<EDataType>();
        var type = EDataType.OTHER;
        try {
            type = EDataType.getByValue(madmpDataset.getType());
        } catch (Exception e) {
            log.info("Could not infer EDataType from provided value: " + madmpDataset.getType());
        } finally {
            types.add(type);
            datasetDO.setType(types);
        }

        return datasetDO;
    }

    private static void mapDistribution(Dataset madmpDataset, DatasetDO datasetDO, DmpDO dmpDO) {
        if (madmpDataset.getDistribution() == null) {
            return;
        }

        var distributions = madmpDataset.getDistribution();
        StringBuilder licenseBuilder = new StringBuilder();
        distributions.forEach(d -> {
            var dataAccess = EDataAccessType.OPEN;
            if (d.getDataAccess() != null) {
                dataAccess = EDataAccessType.getByValue(d.getDataAccess().value());
            }
            datasetDO.setDataAccess(dataAccess);
            licenseBuilder.append(d.getLicense().stream()
                    .map(l -> l.getLicenseRef().toString()).collect(Collectors.joining(", ")));
            datasetDO.setSize(datasetDO.getSize() + d.getByteSize());

            if (d.getHost() == null) {
                // Nothing more to do
                return;
            }

            Host host = d.getHost();
            String hostPath = host.getUrl() == null ? null : host.getUrl().getPath();

            // Check if the provided storage is already set on the DMP.
            var externalStorages = dmpDO.getExternalStorage();
            ExternalStorageDO externalStorageDO = externalStorages.stream()
                    .filter(s -> s.getUrl() != null && s.getUrl().equals(hostPath))
                    .findFirst()
                    .orElse(null);

            if (externalStorageDO == null) {
                externalStorageDO = new ExternalStorageDO();
                externalStorageDO.setBackupFrequency(host.getBackupFrequency());
                externalStorageDO.setStorageLocation(
                        host.getGeoLocation() != null ? host.getGeoLocation().toString() : null);
                externalStorageDO.setTitle(host.getTitle());
                externalStorageDO.setUrl(hostPath);
                externalStorages.add(externalStorageDO);
            }

            var datasetHashes = externalStorageDO.getDatasets();
            datasetHashes.add(datasetDO.getReferenceHash());
            externalStorageDO.setDatasets(datasetHashes);

            dmpDO.setExternalStorage(externalStorages);

        });

        // TODO: Support multiple licenses
        ELicense license = Arrays.stream(ELicense.values())
                .filter(eLicense -> eLicense.getUrl().equals(licenseBuilder.toString()))
                .findFirst()
                .orElse(null);
        datasetDO.setLicense(license);
    }
}

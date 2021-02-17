package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.rest.domain.*;

public class DatasetDOMapper {

    public static void mapAtoB(Dataset dataset, DatasetDO datasetDO) {
        datasetDO.setId(dataset.id);
        datasetDO.setVersion(dataset.getVersion());
        datasetDO.setTitle(dataset.getTitle());
        datasetDO.setType(dataset.getType());
        datasetDO.setSize(dataset.getSize());
        datasetDO.setComment(dataset.getComment());
        datasetDO.setPublish(dataset.isPublish());
        datasetDO.setLicense(dataset.getLicense());
        datasetDO.setStart(dataset.getStart());
        datasetDO.setReferenceHash(dataset.getReferenceHash());

        HostDO hostDO = new HostDO();
        HostDOMapper.mapAtoB(dataset.getHost(), hostDO);
        datasetDO.setHost(hostDO);
    }
}

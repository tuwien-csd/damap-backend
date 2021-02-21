package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.rest.domain.*;

public class DatasetDOMapper {

    public static void mapEntityToDO(Dataset dataset, DatasetDO datasetDO) {
        datasetDO.setTitle(dataset.getTitle());
        datasetDO.setType(dataset.getType());
        datasetDO.setSize(dataset.getSize());
        datasetDO.setComment(dataset.getComment());
        datasetDO.setPublish(dataset.isPublish());
        datasetDO.setLicense(dataset.getLicense());
        datasetDO.setStartDate(dataset.getStart());
        datasetDO.setReferenceHash(dataset.getReferenceHash());
    }

    public static void mapDOtoEntity(DatasetDO datasetDO, Dataset dataset){
        dataset.setTitle(datasetDO.getTitle());
        dataset.setType(datasetDO.getType());
        dataset.setSize(datasetDO.getSize());
        dataset.setComment(datasetDO.getComment());
        dataset.setPublish(datasetDO.isPublish());
        dataset.setLicense(datasetDO.getLicense());
        dataset.setStart(datasetDO.getStartDate());
        dataset.setReferenceHash(datasetDO.getReferenceHash());
    }}

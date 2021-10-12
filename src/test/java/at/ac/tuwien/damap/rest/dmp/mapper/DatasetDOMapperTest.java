package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.enums.EAccessRight;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class DatasetDOMapperTest {

    @Test
    void testMapEntityToDO(){
        Dataset dataset = this.createDatasetEntity();
        Assertions.assertEquals(DatasetDOMapper.mapEntityToDO(dataset, new DatasetDO()), this.createDatasetDO());
    }

    @Test
    void testMapDOToEntity(){
        DatasetDO datasetDO = this.createDatasetDO();
        Assertions.assertEquals(DatasetDOMapper.mapDOtoEntity(datasetDO, new Dataset()), this.createDatasetEntity());
    }

    private Dataset createDatasetEntity(){
        Dataset dataset = new Dataset();
        dataset.setTitle("Mock Dataset");
        dataset.setType("IMAGES");
        dataset.setSize(3L);
        dataset.setComment("Mock Comment");
        dataset.setPersonalData(false);
        dataset.setSensitiveData(true);
        dataset.setLegalRestrictions(false);
        dataset.setLicense("any License");
        dataset.setStart(new Date(100000));
        dataset.setReferenceHash("hex-hash");
        dataset.setDataAccess(EDataAccessType.CLOSED);
        dataset.setSelectedProjectMembersAccess(EAccessRight.WRITE);
        dataset.setOtherProjectMembersAccess(EAccessRight.READ);
        dataset.setPublicAccess(null);
        return dataset;
    }
    private DatasetDO createDatasetDO(){
        DatasetDO datasetDO = new DatasetDO();
        datasetDO.setTitle("Mock Dataset");
        datasetDO.setType("IMAGES");
        datasetDO.setSize(3L);
        datasetDO.setComment("Mock Comment");
        datasetDO.setPersonalData(false);
        datasetDO.setSensitiveData(true);
        datasetDO.setLegalRestrictions(false);
        datasetDO.setLicense("any License");
        datasetDO.setStartDate(new Date(100000));
        datasetDO.setReferenceHash("hex-hash");
        datasetDO.setDataAccess("Closed");
        datasetDO.setSelectedProjectMembersAccess("writing");
        datasetDO.setOtherProjectMembersAccess("reading only");
        datasetDO.setPublicAccess(null);
        return datasetDO;
    }
}

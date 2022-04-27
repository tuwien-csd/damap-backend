package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.enums.EAccessRight;
import at.ac.tuwien.damap.enums.EDataAccessType;
import at.ac.tuwien.damap.enums.EDataType;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

@QuarkusTest
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
        dataset.id = -1L;
        dataset.setTitle("Mock Dataset");
        dataset.setType(List.of(EDataType.IMAGES));
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
        dataset.setDelete(true);
        dataset.setDateOfDeletion(new Date(200000));
        dataset.setReasonForDeletion("because");
        dataset.setRetentionPeriod(10);
        return dataset;
    }
    private DatasetDO createDatasetDO(){
        DatasetDO datasetDO = new DatasetDO();
        datasetDO.setId(-1L);
        datasetDO.setTitle("Mock Dataset");
        datasetDO.setType(List.of(EDataType.IMAGES));
        datasetDO.setSize(3L);
        datasetDO.setComment("Mock Comment");
        datasetDO.setPersonalData(false);
        datasetDO.setSensitiveData(true);
        datasetDO.setLegalRestrictions(false);
        datasetDO.setLicense("any License");
        datasetDO.setStartDate(new Date(100000));
        datasetDO.setReferenceHash("hex-hash");
        datasetDO.setDataAccess(EDataAccessType.CLOSED);
        datasetDO.setSelectedProjectMembersAccess(EAccessRight.WRITE);
        datasetDO.setOtherProjectMembersAccess(EAccessRight.READ);
        datasetDO.setPublicAccess(null);
        datasetDO.setDelete(true);
        datasetDO.setDateOfDeletion(new Date(200000));
        datasetDO.setReasonForDeletion("because");
        datasetDO.setRetentionPeriod(10);
        return datasetDO;
    }
}

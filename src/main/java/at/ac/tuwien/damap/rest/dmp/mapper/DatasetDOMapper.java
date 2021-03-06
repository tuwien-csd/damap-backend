package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Dataset;
import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.enums.EDataType;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DatasetDOMapper {

    public DatasetDO mapEntityToDO(Dataset dataset, DatasetDO datasetDO) {
        datasetDO.setId(dataset.id);
        datasetDO.setTitle(dataset.getTitle());
        datasetDO.setSize(dataset.getSize());
        datasetDO.setDescription(dataset.getDescription());
        datasetDO.setPersonalData(dataset.getPersonalData());
        datasetDO.setSensitiveData(dataset.getSensitiveData());
        datasetDO.setLegalRestrictions(dataset.getLegalRestrictions());
        datasetDO.setLicense(dataset.getLicense());
        datasetDO.setStartDate(dataset.getStart());
        datasetDO.setReferenceHash(dataset.getReferenceHash());
        datasetDO.setDelete(dataset.getDelete());
        datasetDO.setDateOfDeletion(dataset.getDateOfDeletion());
        datasetDO.setReasonForDeletion(dataset.getReasonForDeletion());
        datasetDO.setRetentionPeriod(dataset.getRetentionPeriod());
        datasetDO.setSource(dataset.getSource());
        if (dataset.getDataAccess() != null)
            datasetDO.setDataAccess(dataset.getDataAccess());
        if (dataset.getSelectedProjectMembersAccess() != null)
            datasetDO.setSelectedProjectMembersAccess(dataset.getSelectedProjectMembersAccess());
        if (dataset.getOtherProjectMembersAccess() != null)
            datasetDO.setOtherProjectMembersAccess(dataset.getOtherProjectMembersAccess());
        if (dataset.getPublicAccess() != null)
            datasetDO.setPublicAccess(dataset.getPublicAccess());
        if (dataset.getDeletionPerson() != null)
            datasetDO.setDeletionPerson(ContributorDOMapper.mapEntityToDO(dataset.getDeletionPerson(), new ContributorDO()));
        if (dataset.getDatasetIdentifier() != null)
            datasetDO.setDatasetId(IdentifierDOMapper.mapEntityToDO(dataset.getDatasetIdentifier(), new IdentifierDO()));

        List<EDataType> typeList = new ArrayList<>();
        dataset.getType().forEach(option -> {
            if (option != null) {
                typeList.add(option);
            }
        });
        datasetDO.setType(typeList);

        return datasetDO;
    }

    public Dataset mapDOtoEntity(DatasetDO datasetDO, Dataset dataset, MapperService mapperService) {
        if (datasetDO.getId() != null)
            dataset.id = datasetDO.getId();
        dataset.setTitle(datasetDO.getTitle());
        dataset.setSize(datasetDO.getSize());
        dataset.setDescription(datasetDO.getDescription());
        dataset.setPersonalData(datasetDO.getPersonalData());
        dataset.setSensitiveData(datasetDO.getSensitiveData());
        dataset.setLegalRestrictions(datasetDO.getLegalRestrictions());
        dataset.setLicense(datasetDO.getLicense());
        dataset.setStart(datasetDO.getStartDate());
        dataset.setReferenceHash(datasetDO.getReferenceHash());
        dataset.setDataAccess(datasetDO.getDataAccess());
        dataset.setSelectedProjectMembersAccess(datasetDO.getSelectedProjectMembersAccess());
        dataset.setOtherProjectMembersAccess(datasetDO.getOtherProjectMembersAccess());
        dataset.setPublicAccess(datasetDO.getPublicAccess());
        dataset.setDelete(datasetDO.getDelete());
        dataset.setDateOfDeletion(datasetDO.getDateOfDeletion());
        dataset.setReasonForDeletion(datasetDO.getReasonForDeletion());
        dataset.setRetentionPeriod(datasetDO.getRetentionPeriod());
        dataset.setSource(datasetDO.getSource());
        if (datasetDO.getDeletionPerson() != null && datasetDO.getDeletionPerson().getId() != null)
            dataset.setDeletionPerson(mapperService.getDeletionPerson(datasetDO.getDeletionPerson().getId()));
        else
            dataset.setDeletionPerson(null);

        if (datasetDO.getDatasetId() != null)
            dataset.setDatasetIdentifier(IdentifierDOMapper.mapDOtoEntity(datasetDO.getDatasetId(), new Identifier()));

        List<EDataType> typeList = new ArrayList<>();
        datasetDO.getType().forEach(option -> {
            if (option != null) {
                typeList.add(option);
            }
        });
        dataset.setType(typeList);

        return dataset;
    }}

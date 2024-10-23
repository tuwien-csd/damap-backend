package org.damap.base.rest.storage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.domain.InternalStorage;
import org.damap.base.domain.InternalStorageTranslation;
import org.damap.base.repo.InternalStorageRepo;
import org.damap.base.repo.InternalStorageTranslationRepo;
import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.service.ServiceCreate;
import org.damap.base.rest.base.service.ServiceDelete;
import org.damap.base.rest.base.service.ServiceRead;
import org.damap.base.rest.base.service.ServiceSearch;
import org.damap.base.rest.base.service.ServiceUpdate;

@ApplicationScoped
@JBossLog
public class InternalStorageTranslationService
    implements ServiceCreate<InternalStorageTranslationDO, InternalStorageTranslationDO>,
        ServiceDelete,
        ServiceRead<InternalStorageTranslationDO>,
        ServiceSearch<InternalStorageTranslationDO>,
        ServiceUpdate<InternalStorageTranslationDO, InternalStorageTranslationDO> {

  @Inject InternalStorageTranslationRepo internalStorageTranslationRepo;

  @Inject InternalStorageRepo internalStorageRepo;

  @Override
  @Transactional
  public InternalStorageTranslationDO create(InternalStorageTranslationDO data)
      throws ClientErrorException {
    InternalStorageTranslationValidator.validateForCreation(
        data, internalStorageRepo, internalStorageTranslationRepo);

    InternalStorage internalStorage = internalStorageRepo.findById(data.getStorageId());

    InternalStorageTranslation internalStorageTranslation =
        InternalStorageTranslationDOMapper.mapDOToTranslationEntityForCreation(
            data, internalStorage);
    internalStorageTranslation.persistAndFlush();

    return getInternalStorageTranslationById(internalStorageTranslation.id);
  }

  @Override
  public InternalStorageTranslationDO read(String id, MultivaluedMap<String, String> queryParams)
      throws NumberFormatException {

    InternalStorageTranslation internalStorageTranslation =
        internalStorageTranslationRepo.findById(Long.parseLong(id));

    if (internalStorageTranslation == null) {
      throw new NotFoundException("No internal storage translation found for id " + id);
    }

    return InternalStorageTranslationDOMapper.mapEntityToDO(
        internalStorageTranslation, new InternalStorageTranslationDO());
  }

  @Override
  public InternalStorageTranslationDO read(String id) {
    return this.read(id, null);
  }

  @Override
  @Transactional
  public InternalStorageTranslationDO update(String id, InternalStorageTranslationDO data) {

    InternalStorageTranslationValidator.validateForUpdate(
        id, data, internalStorageTranslationRepo, internalStorageRepo);

    InternalStorageTranslation internalStorageTranslation =
        internalStorageTranslationRepo.findById(Long.parseLong(id));
    internalStorageTranslation =
        InternalStorageTranslationDOMapper.mapDOToEntity(data, internalStorageTranslation);
    internalStorageTranslation.persistAndFlush();

    return getInternalStorageTranslationById(internalStorageTranslation.id);
  }

  @Override
  @Transactional
  public void delete(String id) {

    InternalStorageTranslation internalStorageTranslation =
        internalStorageTranslationRepo.findById(Long.parseLong(id));

    if (internalStorageTranslation == null) {
      throw new NotFoundException("No internal storage translation found for id " + id);
    }

    if (internalStorageTranslationRepo
            .getAllInternalStorageTranslationsByStorageId(
                internalStorageTranslation.getInternalStorageId().id)
            .size()
        == 1) {
      throw new ClientErrorException(
          "Cannot delete the last translation for an internal storage", 400);
    }

    internalStorageTranslationRepo.delete(internalStorageTranslation);
  }

  @Override
  public ResultList<InternalStorageTranslationDO> search(
      MultivaluedMap<String, String> queryParams) {
    return null;
  }

  public List<InternalStorageTranslationDO> getAllByStorageId(String storageId) {

    if (!InternalStorageValidator.storageIdExists(Long.valueOf(storageId))) {
      throw new NotFoundException("No internal storage found for id " + storageId);
    }

    List<InternalStorageTranslation> translations =
        internalStorageTranslationRepo.getAllInternalStorageTranslationsByStorageId(
            Long.valueOf(storageId));

    return translations.stream()
        .map(
            translation ->
                InternalStorageTranslationDOMapper.mapEntityToDO(
                    translation, new InternalStorageTranslationDO()))
        .toList();
  }

  @Transactional
  public InternalStorageTranslationDO getInternalStorageTranslationById(
      long internalStorageTranslationId) {
    return InternalStorageTranslationDOMapper.mapEntityToDO(
        internalStorageTranslationRepo.findById(internalStorageTranslationId),
        new InternalStorageTranslationDO());
  }
}

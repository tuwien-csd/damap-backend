package org.damap.base.rest.dmp.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.domain.*;
import org.damap.base.r3data.RepositoriesService;
import org.damap.base.repo.*;
import org.damap.base.rest.storage.InternalStorageDO;
import org.damap.base.rest.storage.InternalStorageDOMapper;
import org.re3data.schema._2_2.Re3Data;

/*
   Service for injecting other services into mappers which are static
*/

/** MapperService class. */
@ApplicationScoped
@JBossLog
public class MapperService {

  @Inject InternalStorageRepo internalStorageRepo;

  @Inject InternalStorageTranslationRepo internalStorageTranslationRepo;

  @Inject RepositoriesService repositoriesService;

  @Inject ContributorRepo contributorRepo;

  @Inject DmpRepo dmpRepo;

  /**
   * getDmpById.
   *
   * @param id a {@link java.lang.Long} object
   * @return a {@link org.damap.base.domain.Dmp} object
   */
  public Dmp getDmpById(Long id) {
    return dmpRepo.findById(id);
  }

  /**
   * getRe3DataRepository.
   *
   * @param id a {@link java.lang.String} object
   * @return a {@link org.re3data.schema._2_2.Re3Data.Repository} object
   */
  public Re3Data.Repository getRe3DataRepository(String id) {
    return repositoriesService.getById(id).getRepository().get(0);
  }

  /**
   * getInternalStorageById.
   *
   * @param id a {@link java.lang.Long} object
   * @return a {@link org.damap.base.domain.InternalStorage} object
   */
  public InternalStorage getInternalStorageById(Long id) {
    return internalStorageRepo.findById(id);
  }

  /**
   * getInternalStorageDOById.
   *
   * @param id a {@link java.lang.Long} object
   * @param languageCode a {@link java.lang.String} object
   * @return a {@link org.damap.base.rest.storage.InternalStorageDO} object
   */
  public InternalStorageDO getInternalStorageDOById(Long id, String languageCode) {

    InternalStorage internalStorage = internalStorageRepo.findById(id);
    if (internalStorage == null) {
      return null;
    }

    List<InternalStorageTranslation> translations =
        internalStorageTranslationRepo.getAllInternalStorageTranslationsByStorageId(id);

    return InternalStorageDOMapper.mapEntityToDO(
        internalStorage, new InternalStorageDO(), translations);
  }

  /**
   * getDeletionPerson.
   *
   * @param id a {@link java.lang.Long} object
   * @return a {@link org.damap.base.domain.Contributor} object
   */
  public Contributor getDeletionPerson(Long id) {
    return contributorRepo.findById(id);
  }
}

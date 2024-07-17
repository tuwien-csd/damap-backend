package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Repository;
import org.damap.base.rest.dmp.domain.RepositoryDO;

/** RepositoryDOMapper class. */
@UtilityClass
public class RepositoryDOMapper {

  /**
   * mapEntityToDO.
   *
   * @param repository a {@link org.damap.base.domain.Repository} object
   * @param repositoryDO a {@link org.damap.base.rest.dmp.domain.RepositoryDO} object
   * @return a {@link org.damap.base.rest.dmp.domain.RepositoryDO} object
   */
  public RepositoryDO mapEntityToDO(Repository repository, RepositoryDO repositoryDO) {
    repositoryDO.setRepositoryId(repository.getRepositoryId());
    return repositoryDO;
  }

  /**
   * mapDOtoEntity.
   *
   * @param repositoryDO a {@link org.damap.base.rest.dmp.domain.RepositoryDO} object
   * @param repository a {@link org.damap.base.domain.Repository} object
   * @return a {@link org.damap.base.domain.Repository} object
   */
  public Repository mapDOtoEntity(RepositoryDO repositoryDO, Repository repository) {
    repository.setRepositoryId(repositoryDO.getRepositoryId());
    return repository;
  }
}

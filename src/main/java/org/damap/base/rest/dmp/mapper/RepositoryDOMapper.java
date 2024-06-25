package org.damap.base.rest.dmp.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.domain.Repository;
import org.damap.base.rest.dmp.domain.RepositoryDO;

@UtilityClass
public class RepositoryDOMapper {

  public RepositoryDO mapEntityToDO(Repository repository, RepositoryDO repositoryDO) {
    repositoryDO.setRepositoryId(repository.getRepositoryId());
    return repositoryDO;
  }

  public Repository mapDOtoEntity(RepositoryDO repositoryDO, Repository repository) {
    repository.setRepositoryId(repositoryDO.getRepositoryId());
    return repository;
  }
}

package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Repository;
import at.ac.tuwien.damap.rest.dmp.domain.RepositoryDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RepositoryDOMapper {

    public RepositoryDO mapEntityToDO(Repository repository, RepositoryDO repositoryDO) {
        repositoryDO.setRepositoryId(repository.getRepositoryId());
        return repositoryDO;
    }

    public Repository mapDOtoEntity(RepositoryDO repositoryDO, Repository repository){
        repository.setRepositoryId(repositoryDO.getRepositoryId());
        return repository;
    }
}

package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Contributor;
import at.ac.tuwien.damap.domain.InternalStorage;
import at.ac.tuwien.damap.domain.InternalStorageTranslation;
import at.ac.tuwien.damap.r3data.RepositoriesService;
import at.ac.tuwien.damap.repo.ContributorRepo;
import at.ac.tuwien.damap.repo.InternalStorageRepo;
import at.ac.tuwien.damap.repo.InternalStorageTranslationRepo;
import at.ac.tuwien.damap.rest.storage.InternalStorageDO;
import at.ac.tuwien.damap.rest.storage.InternalStorageDOMapper;
import lombok.extern.jbosslog.JBossLog;
import org.re3data.schema._2_2.Re3Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/*
    Service for injecting other services into mappers which are static
 */

@ApplicationScoped
@JBossLog
public class MapperService {

    @Inject
    InternalStorageRepo internalStorageRepo;

    @Inject
    InternalStorageTranslationRepo internalStorageTranslationRepo;

    @Inject
    RepositoriesService repositoriesService;

    @Inject
    ContributorRepo contributorRepo;


    public Re3Data.Repository getRe3DataRepository(String id) {
        return repositoriesService.getById(id).getRepository().get(0);
    }

    public InternalStorage getInternalStorageById(Long id) {
        return internalStorageRepo.findById(id);
    }

    public InternalStorageDO getInternalStorageDOById(Long id, String languageCode) {
        InternalStorageTranslation internalStorageTranslation = internalStorageTranslationRepo.getInternalStorageById(id, languageCode);
        if (internalStorageTranslation != null)
            return InternalStorageDOMapper.mapEntityToDO(internalStorageTranslation, new InternalStorageDO());
        return null;
    }

    public Contributor getDeletionPerson(Long id) {
        return contributorRepo.findById(id);
    }
}

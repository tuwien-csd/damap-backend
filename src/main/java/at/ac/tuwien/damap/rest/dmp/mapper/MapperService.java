package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.r3data.RepositoriesService;
import at.ac.tuwien.damap.repo.*;
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

    @Inject
    DmpRepo dmpRepo;

    public Dmp getDmpById(Long id) {
        return dmpRepo.findById(id);
    }

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

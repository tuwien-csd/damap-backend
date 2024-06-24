package org.damap.base.rest.dmp.mapper;

import org.damap.base.domain.*;
import org.damap.base.r3data.RepositoriesService;
import org.damap.base.repo.*;
import org.damap.base.rest.storage.InternalStorageDO;
import org.damap.base.rest.storage.InternalStorageDOMapper;
import lombok.extern.jbosslog.JBossLog;
import org.re3data.schema._2_2.Re3Data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

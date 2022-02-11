package at.ac.tuwien.damap.rest.madmp.service;

import at.ac.tuwien.damap.r3data.RepositoriesService;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.mapper.DmpDOMapper;
import at.ac.tuwien.damap.rest.madmp.dto.Dmp;
import at.ac.tuwien.damap.rest.madmp.mapper.MaDmpMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MaDmpService {

    @Inject
    DmpRepo dmpRepo;

    @Inject
    RepositoriesService repositoriesService;

    public Dmp getById(long id) {
        // TODO: check permission
        return MaDmpMapper.mapToMaDmp(DmpDOMapper.mapEntityToDO(dmpRepo.findById(id), new DmpDO()), new Dmp(), repositoriesService);
    }
}

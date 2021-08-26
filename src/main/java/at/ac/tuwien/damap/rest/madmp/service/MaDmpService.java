package at.ac.tuwien.damap.rest.madmp.service;

import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.damap.rest.madmp.dto.MaDmp;
import at.ac.tuwien.damap.rest.madmp.mapper.MaDmpMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MaDmpService {

    @Inject
    DmpRepo dmpRepo;

    public MaDmp getById(long id) {
        // TODO: check permission
        return MaDmpMapper.mapEntityToMaDmp(dmpRepo.findById(id), new MaDmp());
    }
}

package at.ac.tuwien.rest.madmp.service;

import at.ac.tuwien.damap.domain.Dmp;
import at.ac.tuwien.damap.repo.DmpRepo;
import at.ac.tuwien.rest.madmp.dto.MaDmp;
import at.ac.tuwien.rest.madmp.mapper.MaDmpMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MaDmpService {

    @Inject
    DmpRepo dmpRepo;

    public MaDmp getById(long id) {
        Dmp dmp = dmpRepo.findById(id);
        MaDmp maDmp = new MaDmp();
        MaDmpMapper.mapEntityToMaDmp(dmp, maDmp);
        return maDmp;
    }
}

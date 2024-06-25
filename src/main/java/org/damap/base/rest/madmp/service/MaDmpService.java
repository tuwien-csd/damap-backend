package org.damap.base.rest.madmp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.damap.base.repo.DmpRepo;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.mapper.DmpDOMapper;
import org.damap.base.rest.dmp.mapper.MapperService;
import org.damap.base.rest.madmp.dto.Dmp;
import org.damap.base.rest.madmp.mapper.MaDmpMapper;

@ApplicationScoped
public class MaDmpService {

  @Inject DmpRepo dmpRepo;

  @Inject MapperService mapperService;

  public Dmp getById(long id) {
    // TODO: check permission
    return MaDmpMapper.mapToMaDmp(
        DmpDOMapper.mapEntityToDO(dmpRepo.findById(id), new DmpDO()), new Dmp(), mapperService);
  }
}

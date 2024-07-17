package org.damap.base.rest.madmp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.damap.base.repo.DmpRepo;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.mapper.DmpDOMapper;
import org.damap.base.rest.dmp.mapper.MapperService;
import org.damap.base.rest.madmp.dto.Dmp;
import org.damap.base.rest.madmp.mapper.MaDmpMapper;

/** MaDmpService class. */
@ApplicationScoped
public class MaDmpService {

  @Inject DmpRepo dmpRepo;

  @Inject MapperService mapperService;

  /**
   * getById.
   *
   * @param id a long
   * @return a {@link org.damap.base.rest.madmp.dto.Dmp} object
   */
  public Dmp getById(long id) {
    // TODO: check permission
    return MaDmpMapper.mapToMaDmp(
        DmpDOMapper.mapEntityToDO(dmpRepo.findById(id), new DmpDO()), new Dmp(), mapperService);
  }
}

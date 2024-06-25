package org.damap.base.rest.fits.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.rest.dmp.domain.MultipartBodyDO;
import org.damap.base.rest.fits.dto.MultipartBodyDTO;

@UtilityClass
public class MultipartBodyMapper {

  public MultipartBodyDTO mapAtoB(
      MultipartBodyDO multipartBodyDO, MultipartBodyDTO multipartBodyDTO) {
    multipartBodyDTO.file = multipartBodyDO.file;

    return multipartBodyDTO;
  }
}

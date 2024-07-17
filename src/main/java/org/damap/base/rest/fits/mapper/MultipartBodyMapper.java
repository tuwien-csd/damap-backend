package org.damap.base.rest.fits.mapper;

import lombok.experimental.UtilityClass;
import org.damap.base.rest.dmp.domain.MultipartBodyDO;
import org.damap.base.rest.fits.dto.MultipartBodyDTO;

/** MultipartBodyMapper class. */
@UtilityClass
public class MultipartBodyMapper {

  /**
   * mapAtoB.
   *
   * @param multipartBodyDO a {@link org.damap.base.rest.dmp.domain.MultipartBodyDO} object
   * @param multipartBodyDTO a {@link org.damap.base.rest.fits.dto.MultipartBodyDTO} object
   * @return a {@link org.damap.base.rest.fits.dto.MultipartBodyDTO} object
   */
  public MultipartBodyDTO mapAtoB(
      MultipartBodyDO multipartBodyDO, MultipartBodyDTO multipartBodyDTO) {
    multipartBodyDTO.file = multipartBodyDO.file;

    return multipartBodyDTO;
  }
}

package org.damap.base.rest.fits.mapper;

import org.damap.base.rest.dmp.domain.MultipartBodyDO;
import org.damap.base.rest.fits.dto.MultipartBodyDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MultipartBodyMapper {

    public MultipartBodyDTO mapAtoB(MultipartBodyDO multipartBodyDO, MultipartBodyDTO multipartBodyDTO) {
        multipartBodyDTO.file = multipartBodyDO.file;

        return multipartBodyDTO;
    }
}

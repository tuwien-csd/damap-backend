package at.ac.tuwien.rest.fits.mapper;

import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MultipartBodyMapper {

    public MultipartBodyDTO mapAtoB(MultipartBodyDO multipartBodyDO, MultipartBodyDTO multipartBodyDTO) {
        multipartBodyDTO.file = multipartBodyDO.file;

        return multipartBodyDTO;
    }
}

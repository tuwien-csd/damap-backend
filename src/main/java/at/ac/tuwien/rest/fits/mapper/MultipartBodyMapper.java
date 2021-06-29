package at.ac.tuwien.rest.fits.mapper;

import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;

public class MultipartBodyMapper {

    public static void mapAtoB(MultipartBodyDO multipartBodyDO, MultipartBodyDTO multipartBodyDTO) {
        multipartBodyDTO.file = multipartBodyDO.file;
    }
}

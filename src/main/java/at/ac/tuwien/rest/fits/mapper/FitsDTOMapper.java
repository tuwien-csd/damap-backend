package at.ac.tuwien.rest.fits.mapper;

import at.ac.tuwien.damap.rest.domain.MultipartBodyDO;
import at.ac.tuwien.rest.fits.dto.MultipartBodyDTO;

public class FitsDTOMapper {

    public static void mapAtoB(MultipartBodyDO multipartBodyDO, MultipartBodyDTO multipartBodyDTO) {
        multipartBodyDTO.file = multipartBodyDO.file;
        // multipartBodyDTO.filename = multipartBodyDO.file.getName();
    }
}

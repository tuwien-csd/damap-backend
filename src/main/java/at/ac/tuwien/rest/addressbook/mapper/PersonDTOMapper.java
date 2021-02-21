package at.ac.tuwien.rest.addressbook.mapper;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.rest.addressbook.dto.PersonDTO;

public class PersonDTOMapper {

    public static void mapAtoB(PersonDTO personDTO, PersonDO personDO){
        personDO.setUniversityId(personDTO.getId());
        personDO.setFirstName(personDTO.getFirstName());
        personDO.setLastName(personDTO.getLastName());
        personDO.setMbox(personDTO.getMainEmail());

        if(personDTO.getOrcid() != null) {
            IdentifierDO personIdentifierDO = new IdentifierDO();
            personIdentifierDO.setIdentifier(personDTO.getOrcid());
            personIdentifierDO.setType(EIdentifierType.ORCID);
            personDO.setPersonId(personIdentifierDO);
        }
    }
}

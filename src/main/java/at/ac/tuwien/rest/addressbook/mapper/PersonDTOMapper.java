package at.ac.tuwien.rest.addressbook.mapper;

import at.ac.tuwien.damap.enums.PersonIdType;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.damap.rest.domain.PersonIdDO;
import at.ac.tuwien.rest.addressbook.dto.PersonDTO;

public class PersonDTOMapper {

    public static void mapAtoB(PersonDTO personDTO, PersonDO personDO){
        personDO.setId(personDTO.getId());
        personDO.setFirstName(personDTO.getFirstName());
        personDO.setLastName(personDTO.getLastName());
        personDO.setMbox(personDTO.getMainEmail());

        if(personDTO.getOrcid() != null) {
            PersonIdDO personIdDO = new PersonIdDO();
            personIdDO.setIdentifier(personDTO.getOrcid());
            personIdDO.setType(PersonIdType.ORCID);
            personDO.setPersonId(personIdDO);
        }
    }
}

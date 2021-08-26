package at.ac.tuwien.damap.rest.addressbook.mapper;

import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;
import at.ac.tuwien.damap.rest.addressbook.dto.PersonDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonDTOMapper {

    public PersonDO mapAtoB(PersonDTO personDTO, PersonDO personDO){
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

        return personDO;
    }
}

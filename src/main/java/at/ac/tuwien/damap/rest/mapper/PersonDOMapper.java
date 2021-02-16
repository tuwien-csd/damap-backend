package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Person;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;

public class PersonDOMapper {

    public static void mapAtoB(Person person, PersonDO personDO) {

        personDO.setId(person.id);
        personDO.setFirstName(person.getFirstName());
        personDO.setLastName(person.getLastName());
        personDO.setMbox(person.getMbox());

        IdentifierDO identifierPersonDO = new IdentifierDO();
        IdentifierDOMapper.mapAtoB(person.getPersonIdentifier(), identifierPersonDO);
        personDO.setPersonId(identifierPersonDO);
    }
}

package at.ac.tuwien.damap.rest.mapper;

import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.domain.Person;
import at.ac.tuwien.damap.rest.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.domain.PersonDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonDOMapper {

    public PersonDO mapEntityToDO(Person person, PersonDO personDO) {
        personDO.setId(person.id);
        personDO.setFirstName(person.getFirstName());
        personDO.setLastName(person.getLastName());
        personDO.setMbox(person.getMbox());
        personDO.setUniversityId(person.getUniversityId());

        if (person.getPersonIdentifier() != null) {
            IdentifierDO identifierPersonDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(person.getPersonIdentifier(), identifierPersonDO);
            personDO.setPersonId(identifierPersonDO);
        }

        return personDO;
    }

    public Person mapDOtoEntity(PersonDO personDO, Person person){
        if (personDO.getId() != null)
            person.id = personDO.getId();
        person.setFirstName(personDO.getFirstName());
        person.setLastName(personDO.getLastName());
        person.setMbox(personDO.getMbox());
        person.setUniversityId(personDO.getUniversityId());

        if (personDO.getPersonId() != null) {
            Identifier identifierPerson = new Identifier();
            if (person.getPersonIdentifier() != null)
                identifierPerson = person.getPersonIdentifier();
            IdentifierDOMapper.mapDOtoEntity(personDO.getPersonId(), identifierPerson);
            person.setPersonIdentifier(identifierPerson);
        } else
            person.setPersonIdentifier(null);

        return person;
    }
}

package at.ac.tuwien.damap.rest.dmp.mapper;

import at.ac.tuwien.damap.domain.Identifier;
import at.ac.tuwien.damap.domain.Person;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonDOMapper {

    public PersonDO mapEntityToDO(Person person, PersonDO personDO) {
        personDO.setId(person.id);
        personDO.setFirstName(person.getFirstName());
        personDO.setLastName(person.getLastName());
        personDO.setMbox(person.getMbox());
        personDO.setUniversityId(person.getUniversityId());
        personDO.setAffiliation(person.getAffiliation());

        if (person.getPersonIdentifier() != null) {
            IdentifierDO identifierPersonDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(person.getPersonIdentifier(), identifierPersonDO);
            personDO.setPersonId(identifierPersonDO);
        }
        if (person.getAffiliationId() != null) {
            IdentifierDO affiliationIdentifierDO = new IdentifierDO();
            IdentifierDOMapper.mapEntityToDO(person.getAffiliationId(), affiliationIdentifierDO);
            personDO.setAffiliationId(affiliationIdentifierDO);
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
        person.setAffiliation(personDO.getAffiliation());

        if (personDO.getPersonId() != null) {
            Identifier identifierPerson = new Identifier();
            if (person.getPersonIdentifier() != null)
                identifierPerson = person.getPersonIdentifier();
            IdentifierDOMapper.mapDOtoEntity(personDO.getPersonId(), identifierPerson);
            person.setPersonIdentifier(identifierPerson);
        } else
            person.setPersonIdentifier(null);

        if (personDO.getAffiliationId() != null) {
            Identifier affiliationIdentifier = new Identifier();
            if (person.getAffiliationId() != null)
                affiliationIdentifier = person.getAffiliationId();
            IdentifierDOMapper.mapDOtoEntity(personDO.getAffiliationId(), affiliationIdentifier);
            person.setAffiliationId(affiliationIdentifier);
        } else
            person.setAffiliationId(null);

        return person;
    }
}

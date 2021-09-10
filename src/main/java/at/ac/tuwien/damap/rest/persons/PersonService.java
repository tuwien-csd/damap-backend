package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;

public interface PersonService {
    PersonDO getPersonById(String id);
}

package at.ac.tuwien.damap.rest.persons.service;

import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;

public interface PersonService {
    PersonDO getPersonById(String id);
}

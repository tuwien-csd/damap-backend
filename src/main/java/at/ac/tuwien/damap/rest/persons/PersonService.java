package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;

public interface PersonService {
    ContributorDO getPersonById(String id);
}

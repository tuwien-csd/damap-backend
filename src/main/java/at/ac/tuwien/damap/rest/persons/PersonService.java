package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;

import java.util.List;

public interface PersonService {
    ContributorDO getPersonById(String id);

    List<ContributorDO> getPersonSearchResult(String searchTerm);
}

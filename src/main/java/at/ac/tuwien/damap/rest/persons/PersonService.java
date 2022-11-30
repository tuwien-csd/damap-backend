package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.base.service.ServiceRead;
import at.ac.tuwien.damap.rest.base.service.ServiceSearch;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;

public interface PersonService extends ServiceRead<ContributorDO>, ServiceSearch<ContributorDO> {
}

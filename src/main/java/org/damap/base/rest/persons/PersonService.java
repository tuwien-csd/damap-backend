package org.damap.base.rest.persons;

import org.damap.base.rest.base.service.ServiceRead;
import org.damap.base.rest.base.service.ServiceSearch;
import org.damap.base.rest.dmp.domain.ContributorDO;

public interface PersonService extends ServiceRead<ContributorDO>, ServiceSearch<ContributorDO> {
}

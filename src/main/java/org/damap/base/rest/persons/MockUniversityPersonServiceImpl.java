package org.damap.base.rest.persons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.Search;
import org.damap.base.rest.dmp.domain.ContributorDO;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
public class MockUniversityPersonServiceImpl implements PersonService {

    @Inject
    @RestClient
    MockPersonRestService mockPersonRestService;

    @Override
    public ContributorDO read(String id, MultivaluedMap<String, String> queryParams) {
        return mockPersonRestService.getContributorDetails(id).get(0);
    }

    @Override
    public ResultList<ContributorDO> search(MultivaluedMap<String, String> queryParams) {
        var items = mockPersonRestService.getContributorSearchResult();
        var search = Search.fromMap(queryParams);

        return ResultList.fromItemsAndSearch(items, search);
    }
}

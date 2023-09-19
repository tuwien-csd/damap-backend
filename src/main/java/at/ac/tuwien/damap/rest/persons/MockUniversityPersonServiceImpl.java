package at.ac.tuwien.damap.rest.persons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.ac.tuwien.damap.rest.base.ResultList;
import at.ac.tuwien.damap.rest.base.Search;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;

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

        ResultList<ContributorDO> resultList = ResultList.fromItemsAndSearch(items, search);

        return resultList;
    }
}

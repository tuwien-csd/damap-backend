package at.ac.tuwien.damap.rest.persons;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

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
    public ContributorDO read(String id) {
        return mockPersonRestService.getContributorDetails(id).get(0);
    }

    @Override
    public ResultList<ContributorDO> search(Search search, MultivaluedMap<String, String> queryParams) {
        var items = mockPersonRestService.getContributorSearchResult();

        ResultList<ContributorDO> resultList = new ResultList<>();
        resultList.setItems(items);
        resultList.setSearch(search);
        search.getPagination().setNumItems(items.size());
        search.getPagination().calculateFields();

        return resultList;
    }
}

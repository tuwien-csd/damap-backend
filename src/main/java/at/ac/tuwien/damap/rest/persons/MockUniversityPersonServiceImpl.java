package at.ac.tuwien.damap.rest.persons;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import io.quarkus.arc.DefaultBean;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class MockUniversityPersonServiceImpl implements PersonService {

    @Inject
    @RestClient
    MockPersonRestService mockPersonRestService;

    @Override
    public ContributorDO getPersonById(String id) {
        return mockPersonRestService.getContributorDetails(id).get(0);
    }

    @Override
    public List<ContributorDO> getPersonSearchResult(String searchTerm) {
        return mockPersonRestService.getContributorSearchResult();
    }
}

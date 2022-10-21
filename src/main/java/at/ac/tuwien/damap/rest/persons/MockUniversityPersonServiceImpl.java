package at.ac.tuwien.damap.rest.persons;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import io.quarkus.arc.DefaultBean;
import lombok.extern.jbosslog.JBossLog;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class MockUniversityPersonServiceImpl implements PersonService {

    @RestClient
    MockPersonRestService mockPersonRestService;

    public MockUniversityPersonServiceImpl() {
        // manual creation of service should take care of creating injected values.
        try {
            mockPersonRestService = MockPersonRestService.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ContributorDO getPersonById(String id) {
        return mockPersonRestService.getContributorDetails(id).get(0);
    }

    @Override
    public List<ContributorDO> getPersonSearchResult(String searchTerm) {
        return mockPersonRestService.getContributorSearchResult();
    }
}

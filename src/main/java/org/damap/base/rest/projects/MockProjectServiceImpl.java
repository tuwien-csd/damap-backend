package org.damap.base.rest.projects;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.Search;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.ProjectDO;
import org.damap.base.rest.persons.MockPersonRestService;
import io.quarkus.arc.DefaultBean;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class MockProjectServiceImpl implements ProjectService {

    @Inject
    @RestClient
    MockPersonRestService mockPersonRestService;

    @Inject
    @RestClient
    MockProjectRestService mockProjectRestService;

    @Override
    public List<ContributorDO> getProjectStaff(String projectId) {
        return mockPersonRestService.getContributorSearchResult();
    }

    @Override
    public ProjectSupplementDO getProjectSupplement(String projectId) {
        try {
            return mockProjectRestService.getProjectSupplement();
        } catch (ProcessingException pe) {
            return null;
        }
    }

    @Override
    public ContributorDO getProjectLeader(String projectId) {
        return mockPersonRestService.getContributorSearchResult().get(0);
    }

    @Override
    public ResultList<ProjectDO> search(MultivaluedMap<String, String> queryParams) {
        var search = Search.fromMap(queryParams);
        var items = mockProjectRestService.getProjectList(search.getQuery());

        return ResultList.fromItemsAndSearch(items, search);
    }

    @Override
    public ProjectDO read(String id, MultivaluedMap<String, String> queryParams) {
        return mockProjectRestService.getProjectDetails(id).get(0);
    }

    @Override
    public ResultList<ProjectDO> getRecommended(MultivaluedMap<String, String> queryParams) {
        var search = Search.fromMap(queryParams);
        var items = mockProjectRestService.getRecommended("recommend");

        return ResultList.fromItemsAndSearch(items, search);
    }
}

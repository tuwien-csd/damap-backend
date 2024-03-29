package at.ac.tuwien.damap.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import at.ac.tuwien.damap.rest.base.ResultList;
import at.ac.tuwien.damap.rest.base.Search;
import at.ac.tuwien.damap.rest.base.resource.ResourceRead;
import at.ac.tuwien.damap.rest.base.resource.ResourceSearch;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

@Path("/api/persons")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class PersonResource implements ResourceRead<ContributorDO>, ResourceSearch<ContributorDO> {

    @Inject
    PersonServiceBroker personServiceBroker;

    @Override
    public ContributorDO read(String id, UriInfo uriInfo) {
        var queryParams = uriInfo.getQueryParameters();
        log.info("Return person details for id=" + id + " and query=" + queryParams.toString());
        PersonService searchService = personServiceBroker.getServiceFromQueryParams(queryParams);

        ContributorDO result = null;
        if (searchService != null) {
            result = searchService.read(id, queryParams);
        }

        return result;
    }

    @Override
    public ResultList<ContributorDO> search(UriInfo uriInfo) {
        var queryParams = uriInfo.getQueryParameters();
        log.info("Return person list for query=" + queryParams.toString());

        PersonService searchService = personServiceBroker.getServiceFromQueryParams(queryParams);
        Search search = Search.fromMap(queryParams);

        ResultList<ContributorDO> result = ResultList.fromItemsAndSearch(null, search);

        if (searchService != null) {
            result = searchService.search(queryParams);
        }

        return result;
    }

}

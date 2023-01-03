package at.ac.tuwien.damap.rest;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.ac.tuwien.damap.rest.config.domain.ServiceConfig;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.arc.All;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

@Path("/api/persons")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class PersonResource {

    LinkedHashMap<String, PersonService> personServices = new LinkedHashMap<String, PersonService>();

    @Inject
    public PersonResource(ConfigResource config, @All List<PersonService> availableServices) {
        List<ServiceConfig> configuredServices = config.personServiceConfigurations.getConfigs();

        configuredServices.forEach(serviceConfig -> {
            Boolean found = false;
            String configClassName = serviceConfig.getClassName();
            for (var service : availableServices) {
                try {
                    String serviceClassName = service.getClass().getCanonicalName().split("_ClientProxy")[0];

                    if (configClassName.equals(serviceClassName)) {
                        personServices.put(serviceConfig.getQueryValue(), service);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!found) {
                log.warn(String.format("Service '%s' configured but is not available", serviceConfig.getClassName()));
            }
        });
    }

    @GET
    @Path("/{id}")
    public ContributorDO getPersonById(@PathParam("id") String id,
            @QueryParam("searchService") String searchServiceType) {
        log.info("Return person details for id=" + id);
        PersonService searchService = getServiceForQueryParam(searchServiceType);

        ContributorDO person = null;
        if (searchService != null) {
            person = searchService.getPersonById(id);
        }

        return person;
    }

    @GET
    @Path("/search")
    public List<ContributorDO> getPersonSearchResult(@QueryParam("q") String searchTerm,
            @QueryParam("searchService") String searchServiceType) {

        log.info("Return person list for query=" + searchTerm + "&searchService=" + searchServiceType);

        PersonService searchService = getServiceForQueryParam(searchServiceType);

        List<ContributorDO> persons = List.of();
        if (searchService != null) {
            persons = searchService.getPersonSearchResult(searchTerm);
        }

        return persons;
    }

    private PersonService getServiceForQueryParam(String searchServiceType) {
        PersonService searchService = personServices.get(searchServiceType);
        if (searchService == null && !personServices.isEmpty()) {
            searchService = personServices.entrySet().iterator().next().getValue();
        }

        return searchService;
    }
}

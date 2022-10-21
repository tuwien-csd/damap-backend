package at.ac.tuwien.damap.rest;

import java.lang.reflect.Constructor;
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

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import io.quarkus.security.Authenticated;
import lombok.extern.jbosslog.JBossLog;

/* Resource currently unused, but will be required for person search at a later stage */
// TODO: remove unused functions
@Path("/api/persons")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class PersonResource {

    @Inject
    ConfigResource config;

    LinkedHashMap<String, PersonService> personServices = new LinkedHashMap<String, PersonService>();

    public PersonResource(ConfigResource config) {
        config.personServiceConfigurations.getConfigs().forEach(serviceConfig -> {
            try {
                Class<?> clazz = Class.forName(serviceConfig.getClassName());
                Constructor<?> ctor = clazz.getDeclaredConstructor();
                PersonService newService = (PersonService) ctor.newInstance();

                personServices.put(serviceConfig.getQueryValue(), newService);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @GET
    @Path("/{id}")
    public ContributorDO getPersonById(@PathParam("id") String id,
            @QueryParam("searchService") String searchServiceType) {
        log.info("Return person details for id=" + id);
        PersonService searchService = getServiceForQueryParam(searchServiceType);

        return searchService.getPersonById(id);
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

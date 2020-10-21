package at.ac.tuwien.r3data;

import generated.Repository;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.re3data.schema._2_2.Re3Data;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/repositories")
@RolesAllowed("user")
@Produces(MediaType.APPLICATION_JSON)
public class RepositoriesResource {

    @Inject RepositoriesService repositoriesService;

    @GET
    public List<Repository> getAll() {
        return repositoriesService.getAll();
    }

    @GET
    @Path("/{id}")
    public Re3Data getById(@PathParam String id) { return repositoriesService.getById(id); }

}

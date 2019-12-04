package at.ac.tuwien.r3data;

import generated.Repository;

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

}

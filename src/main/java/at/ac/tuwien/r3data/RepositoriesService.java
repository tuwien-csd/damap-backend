package at.ac.tuwien.r3data;

import generated.Repository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.re3data.schema._2_2.Re3Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class RepositoriesService {

    private List<Repository> repositories;

    @Inject @RestClient RepositoriesRemoteResource repositoriesRemoteResource;

    public List<Repository> getAll() {
        if(repositories == null)
            repositories = repositoriesRemoteResource.getAll();
        return repositories;
    }

    public Re3Data getById(String id) {
        return repositoriesRemoteResource.getById(id);
    }

}

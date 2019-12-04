package at.ac.tuwien.r3data;

import generated.Repository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

}

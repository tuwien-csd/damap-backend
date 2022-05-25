package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import io.quarkus.arc.DefaultBean;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class PersonServiceImpl implements PersonService {

    @Override
    public ContributorDO getPersonById(String id) {
        return null;
    }

    @Override
    public List<ContributorDO> getPersonSearchResult(String searchTerm) {
        return null;
    }
}

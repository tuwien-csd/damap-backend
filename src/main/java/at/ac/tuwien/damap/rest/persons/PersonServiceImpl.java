package at.ac.tuwien.damap.rest.persons;

import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;
import io.quarkus.arc.DefaultBean;

import javax.enterprise.context.ApplicationScoped;

/*
    extend this class in your custom project, for your implementation
 */

@ApplicationScoped
@DefaultBean
public class PersonServiceImpl implements PersonService {

    @Override
    public PersonDO getPersonById(String id) {
        return null;
    }
}
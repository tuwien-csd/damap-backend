package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
public class ORCIDPersonServiceImpl implements PersonService {

    @Inject
    @RestClient
    OrcidPersonService orcidRestClient;

    @Override
    public ContributorDO getPersonById(String id) {
        return null;
    }

    @Override
    public List<ContributorDO> getPersonSearchResult(String searchTerm) {
        List<ContributorDO> contributors = List.of();

        try {

            var orcidSearch = orcidRestClient.getAll(searchTerm, 10);

            if (orcidSearch.getNumFound() > 0 && orcidSearch.getPersons() != null) {
                contributors = orcidSearch.getPersons().stream().map(o -> {
                    var c = new ContributorDO();
                    ContributorORCIDExpandedSearchMapper.mapEntityToDO(o, c);
                    return c;
                }).collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contributors;
    }
}

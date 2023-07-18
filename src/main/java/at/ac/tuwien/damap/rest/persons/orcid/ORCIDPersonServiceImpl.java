package at.ac.tuwien.damap.rest.persons.orcid;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.ac.tuwien.damap.rest.base.ResultList;
import at.ac.tuwien.damap.rest.base.Search;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.persons.PersonService;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class ORCIDPersonServiceImpl implements PersonService {

    @Inject
    @RestClient
    OrcidPersonService orcidRestClient;

    @Override
    public ContributorDO read(String id, MultivaluedMap<String, String> queryParams) {
        return ORCIDMapper.mapRecordEntityToPersonDO(orcidRestClient.get(id), new ContributorDO());
    }

    @Override
    public ResultList<ContributorDO> search(MultivaluedMap<String, String> queryParams) {
        Search search = Search.fromMap(queryParams);

        List<ContributorDO> contributors = null;
        try {
            var orcidSearch = orcidRestClient.getAll(search.getQuery(), search.getPagination().getPerPage());

            if (orcidSearch.getNumFound() > 0 && orcidSearch.getPersons() != null) {
                contributors = orcidSearch.getPersons().stream().map(o -> {
                    var c = new ContributorDO();
                    ORCIDMapper.mapExpandedSearchPersonEntityToDO(o, c);
                    return c;
                }).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("Issue searching ORCID persons", e);
        }

        return ResultList.fromItemsAndSearch(contributors, search);
    }
}

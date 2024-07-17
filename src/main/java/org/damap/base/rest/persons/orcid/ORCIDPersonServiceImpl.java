package org.damap.base.rest.persons.orcid;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.Search;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.persons.PersonService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/** ORCIDPersonServiceImpl class. */
@ApplicationScoped
@JBossLog
public class ORCIDPersonServiceImpl implements PersonService {

  @Inject @RestClient OrcidPersonService orcidRestClient;

  /** {@inheritDoc} */
  @Override
  public ContributorDO read(String id, MultivaluedMap<String, String> queryParams) {
    return ORCIDMapper.mapRecordEntityToPersonDO(orcidRestClient.get(id), new ContributorDO());
  }

  /** {@inheritDoc} */
  @Override
  public ResultList<ContributorDO> search(MultivaluedMap<String, String> queryParams) {
    Search search = Search.fromMap(queryParams);

    List<ContributorDO> contributors = null;
    try {
      var orcidSearch =
          orcidRestClient.getAll(search.getQuery(), search.getPagination().getPerPage());

      if (orcidSearch.getNumFound() > 0 && orcidSearch.getPersons() != null) {
        contributors =
            orcidSearch.getPersons().stream()
                .map(
                    o -> {
                      var c = new ContributorDO();
                      ORCIDMapper.mapExpandedSearchPersonEntityToDO(o, c);
                      return c;
                    })
                .collect(Collectors.toList());
      }
    } catch (Exception e) {
      log.error("Issue searching ORCID persons", e);
    }

    return ResultList.fromItemsAndSearch(contributors, search);
  }
}

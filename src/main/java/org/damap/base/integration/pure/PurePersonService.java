package org.damap.base.integration.pure;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.jbosslog.JBossLog;
import org.damap.base.integration.PersonService;
import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.Search;
import org.damap.base.rest.dmp.domain.ContributorDO;

/**
 * This service implements partially reading Elsevier Pure Person objects from their API.
 *
 * <p><strong>Note:</strong> this implementation is currently experimental.
 *
 * @see <a href="https://api.elsevierpure.com/ws/api/rapidoc.html">Elsevier Pure API doc</a>
 */
@ApplicationScoped
@JBossLog
public class PurePersonService implements PersonService {
  @Inject PureAPI pureAPI;

  /** {@inheritDoc} */
  @Override
  @CacheResult(cacheName = "pure-read-person", keyGenerator = PureCacheKeyGenerator.class)
  public ContributorDO read(String id) {
    PureAPIPerson person = pureAPI.getPerson(id);
    if (person == null) {
      return null;
    }
    return toContributorWithEmail(person);
  }

  /** {@inheritDoc} */
  @Override
  @CacheResult(cacheName = "pure-search-persons", keyGenerator = PureCacheKeyGenerator.class)
  public ResultList<ContributorDO> search(Search search) {
    ResultList<ContributorDO> result = new ResultList<>();
    result.setSearch(search);
    Stream<PureAPIPerson> stream = pureAPI.listAllPersons().stream();
    String query = search.getQuery();
    if (query != null && !query.isEmpty()) {
      String queryLower = query.toLowerCase();
      stream = stream.filter(person -> matchesQuery(person, queryLower));
    }
    result.setItems(stream.map(this::toContributorWithEmail).collect(Collectors.toList()));
    return result;
  }

  ContributorDO toContributorWithEmail(PureAPIPerson person) {
    ContributorDO contributor = person.toContributor();
    if ((contributor.getMbox() == null || contributor.getMbox().isBlank())
        && person.getUser() != null
        && person.getUser().getUuid() != null) {
      PureAPIUser user = pureAPI.getUser(person.getUser().getUuid());
      if (user != null && user.getEmail() != null && !user.getEmail().isBlank()) {
        contributor.setMbox(user.getEmail());
      }
    }
    return contributor;
  }

  private static boolean matchesQuery(PureAPIPerson person, String queryLower) {
    if (person.getName() != null) {
      String firstName = person.getName().getFirstName();
      String lastName = person.getName().getLastName();
      if (firstName != null && firstName.toLowerCase().contains(queryLower)) {
        return true;
      }
      if (lastName != null && lastName.toLowerCase().contains(queryLower)) {
        return true;
      }
    }
    String identifier =
        Boolean.TRUE.equals(person.getOrcidAuthenticated())
                && person.getOrcid() != null
                && !person.getOrcid().isEmpty()
            ? person.getOrcid()
            : person.getUuid();
    return identifier != null && identifier.toLowerCase().contains(queryLower);
  }
}

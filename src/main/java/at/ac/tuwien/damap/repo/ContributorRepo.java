package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Contributor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContributorRepo implements PanacheRepository<Contributor> {
}

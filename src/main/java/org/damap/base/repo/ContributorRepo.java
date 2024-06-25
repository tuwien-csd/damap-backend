package org.damap.base.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.damap.base.domain.Contributor;

@ApplicationScoped
public class ContributorRepo implements PanacheRepository<Contributor> {}

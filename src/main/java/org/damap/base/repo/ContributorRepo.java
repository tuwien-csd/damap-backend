package org.damap.base.repo;

import org.damap.base.domain.Contributor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContributorRepo implements PanacheRepository<Contributor> {
}

package org.damap.base.repo;

import org.damap.base.domain.Funding;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FundingRepo implements PanacheRepository<Funding> {

    public List<Funding> getAll() {
        return listAll();
    }
}

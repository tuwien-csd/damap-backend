package at.ac.tuwien.damap.repo;

import at.ac.tuwien.damap.domain.Funding;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FundingRepo implements PanacheRepository<Funding> {

    public List<Funding> getAll() {
        return find("select funding from Funding funding").list();
    }
}

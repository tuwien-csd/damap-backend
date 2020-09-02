package at.ac.tuwien.dmap.repo;

import at.ac.tuwien.dmap.domain.DataManagementPlan;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DataManagementPlanRepo implements PanacheRepository<DataManagementPlan> {

    public List<DataManagementPlan> getAll() {
        return find("select dmp from DataManagementPlan dmp").list();
    }

}

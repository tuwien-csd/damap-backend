package at.ac.tuwien.dmap.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Version;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class DataManagementPlan extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

}

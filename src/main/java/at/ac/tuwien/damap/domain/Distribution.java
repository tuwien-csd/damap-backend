package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
public class Distribution extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @NotNull(message = "{subprojectAccount.subproject.notnull}")
    @ManyToOne
    @JoinColumn(name = "dataset_id", nullable = false, updatable = false)
    private Dataset dataset;

    @NotNull(message = "{subprojectAccount.account.notnull}")
    @ManyToOne()
    @JoinColumn(name = "host_id", nullable = false)
    private Host host;
}

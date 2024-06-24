package org.damap.base.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"dataset"})
@ToString(exclude = "dataset")
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

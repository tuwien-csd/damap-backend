package org.damap.base.domain;

import org.damap.base.enums.EIdentifierType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class Identifier extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EIdentifierType identifierType;
}

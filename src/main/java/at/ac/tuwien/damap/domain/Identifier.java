package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EIdentifierType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
//@Audited
public class Identifier extends PanacheEntity {

    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EIdentifierType identifierType;
}

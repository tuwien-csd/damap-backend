package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EContributorRole;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class Contributor extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "dmp_id", nullable = false, updatable = false, insertable = false)
    private Dmp dmp;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person contributor;

    @Enumerated(EnumType.STRING)
    @Column(name = "contributor_role")
    private EContributorRole contributorRole;
}

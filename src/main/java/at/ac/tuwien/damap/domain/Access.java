package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EFunctionRole;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class Access extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "dmp_id")
    private Dmp dmp;

    @Audited
    @Column(name = "university_id")
    private long universityId;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "identifier_id")
    private Identifier personIdentifier;

    @Enumerated(EnumType.STRING)
    private EFunctionRole role;

    @Audited
    private Date from;

    @Audited
    private Date until;
}

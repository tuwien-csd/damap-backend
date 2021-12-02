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
@Table(name = "access_management")
public class Access extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id", nullable = false, updatable = false)
    private Dmp dmp;

    @Column(name = "university_id")
    private String universityId;

//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identifier_id", updatable = false)
    private Identifier personIdentifier;

    @Enumerated(EnumType.STRING)
    private EFunctionRole role;

    @Column(name = "start_date")
    private Date start;

    @Column(name = "until_date")
    private Date until;
}

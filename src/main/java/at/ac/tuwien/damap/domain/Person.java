package at.ac.tuwien.damap.domain;

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
public class Person extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Identifier personIdentifier;

    @Audited
    @Column(name = "university_id")
    private Long universityId;

    @Audited
    private String mbox;

    @Audited
    @Column(name = "first_name")
    private String firstName;

    @Audited
    @Column(name = "last_name")
    private String lastName;
}

package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "dmp")
@ToString(exclude = "dmp")@Entity
@Audited
public class Host extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "dmp_id")
    private Dmp dmp;

    @Audited
    private String name;

    @Audited
    private Date date;
}

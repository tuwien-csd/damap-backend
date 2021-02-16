package at.ac.tuwien.damap.domain;

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
public class Dataset extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "dmp_id")
    private Dmp dmp;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host host;

    @Audited
    private String title;

    @Audited
    private String type;

    @Audited
    private String size;

    @Audited
    private String comment;

    @Audited
    private boolean publish;

    @Audited
    private String license;

    @Audited
    private Date start;

    @Audited
    @Column(name = "reference_hash")
    private String referenceHash;
}

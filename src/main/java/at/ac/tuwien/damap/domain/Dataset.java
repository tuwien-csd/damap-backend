package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EDataAccessType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "dmp")
@ToString(exclude = "dmp")
@Entity
//@Audited
public class Dataset extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id")
    private Dmp dmp;

//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

    private String title;

    private String type;

    @Column(name = "data_size")
    private String size;

    @Column(name = "dataset_comment")
    private String comment;

    @Column(name = "personal_data")
    private Boolean personalData;

    @Column(name = "sensitive_data")
    private Boolean sensitiveData;

    @Column(name = "legal_restrictions")
    private Boolean legalRestrictions;

    private String license;

    @Column(name = "start_date")
    private Date start;

    @Column(name = "reference_hash")
    private String referenceHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_access")
    private EDataAccessType dataAccess;
}

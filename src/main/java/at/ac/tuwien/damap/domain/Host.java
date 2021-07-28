package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"dmp", "distributionList"})
@ToString(exclude = {"dmp", "distributionList"})
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="discriminator", discriminatorType= DiscriminatorType.STRING)
//@Audited
public class Host extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Column(name = "host_id")
    private String hostId;

//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id")
    private Dmp dmp;

    private String title;

    @Column(name = "retrieval_date")
    private Date date;

    @OneToMany(mappedBy = "host", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Distribution> distributionList = new ArrayList<>();
}

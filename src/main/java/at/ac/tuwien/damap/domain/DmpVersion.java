package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "dmp")
@Entity
@Table(name = "dmp_version")
public class DmpVersion extends PanacheEntity {

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id")
    @EqualsAndHashCode.Exclude
    private Dmp dmp;

    @Column(name = "revision_number")
    private long revisionNumber;

    @Column(name = "version_date")
    private Date versionDate;

    @Column(name = "version_name")
    private String versionName;
}

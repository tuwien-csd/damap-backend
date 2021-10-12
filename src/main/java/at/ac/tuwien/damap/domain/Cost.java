package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.ECostType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"dmp"})
@ToString(exclude = {"dmp"})
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
public class Cost extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id")
    private Dmp dmp;

    private String title;

    private Long value;

    @Column(name = "currency_code")
    private String currencyCode; // controlled vocabulary: ISO 4217

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "cost_type")
    private ECostType type;

    @Column(name = "custom_type")
    private String customType;
}

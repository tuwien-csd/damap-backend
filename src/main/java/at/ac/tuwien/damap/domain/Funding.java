package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EFundingState;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
public class Funding extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "funder_id")
    private Identifier funderIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "funding_status")
    private EFundingState fundingStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grant_id")
    private Identifier grantIdentifier;
}

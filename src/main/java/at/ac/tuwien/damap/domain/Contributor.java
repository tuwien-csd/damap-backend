package at.ac.tuwien.damap.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.ac.tuwien.damap.enums.EContributorRole;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "dmp")
@ToString(exclude = "dmp")
@Entity
@Audited
public class Contributor extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id", nullable = false, updatable = false)
    private Dmp dmp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Identifier personIdentifier;

    @Column(name = "university_id")
    private String universityId;

    private String mbox;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String affiliation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "affiliation_id")
    private Identifier affiliationId;

    private Boolean contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "contributor_role")
    private EContributorRole contributorRole;
}

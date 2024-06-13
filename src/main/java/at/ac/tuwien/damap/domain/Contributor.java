package at.ac.tuwien.damap.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

import at.ac.tuwien.damap.annotations.gdpr.*;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import at.ac.tuwien.damap.enums.EContributorRole;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Gdpr
@Data
@EqualsAndHashCode(callSuper = true, exclude = "dmp")
@ToString(exclude = "dmp")
@Entity
@Audited
public class Contributor extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @GdprContext(properties = {"id", "project.title"})
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmp_id", nullable = false, updatable = false)
    private Dmp dmp;

    @GdprBase
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Identifier personIdentifier;

    @GdprKey
    @Column(name = "university_id")
    private String universityId;

    @GdprBase
    private String mbox;

    @GdprBase
    @Column(name = "first_name")
    private String firstName;

    @GdprBase
    @Column(name = "last_name")
    private String lastName;

    @GdprExtended
    private String affiliation;

    @GdprExtended
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "affiliation_id")
    private Identifier affiliationId;

    private Boolean contact;

    @GdprExtended
    @Enumerated(EnumType.STRING)
    @Column(name = "contributor_role")
    private EContributorRole contributorRole;
}

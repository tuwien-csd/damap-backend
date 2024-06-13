package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.annotations.gdpr.Gdpr;
import at.ac.tuwien.damap.annotations.gdpr.GdprExtended;
import at.ac.tuwien.damap.annotations.gdpr.GdprKey;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;

@Gdpr
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Consent extends PanacheEntity {

    @GdprKey
    @Column(name = "university_id")
    private String universityId;

    @GdprExtended
    @Column(name = "consent_given")
    private Boolean consentGiven;

    @GdprExtended
    @Column(name = "given_date")
    private Date givenDate;

}

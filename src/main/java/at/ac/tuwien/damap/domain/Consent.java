package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity

public class Consent extends PanacheEntity {

    @Column(name = "university_id")
    private String universityId;

    @Column(name = "consent_given")
    private Boolean consentGiven;

    @Column(name = "given_date")
    private Date givenDate;

}

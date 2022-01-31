package at.ac.tuwien.damap.domain;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
@Audited

public class Consent {

    @Column(name = "university_id")
    private String universityId;

    @Column(name = "consent_given")
    private Boolean consentGiven;

    @Column(name = "given_date")
    private Date givenDate;

}

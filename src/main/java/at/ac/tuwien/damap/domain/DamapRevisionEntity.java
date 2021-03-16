package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.domain.listener.DamapRevisionListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@RevisionEntity(DamapRevisionListener.class)
@Table(name = "revinfo")
public class DamapRevisionEntity {

    @Id
    @GeneratedValue
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    private Date timestamp;

    private String changed_by;

    private String changed_by_id;

    //TODO edit DamapRevisionListener to update additional fields

}


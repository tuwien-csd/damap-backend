package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.domain.listener.DamapRevisionListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.ModifiedEntityNames;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "changed_by_id")
    private String changedById;

    //TODO edit DamapRevisionListener to update additional fields

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "REVCHANGES", joinColumns = @JoinColumn(name = "REV"))
    @Column(name = "ENTITYNAME")
    @Fetch(FetchMode.JOIN)
    @ModifiedEntityNames
    private Set<String> modifiedEntityNames = new HashSet<>();
}

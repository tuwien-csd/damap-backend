package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
//@Audited
public class Project extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Column(name = "university_id")
    private String universityId;

    private String title;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "funding")
    private Funding funding;

    @Column(name = "project_start")
    private Date start;

    @Column(name = "project_end")
    private Date end;
}

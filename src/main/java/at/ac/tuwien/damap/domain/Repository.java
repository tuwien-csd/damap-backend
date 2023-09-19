package at.ac.tuwien.damap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import jakarta.persistence.Access;
import jakarta.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("REPOSITORY")
@Access(AccessType.FIELD)
@Audited
public class Repository extends Host {

    @Column(name = "repository_id")
    private String repositoryId;
}

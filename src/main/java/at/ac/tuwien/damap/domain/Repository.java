package at.ac.tuwien.damap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Access;
import javax.persistence.*;

@Data
@Entity

@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("REPOSITORY")
@Access(AccessType.FIELD)
public class Repository extends Host {

}

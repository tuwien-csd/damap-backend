package at.ac.tuwien.damap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Access;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("STORAGE")
@Access(AccessType.FIELD)
@Audited
public class Storage extends Host {

    @ManyToOne
    @JoinColumn(name = "internal_storage_id")
    private InternalStorage internalStorageId;
}

package at.ac.tuwien.damap.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Access;
import javax.persistence.*;

@Data
@Entity

@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("STORAGE")
@Access(AccessType.FIELD)
//@Audited
public class Storage extends Host {

    private String url;
    @Column(name = "backup_frequency")
    private String backupFrequency;
    @Column(name = "storage_location")
    private String storageLocation;
    @Column(name = "backup_location")
    private String backupLocation;
}

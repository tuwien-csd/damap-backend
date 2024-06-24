package org.damap.base.domain;

import lombok.*;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.persistence.Access;

@Data
@Entity

@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("EXTERNAL_STORAGE")
@Access(AccessType.FIELD)
@Audited
@Table(name = "external_storage")
public class ExternalStorage extends Host {

    private String url;
    @Column(name = "backup_frequency")
    private String backupFrequency;
    @Column(name = "storage_location")
    private String storageLocation;
    @Column(name = "backup_location")
    private String backupLocation;
}

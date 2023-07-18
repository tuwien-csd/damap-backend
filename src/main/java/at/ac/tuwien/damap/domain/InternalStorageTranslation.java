package at.ac.tuwien.damap.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.persistence.Access;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Access(AccessType.FIELD)
@Audited
@Table(name = "inter_storage_translation")
public class InternalStorageTranslation extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "internal_storage_id")
    private InternalStorage internalStorageId;
    @Column(name = "language_code")
    private String languageCode;
    private String title;
    private String description;
    @Column(name = "backup_frequency")
    private String backupFrequency;
}

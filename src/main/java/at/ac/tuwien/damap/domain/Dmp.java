package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EDataKind;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Table
public class Dmp extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Audited
    private Date created;

    @Audited
    private Date modified;

    @Audited
    private String title;

    @Audited
    private String description;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "project")
    private Project project;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne
    @JoinColumn(name = "contact")
    private Person contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_kind")
    private EDataKind dataKind;

    @Audited
    @Column(name = "no_data_explanation")
    private String noDataExpalnation;

    @Audited
    private String metadata;

    @Audited
    @Column(name = "data_generation")
    private String dataGeneration;

    @Audited
    private String structure;

    @Audited
    @Column(name = "target_audience")
    private String targetAudience;

    @Audited
    @Column(name = "personal_information")
    private boolean personalInformation;

    @Audited
    @Column(name = "sensitive_data")
    private boolean sensitiveData;

    @Audited
    @Column(name = "legal_restrictions")
    private boolean legalRestrictions;

    @Audited
    @Column(name = "ethical_issues_exist")
    private boolean ethicalIssuesExist;

    @Audited
    @Column(name = "committee_approved")
    private boolean committeeApproved;

    @Audited
    @Column(name = "ethics_report")
    private String ethicsReport;

    @Audited
    @Column(name = "optional_statement")
    private String optionalStatement;
}

package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EDataKind;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
//@Audited
@Table
public class Dmp extends PanacheEntity {

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    private Date created;

    private Date modified;

    private String title;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact")
    private Person contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_kind")
    private EDataKind dataKind;

    @Column(name = "no_data_explanation")
    private String noDataExpalnation;

    private String metadata;

    @Column(name = "data_generation")
    private String dataGeneration;

    private String structure;

    @Column(name = "target_audience")
    private String targetAudience;

    @Column(name = "personal_information")
    private Boolean personalInformation;

    @Column(name = "sensitive_data")
    private Boolean sensitiveData;

    @Column(name = "legal_restrictions")
    private Boolean legalRestrictions;

    @Column(name = "ethical_issues_exist")
    private Boolean ethicalIssuesExist;

    @Column(name = "committee_approved")
    private Boolean committeeApproved;

    @Column(name = "ethics_report")
    private String ethicsReport;

    @Column(name = "optional_statement")
    private String optionalStatement;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Contributor> contributorList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Dataset> datasetList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Host> hostList = new ArrayList<>();
}

package at.ac.tuwien.damap.domain;

import at.ac.tuwien.damap.enums.EComplianceType;
import at.ac.tuwien.damap.enums.EDataKind;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
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
    private String noDataExplanation;

    private String metadata;

    @Column(name = "data_generation")
    private String dataGeneration;

    private String structure;

    @Column(name = "target_audience")
    private String targetAudience;

    private String tools;

    @Column(name = "restricted_data_access")
    private String restrictedDataAccess;

    @Column(name = "personal_data")
    private Boolean personalData;

    @Column(name = "personal_data_access")
    private String personalDataAccess;

    @ElementCollection(targetClass = EComplianceType.class, fetch = FetchType.LAZY)
    @CollectionTable(name="personal_data_compliance_list")
    @Column(name = "compliance_type")
    @Enumerated(EnumType.STRING)
    private List<EComplianceType> personalDataCompliance;

    @Column(name = "other_personal_data_compliance")
    private String otherPersonalDataCompliance;

    @Column(name = "sensitive_data")
    private Boolean sensitiveData;

    @Column(name = "sensitive_data_security")
    private String sensitiveDataSecurity;

    @Column(name = "legal_restrictions")
    private Boolean legalRestrictions;

    @Column(name = "legal_restrictions_comment")
    private String legalRestrictionsComment;

    @Column(name = "ethical_issues_exist")
    private Boolean ethicalIssuesExist;

    @Column(name = "committee_approved")
    private Boolean committeeApproved;

    @Column(name = "ethics_report")
    private String ethicsReport;

    @Column(name = "ethical_compliance_statement")
    private String ethicalComplianceStatement;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Contributor> contributorList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Dataset> datasetList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Host> hostList = new ArrayList<>();

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
//    private List<Storage> storage;
//
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
//    private List<Storage> externalStorage;

    @Column(name = "external_storage_info")
    private String externalStorageInfo;

    @Column(name = "restricted_access_info")
    private String restrictedAccessInfo;

    @Column(name = "closed_access_info")
    private String closedAccessInfo;

    @Column(name = "costs_exist")
    private Boolean costsExist;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dmp", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Cost> costs = new ArrayList<>();
}

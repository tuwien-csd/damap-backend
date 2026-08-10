package org.damap.base.rda.dmpcommonstandard;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.damap.base.enums.EDataKind;
import org.damap.base.enums.EDataSource;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.DatasetDO;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.ProjectDO;

/**
 * This class implements DMP conversion from and to the RDA DMP Common Standard. (See <a
 * href="https://github.com/RDA-DMP-Common/common-madmp-api">github.com/RDA-DMP-Common/common-madmp-api</a>
 * )
 *
 * <p>The conversion from the Common Standard into DAMAP objects is best-effort since not all data
 * can be represented.
 */
public final class DMPMapper extends AbstractMapper {

  private final ProjectMapper projectMapper;
  private final ContributorMapper contributorMapper;
  private final CostsMapper costsMapper;
  private final DatasetMapper datasetMapper;
  private final HostsMapper hostsMapper;

  /** Initialize the mapper with the default settings (strict mode turned on). */
  public DMPMapper() {
    this(true);
  }

  /**
   * Initialize the mapper with an option to turn off strict mode. Only use non-strict mode when a
   * human has to review the DMP afterward, never use it for automated imports!
   *
   * @param strict When enabled, any dropped data will cause a {@link
   *     CommonStandardCompatibilityException}. When disabled, DAMAP will do its best to represent
   *     any imported data in the common format but drop data it cannot represent.
   */
  public DMPMapper(boolean strict) {
    this(
        strict,
        new ProjectMapper(strict),
        new ContributorMapper(strict),
        new CostsMapper(strict),
        new DatasetMapper(strict),
        new HostsMapper(strict));
  }

  /**
   * Initialize the mapper with an option to turn off strict mode and the ability to override
   * submappers. Only use non-strict mode when a human has to review the DMP afterward, never use it
   * for automated imports!
   *
   * @param strict When enabled, any dropped data will cause a {@link
   *     CommonStandardCompatibilityException}. When disabled, DAMAP will do its best to represent
   *     any imported data in the common format but drop data it cannot represent.
   * @param projectMapper contains the pre-configured {@link ProjectMapper}.
   * @param costsMapper contains a pre-configured {@link CostsMapper}.
   * @param datasetMapper contains a pre-configured {@link DatasetMapper}.
   */
  public DMPMapper(
      boolean strict,
      ProjectMapper projectMapper,
      ContributorMapper contributorMapper,
      CostsMapper costsMapper,
      DatasetMapper datasetMapper,
      HostsMapper hostsMapper) {
    super(strict);
    this.projectMapper = projectMapper;
    this.contributorMapper = contributorMapper;
    this.costsMapper = costsMapper;
    this.datasetMapper = datasetMapper;
    this.hostsMapper = hostsMapper;
  }

  /** DAMAP to RDA (Export). * */
  public DMPWithID convert(DmpDO dmp) {
    return new DMPWithID().id(String.valueOf(dmp.getId())).dmp(convertData(dmp));
  }

  /** Helper method for DAMAP to RDA (Export). * */
  private DMPData convertData(DmpDO dmp) {
    DMPData result = new DMPData();
    result.setTitle(dmp.getTitle() != null ? dmp.getTitle() : "Untitled DMP");
    result.setDescription(dmp.getDescription());
    result.setRights(dmp.getDataRightsAndAccessControl());
    if (dmp.getCreated() != null) {
      result.setCreated(OffsetDateTime.ofInstant(dmp.getCreated().toInstant(), ZoneOffset.UTC));
    } else if (dmp.getModified() != null) {
      result.setCreated(OffsetDateTime.ofInstant(dmp.getModified().toInstant(), ZoneOffset.UTC));
    } else {
      result.setCreated(OffsetDateTime.now(ZoneOffset.UTC));
    }

    if (dmp.getModified() != null) {
      result.setModified(OffsetDateTime.ofInstant(dmp.getModified().toInstant(), ZoneOffset.UTC));
    } else if (dmp.getCreated() != null) {
      result.setModified(OffsetDateTime.ofInstant(dmp.getCreated().toInstant(), ZoneOffset.UTC));
    } else {
      result.setModified(OffsetDateTime.now(ZoneOffset.UTC));
    }
    result.setLanguage(LanguageCode.ENG);
    result.setDmpId(new DMPID().type("other").identifier(String.valueOf(dmp.getId())));

    ProjectDO project = dmp.getProject();
    if (project != null) {
      result.project(List.of(projectMapper.convert(project)));
    }
    if (dmp.getContact() != null) {
      result.setContact(contributorMapper.convertToContact(dmp.getContact()));
    } else {
      Contact placeholderContact = new Contact();
      placeholderContact.setName("Contact Not Provided");
      placeholderContact.setMbox("no-reply@example.com");
      placeholderContact.setContactId(new ContactID().identifier("not-provided").type("other"));
      result.setContact(placeholderContact);
    }
    var contributors = dmp.getContributors();
    if (contributors != null) {
      result.setContributor(
          contributors.stream().map(contributorMapper::convert).collect(Collectors.toList()));
    }
    var costs = dmp.getCosts();
    if (costs != null) {
      result.setCost(costs.stream().map(costsMapper::convert).collect(Collectors.toList()));
    }

    if (dmp.getDatasets() != null) {
      result.setDataset(dmp.getDatasets().stream().map(datasetMapper::convert).toList());
      for (int i = 0; i < dmp.getDatasets().size(); i++) {
        DatasetDO datasetDO = dmp.getDatasets().get(i);
        Dataset rdaDataset = result.getDataset().get(i);

        if (rdaDataset.getDistribution() != null && !rdaDataset.getDistribution().isEmpty()) {
          Distribution baseDist = rdaDataset.getDistribution().get(0);

          List<Distribution> mappedDistributions =
              hostsMapper.mapDistributions(dmp, datasetDO, baseDist);

          rdaDataset.setDistribution(mappedDistributions);
        }
      }
    } else {
      result.setDataset(new ArrayList<>());
    }

    var ethicalIssuesExist = dmp.getEthicalIssuesExist();
    if (ethicalIssuesExist != null) {
      if (ethicalIssuesExist) {
        result.setEthicalIssuesExist(Booleanish.YES);
      } else {
        result.setEthicalIssuesExist(Booleanish.NO);
      }
    } else {
      result.setEthicalIssuesExist(Booleanish.UNKNOWN);
    }
    result.setEthicalIssuesReport(dmp.getEthicalIssuesReport());
    return result;
  }

  /** RDA to DAMAP (Import). * */
  public DmpDO convert(DMPWithID dmp) {
    var dmpDO = new DmpDO();
    var dmpData = dmp.getDmp();
    if (dmpData == null) {
      return dmpDO;
    }
    convertData(dmpData, dmpDO);
    return dmpDO;
  }

  /** RDA to DAMAP (Import). * */
  private void convertData(DMPData data, DmpDO target) {
    target.setTitle(data.getTitle());
    target.setDescription(data.getDescription());
    target.setDataRightsAndAccessControl(data.getRights());
    var projects = data.getProject();
    if (projects != null) {
      if (projects.size() > 1 && strict) {
        throw new CommonStandardCompatibilityException("More than one project present.");
      }
      String dmpIdString = data.getDmpId() != null ? data.getDmpId().getIdentifier() : null;
      for (var project : projects) {
        target.setProject(projectMapper.convert(project, dmpIdString));
      }
    }
    List<ContributorDO> damapContributors = new ArrayList<>();

    // 1. Process the primary Contact
    ContributorDO contactDO = null;
    var contact = data.getContact();
    if (contact != null) {
      contactDO = contributorMapper.convertToContributor(contact);
      contactDO.setContact(true); // Flag this person as the primary contact
      damapContributors.add(contactDO);
    }

    // 2. Process the Contributors
    var contributors = data.getContributor();
    if (contributors != null) {
      for (var rdaContributor : contributors) {
        ContributorDO contributorDO = contributorMapper.convert(rdaContributor);

        // Check if this contributor is the same person as the contact
        if (contactDO != null && isSamePerson(contactDO, contributorDO)) {
          // Merge roles into the contact DO instead of duplicating
          if (contributorDO.getRoles() != null) {
            contactDO.getRoles().addAll(contributorDO.getRoles());
          }
          continue; // Skip adding as a separate contributor
        }

        // Check if this contributor has already been added to avoid general duplicates
        ContributorDO existing = findExistingContributor(damapContributors, contributorDO);
        if (existing != null) {
          if (contributorDO.getRoles() != null) {
            existing.getRoles().addAll(contributorDO.getRoles());
          }
        } else {
          damapContributors.add(contributorDO);
        }
      }
    }

    target.setContributors(damapContributors);

    if (data.getLanguage() != LanguageCode.ENG && strict) {
      throw new CommonStandardCompatibilityException(
          "DAMAP does not support importing non-English DMPs");
    }
    var costs = data.getCost();
    if (costs != null && !costs.isEmpty()) {
      target.setCosts(costs.stream().map(costsMapper::convert).toList());
      target.setCostsExist(true);
    } else {
      target.setCosts(List.of());
      target.setCostsExist(false);
    }
    var datasets = data.getDataset();
    if (datasets != null && !datasets.isEmpty()) {
      List<DatasetDO> damapDatasets = new ArrayList<>();
      boolean hasNew = false;
      boolean hasReused = false;

      for (var rdaDataset : datasets) {
        DatasetDO datasetDO = datasetMapper.convert(rdaDataset);

        if (datasetDO.getReferenceHash() == null) {
          datasetDO.setReferenceHash(java.util.UUID.randomUUID().toString());
        }
        damapDatasets.add(datasetDO);

        if (datasetDO.getSource() == EDataSource.REUSED) {
          hasReused = true;
        } else {
          hasNew = true;
        }

        if (rdaDataset.getDistribution() != null && !rdaDataset.getDistribution().isEmpty()) {
          var distribution = rdaDataset.getDistribution().get(0);
          var rdaHost = distribution.getHost();
          if (rdaHost != null) {
            hostsMapper.importHost(target, rdaHost, datasetDO.getReferenceHash());
          }
        }
      }

      target.setDatasets(damapDatasets);
      target.setDataKind(hasNew ? EDataKind.SPECIFY : EDataKind.NONE);
      target.setReusedDataKind(hasReused ? EDataKind.SPECIFY : EDataKind.NONE);
    } else {
      target.setDatasets(List.of());
      target.setDataKind(EDataKind.NONE);
      target.setReusedDataKind(EDataKind.NONE);
    }
    if (data.getEthicalIssuesDescription() != null
        && !data.getEthicalIssuesDescription().isEmpty()
        && strict) {
      throw new CommonStandardCompatibilityException(
          "DAMAP does not support the ethical_issues_description field");
    }
    target.setEthicalIssuesExist(
        switch (data.getEthicalIssuesExist()) {
          case YES -> true;
          case NO -> false;
          case UNKNOWN -> null;
        });
    target.setEthicalIssuesReport(data.getEthicalIssuesReport());
  }

  /**
   * Helper method to evaluate if two ContributorDO objects represent the same person using this
   * matching chain: Person ID -> Email -> Full Name.
   */
  private boolean isSamePerson(ContributorDO a, ContributorDO b) {
    // A. Match by Person ID
    if (a.getPersonId() != null
        && b.getPersonId() != null
        && a.getPersonId().getIdentifier() != null
        && a.getPersonId().getIdentifier().equalsIgnoreCase(b.getPersonId().getIdentifier())) {
      return true;
    }

    // B. Match by Email (mbox)
    if (a.getMbox() != null
        && !a.getMbox().isBlank()
        && b.getMbox() != null
        && !b.getMbox().isBlank()
        && a.getMbox().equalsIgnoreCase(b.getMbox())) {
      return true;
    }

    // C. Match by First and Last Name
    if (a.getFirstName() != null
        && b.getFirstName() != null
        && a.getLastName() != null
        && b.getLastName() != null
        && a.getFirstName().equalsIgnoreCase(b.getFirstName())
        && a.getLastName().equalsIgnoreCase(b.getLastName())) {
      return true;
    }

    return false;
  }

  /** Helper method to search an active list of contributors for a duplicate record. */
  private ContributorDO findExistingContributor(List<ContributorDO> list, ContributorDO target) {
    return list.stream().filter(c -> isSamePerson(c, target)).findFirst().orElse(null);
  }
}

package org.damap.base.rest.dmp;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.java.Log;
import org.damap.base.TestSetup;
import org.damap.base.enums.EContributorRole;
import org.damap.base.enums.EIdentifierType;
import org.damap.base.rest.dmp.domain.ContributorDO;
import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.dmp.domain.IdentifierDO;
import org.damap.base.rest.dmp.domain.ProjectDO;
import org.damap.base.rest.dmp.domain.RepositoryDO;
import org.damap.base.rest.persons.orcid.ORCIDPersonServiceImpl;
import org.damap.base.rest.persons.orcid.models.ORCIDRecord;
import org.damap.base.util.MockDmpService;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@Log
class DmpServiceTest extends TestSetup {
  @Inject TestDOFactory testDOFactory;

  @Inject MockDmpService dmpService;

  @InjectMock ORCIDPersonServiceImpl orcidPersonServiceImpl;

  @Test
  void updateProjectLeadTest() {
    ProjectDO projectDO =
        new ProjectDO() {
          {
            setUniversityId("-1");
          }
        };
    testDOFactory.getOrCreateTestDmpDOEmpty();
    DmpDO dmpDO =
        new DmpDO() {
          {
            setTitle("title");
            setProject(projectDO);
          }
        };

    dmpDO = dmpService.create(dmpDO, "editedBy");

    // On a new project, the project lead from the CRIS system should be
    // added as contributor, contact and get the PROJECT_LEADER role.
    Assertions.assertFalse(dmpDO.getContributors().isEmpty());
    Optional<ContributorDO> projectLead =
        dmpDO.getContributors().stream()
            .filter(c -> c.getRole().equals(EContributorRole.PROJECT_LEADER))
            .findFirst();
    Assertions.assertTrue(projectLead.isPresent());
    Assertions.assertTrue(projectLead.get().isContact());

    long projectLeadID = projectLead.get().getId();
    // Remove project from dmp and update. Nothing should happen.
    dmpDO.setProject(null);
    dmpDO = dmpService.update(dmpDO);

    // Project leader contributor has changed the role and is no longer
    // contact.
    projectLead.get().setRole(EContributorRole.PROJECT_MANAGER);
    projectLead.get().setContact(false);

    // Add other contributor and set as contact.
    ContributorDO otherContributor =
        new ContributorDO() {
          {
            setContact(true);
          }
        };
    dmpDO.setContributors(Arrays.asList(projectLead.get(), otherContributor));

    // Set project again and update.
    dmpDO.setProject(projectDO);
    dmpDO = dmpService.update(dmpDO);

    // Now there should
    // - two contributors
    // - no project leader (don't override already set role)
    // - other contributor is contact (don't override already set contact)
    Assertions.assertEquals(2, dmpDO.getContributors().size());
    projectLead =
        dmpDO.getContributors().stream().filter(c -> c.getId().equals(projectLeadID)).findFirst();
    otherContributor =
        dmpDO.getContributors().stream()
            .filter(c -> !c.getId().equals(projectLeadID))
            .findFirst()
            .get();
    Assertions.assertTrue(projectLead.isPresent());
    Assertions.assertFalse(projectLead.get().isContact());
    Assertions.assertTrue(otherContributor.isContact());
    Assertions.assertEquals(EContributorRole.PROJECT_MANAGER, projectLead.get().getRole());

    // Remove project from dmp and update. Nothing should happen.
    dmpDO.setProject(null);
    dmpDO = dmpService.update(dmpDO);

    // Remove other contributor and set role of project lead to null.
    projectLead.get().setRole(null);
    dmpDO.setContributors(Arrays.asList(projectLead.get()));

    // Set project again and update.
    dmpDO.setProject(projectDO);
    dmpDO = dmpService.update(dmpDO);

    // Now there should be
    // - one contributor
    // - a project leader
    // - a contact (project leader)
    Assertions.assertEquals(1, dmpDO.getContributors().size());
    projectLead =
        dmpDO.getContributors().stream().filter(c -> c.getId().equals(projectLeadID)).findFirst();
    Assertions.assertTrue(projectLead.get().isContact());
    Assertions.assertEquals(EContributorRole.PROJECT_LEADER, projectLead.get().getRole());
  }

  @Test
  void fetchORCIDContributorInfo() {
    ORCIDRecord testRecord = testDOFactory.getORCIDTestRecord();

    DmpDO dmpDO = new DmpDO();
    dmpDO.setTitle("fetchORCIDContributorInfo");
    ContributorDO orcidContributorDO = new ContributorDO();

    IdentifierDO orcidIdentifier = new IdentifierDO();
    orcidIdentifier.setType(EIdentifierType.ORCID);
    orcidIdentifier.setIdentifier("orcid");

    orcidContributorDO.setPersonId(orcidIdentifier);

    dmpDO.setContributors(List.of(orcidContributorDO));

    dmpDO = dmpService.create(dmpDO, "");
    var contributorDOs = dmpDO.getContributors();
    Assertions.assertFalse(contributorDOs.isEmpty());
    Assertions.assertEquals(
        testRecord.getPerson().getName().getGivenNames().getValue(),
        contributorDOs.get(0).getFirstName());

    Assertions.assertEquals(
        testRecord.getPerson().getName().getFamilyName().getValue(),
        contributorDOs.get(0).getLastName());
  }

  @Test
  void givenRepositoryIsRemoved_whenUpdatingDMP_thenRepositoryShouldBeRemoved() {
    DmpDO testDMP = testDOFactory.createDmp(this.toString(), true);
    Assertions.assertEquals(1, testDMP.getRepositories().size());
    testDMP.setRepositories(new ArrayList<>());
    DmpDO updatedDMP = dmpService.update(testDMP);
    Assertions.assertEquals(0, updatedDMP.getRepositories().size());
  }

  @Test
  void givenRepositoryIsAdded_whenUpdatingDMP_thenRepositoryShouldBeAdded() {
    DmpDO testDMP = testDOFactory.createDmp(this.toString(), true);
    Assertions.assertEquals(1, testDMP.getRepositories().size());

    List<RepositoryDO> repoList = testDMP.getRepositories();
    RepositoryDO repositoryToAdd = new RepositoryDO();
    repositoryToAdd.setRepositoryId("r3d100013558");
    repositoryToAdd.setTitle("TU Data 2");
    repositoryToAdd.setDatasets(List.of("referenceHash123456", "referenceHash234567"));
    repoList.add(repositoryToAdd);

    DmpDO updatedDMP = dmpService.update(testDMP);
    Assertions.assertEquals(2, updatedDMP.getRepositories().size());
  }

  @Test
  void givenDmpProjectAcronymIsUpdated_whenUpdatingDMP_thenProjectAcronymShouldBeUpdated() {
    DmpDO testDMP = testDOFactory.createDmp(this.toString(), true);
    Assertions.assertEquals("TEST", testDMP.getProject().getAcronym());
    testDMP.getProject().setAcronym("newAcronym");
    DmpDO updatedDMP = dmpService.update(testDMP);
    Assertions.assertEquals("newAcronym", updatedDMP.getProject().getAcronym());
  }
}

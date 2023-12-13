package at.ac.tuwien.damap.rest.dmp;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import at.ac.tuwien.damap.TestSetup;
import at.ac.tuwien.damap.enums.EContributorRole;
import at.ac.tuwien.damap.enums.EIdentifierType;
import at.ac.tuwien.damap.rest.dmp.domain.ContributorDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.IdentifierDO;
import at.ac.tuwien.damap.rest.dmp.domain.ProjectDO;
import at.ac.tuwien.damap.rest.persons.orcid.ORCIDPersonServiceImpl;
import at.ac.tuwien.damap.rest.persons.orcid.models.ORCIDRecord;
import at.ac.tuwien.damap.util.MockDmpService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.extern.java.Log;

@QuarkusTest
@Log
class DmpServiceTest extends TestSetup {
    @Inject
    TestDOFactory testDOFactory;

    @Inject
    MockDmpService dmpService;

    @InjectMock
    ORCIDPersonServiceImpl orcidPersonServiceImpl;

    @Test
    void updateProjectLeadTest() {
        ProjectDO projectDO = new ProjectDO() {
            {
                setUniversityId("-1");
            }
        };
        testDOFactory.getOrCreateTestDmpDOEmpty();
        DmpDO dmpDO = new DmpDO() {
            {
                setTitle("title");
                setProject(projectDO);
            }
        };

        dmpDO = dmpService.create(dmpDO, "editedBy");

        // On a new project, the project lead from the CRIS system should be
        // added as contributor, contact and get the PROJECT_LEADER role.
        Assertions.assertFalse(dmpDO.getContributors().isEmpty());
        Optional<ContributorDO> projectLead = dmpDO.getContributors()
                .stream()
                .filter(
                        c -> c.getRole().equals(EContributorRole.PROJECT_LEADER))
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
        ContributorDO otherContributor = new ContributorDO() {
            {
                setContact(true);
            }
        };
        dmpDO.setContributors(
                Arrays.asList(projectLead.get(), otherContributor));

        // Set project again and update.
        dmpDO.setProject(projectDO);
        dmpDO = dmpService.update(dmpDO);

        // Now there should
        // - two contributors
        // - no project leader (don't override already set role)
        // - other contributor is contact (don't override already set contact)
        Assertions.assertEquals(2, dmpDO.getContributors().size());
        projectLead = dmpDO.getContributors()
                .stream()
                .filter(c -> c.getId().equals(projectLeadID))
                .findFirst();
        otherContributor = dmpDO.getContributors()
                .stream()
                .filter(c -> !c.getId().equals(projectLeadID))
                .findFirst()
                .get();
        Assertions.assertTrue(projectLead.isPresent());
        Assertions.assertFalse(projectLead.get().isContact());
        Assertions.assertTrue(otherContributor.isContact());
        Assertions.assertEquals(
                EContributorRole.PROJECT_MANAGER, projectLead.get().getRole());

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
        projectLead = dmpDO.getContributors()
                .stream()
                .filter(c -> c.getId().equals(projectLeadID))
                .findFirst();
        Assertions.assertTrue(projectLead.get().isContact());
        Assertions.assertEquals(
                EContributorRole.PROJECT_LEADER, projectLead.get().getRole());
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
        Assertions.assertEquals(testRecord.getPerson().getName().getPath(),
                contributorDOs.get(0).getPersonId().getIdentifier());

    }
}

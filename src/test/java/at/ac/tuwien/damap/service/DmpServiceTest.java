package at.ac.tuwien.damap.service;

import at.ac.tuwien.damap.domain.*;
import at.ac.tuwien.damap.rest.dmp.domain.*;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class DmpServiceTest {

    @Inject
    DmpService dmpService;

    @Inject
    TestDOFactory testDOFactory;

    private DmpDO dmpDO;

    @BeforeEach
    public void setup() {
        // needs to be run before test avoid caching issues
        dmpDO = testDOFactory.getOrCreateTestDmpDO();
        testDOFactory.getOrCreateTestVersionDO();
    }

    @Test
    void testDeleteDmp_Valid() {
        dmpService.delete(dmpDO.getId());

        // Check if all dependent entities are deleted
        assertNull(Project.findById(dmpDO.getProject().getId()));

        for (ContributorDO contributor : dmpDO.getContributors()) {
            assertNull(Contributor.findById(contributor.getId()));
        }

        for (DatasetDO dataset : dmpDO.getDatasets()) {
            assertNull(Dataset.findById(dataset.getId()));
        }

        for (StorageDO storage : dmpDO.getStorage()) {
            assertNull(Storage.findById(storage.getId()));
        }

        for (RepositoryDO repo : dmpDO.getRepositories()) {
            assertNull(Repository.findById(repo.getId()));
        }

        for (CostDO cost : dmpDO.getCosts()) {
            assertNull(Cost.findById(cost.getId()));
        }
    }
}

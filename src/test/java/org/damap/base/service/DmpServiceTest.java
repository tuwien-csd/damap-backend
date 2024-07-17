package org.damap.base.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.damap.base.domain.*;
import org.damap.base.rest.dmp.domain.*;
import org.damap.base.rest.dmp.service.DmpService;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class DmpServiceTest {

  @Inject DmpService dmpService;

  @Inject TestDOFactory testDOFactory;

  private DmpDO dmpDO;

  /** setup. */
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

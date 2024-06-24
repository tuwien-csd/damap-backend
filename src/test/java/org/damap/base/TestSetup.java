package org.damap.base;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import org.damap.base.rest.dmp.domain.DmpDO;
import org.damap.base.rest.persons.MockUniversityPersonServiceImpl;
import org.damap.base.rest.persons.orcid.ORCIDMapper;
import org.damap.base.rest.persons.orcid.ORCIDPersonServiceImpl;
import org.damap.base.security.SecurityService;
import org.damap.base.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;

// Common config for test case setup
@QuarkusTest
public class TestSetup {
    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    protected SecurityService securityService;

    @InjectMock
    MockUniversityPersonServiceImpl personService;

    @InjectMock
    protected ORCIDPersonServiceImpl orcidPersonServiceImpl;

    protected DmpDO dmpDO;

    @BeforeEach
    public void setup() {
        Mockito.when(securityService.getUserId()).thenReturn("012345");
        Mockito.when(securityService.getUserName()).thenReturn("testUser");
        Mockito.when(personService.read(any(String.class))).thenReturn(testDOFactory.getTestContributorDO());
        Mockito.when(orcidPersonServiceImpl.read(any(String.class))).thenReturn(testDOFactory.getTestContributorDO());
        Mockito.when(orcidPersonServiceImpl.read(any(String.class)))
                .thenReturn(
                        ORCIDMapper.mapRecordEntityToPersonDO(testDOFactory.getORCIDTestRecord(),
                                testDOFactory.getTestContributorDO()));
        dmpDO = testDOFactory.getOrCreateTestDmpDO();
    }
}

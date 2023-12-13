package at.ac.tuwien.damap;

import static org.mockito.ArgumentMatchers.any;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.persons.MockUniversityPersonServiceImpl;
import at.ac.tuwien.damap.rest.persons.orcid.ORCIDMapper;
import at.ac.tuwien.damap.rest.persons.orcid.ORCIDPersonServiceImpl;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

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

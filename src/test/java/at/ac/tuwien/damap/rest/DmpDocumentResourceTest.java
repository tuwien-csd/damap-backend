package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestHTTPEndpoint(DmpDocumentResource.class)
public class DmpDocumentResourceTest {

    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    SecurityService securityService;

    @InjectMock
    MockProjectServiceImpl mockProjectService;

    @BeforeEach
    public void setup() {
        Mockito.when(securityService.getUserId()).thenReturn("012345");
        Mockito.when(securityService.getUserName()).thenReturn("testUser");
        Mockito.when(mockProjectService.read(anyString())).thenReturn(testDOFactory.getTestProjectDO());
    }

    @Test
    public void testExportTemplateEndpoint_Invalid() {
        given()
                .when().get("/0")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testExportTemplateEndpoint_Unauthorized() {
        given()
                .when().get("/0")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testExportTemplateEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/" + dmpDO.getId())
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testExportTemplateEndpointWithTemplateTypeFWF_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/" + dmpDO.getId() + "?template=FWF")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testExportTemplateEndpointWithTemplateTypeScienceEurope_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/" + dmpDO.getId() + "?template=SCIENCE_EUROPE")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testExportTemplateEndpointWithTemplateType_Invalid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/" + dmpDO.getId() + "?template=invalid")
                .then()
                .statusCode(404);
    }
}

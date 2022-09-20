package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.VersionResource;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.version.VersionDO;
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
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(VersionResource.class)
public class VersionResourceTest {

    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    SecurityService securityService;

    @BeforeEach
    public void setup() {
        Mockito.when(securityService.getUserId()).thenReturn("012345");
        Mockito.when(securityService.getUserName()).thenReturn("testUser");
    }

    @Test
    public void testGetDmpVersionsEndpoint_Unauthorized() {
        given()
                .when().get("/list/0")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetDmpVersionsEndpoint_Valid() {
        VersionDO versionDO = testDOFactory.getOrCreateTestVersionDO();
        given()
                .when().get("/list/" + versionDO.getDmpId())
                .then()
                .statusCode(200);
    }

    @Test
    public void testSaveDmpVersionsEndpoint_Unauthorized() {
        VersionDO versionDO = new VersionDO();
        versionDO.setVersionName("TestVersion");
        versionDO.setDmpId(0L);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(versionDO)
                .when().put()
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testSaveDmpVersionsEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        VersionDO versionDO = new VersionDO();
        versionDO.setVersionName("TestVersion2");
        versionDO.setDmpId(dmpDO.getId());
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(versionDO)
                .when().put()
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }
}

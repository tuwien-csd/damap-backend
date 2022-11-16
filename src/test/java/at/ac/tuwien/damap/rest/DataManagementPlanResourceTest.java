package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.version.VersionService;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(DataManagementPlanResource.class)
public class DataManagementPlanResourceTest {

    @Inject
    TestDOFactory testDOFactory;

    @Inject
    VersionService versionService;

    @InjectMock
    SecurityService securityService;

    @BeforeEach
    public void setup() {
        Mockito.when(securityService.getUserId()).thenReturn("012345");
        Mockito.when(securityService.getUserName()).thenReturn("testUser");
    }

    @Test
    public void testGetAllPlansEndpoint_Invalid() {
        given()
                .when().get("/all")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetAllPlansEndpoint_InvalidRole() {
        given()
                .when().get("/all")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "adminJwt", roles = "Damap Admin")
    public void testGetAllPlansEndpoint_ValidRole() {
        given()
                .when().get("/all")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetPlansEndpoint_Valid() {
        given()
                .when().get("/list")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetPlansEndpoint_Invalid() {
        given()
                .when().get("/list")
                .then()
                .statusCode(401);
    }

    @Test
    public void testPlanByIdEndpoint_Invalid() {
        given()
                .when().get("/1")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testPlanByIdEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/" + dmpDO.getId())
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testPlanByIdEndpoint_Unauthorized() {
        given()
                .when().get("/0")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testSavePlanEndpoint_Valid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.createDmpDO())
                .when().post("")
                .then()
                .statusCode(200)
                .body("title", is("Mock Dmp"));
    }

    @Test
    public void testSavePlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.createDmpDO())
                .when().post("")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdatePlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .when().put("/1")
                .then()
                .statusCode(401);
    }

    @Disabled
    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testUpdatePlanEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDOEmpty();
        dmpDO.setMetadata("Different String for metadata");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dmpDO)
                .when()
                .put("/" + dmpDO.getId())
                .then()
                .statusCode(200)
                .body("title", is("EmptyTestDmp"));
    }

    @Disabled
    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testUpdatePlanEndpoint_Unauthorized() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDOEmpty();
        dmpDO.setMetadata("Different String for metadata");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dmpDO)
                .when().put("/0")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testDeletePlanOwner_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().delete("/" + dmpDO.getId())
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testDeletePlan_Invalid() {
        // use unauthorized user id
        Mockito.when(securityService.getUserId()).thenReturn("12345");
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().delete("/" + dmpDO.getId())
                .then()
                .statusCode(403);
    }

    @Test
    public void testGetDmpByIdAndRevisionlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/0/1")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetDmpByIdAndRevisionlanEndpoint_Unauthorized() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/0/1")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetDmpByIdAndRevisionlanEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        testDOFactory.getOrCreateTestVersionDO();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/" + dmpDO.getId() + "/" + versionService.getDmpVersions(dmpDO.getId()).get(0).getRevisionNumber())
                .then()
                .statusCode(200);
    }

    private DmpDO createDmpDO() {
        DmpDO dmpDO = new DmpDO();
        dmpDO.setTitle("Mock Dmp");
        return dmpDO;
    }
}

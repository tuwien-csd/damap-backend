package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestHTTPEndpoint(MaDmpResource.class)
public class MaDmpResourceTest {

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
    public void testGetByIdEndpoint_Invalid() {
        given()
                .when().get("/0")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetByIdEndpoint_Unauthorized() {
        given()
                .when().get("/0")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetByIdEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/" + dmpDO.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetFileByIdEndpoint_Invalid() {
        given()
                .when().get("/file/0")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetFileByIdEndpoint_Unauthorized() {
        given()
                .when().get("/file/0")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetFileByIdEndpoint_Valid() {
        DmpDO dmpDO = testDOFactory.getOrCreateTestDmpDO();
        given()
                .when().get("/file/" + dmpDO.getId())
                .then()
                .statusCode(200);
    }
}

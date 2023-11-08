package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.administration.domain.ConsentDO;
import at.ac.tuwien.damap.security.SecurityService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ConsentResource.class)
class ConsentResourceTest {

    @InjectMock
    SecurityService securityService;

    @BeforeEach
    public void setup() {
        Mockito.when(securityService.getUserId()).thenReturn("012345");
        Mockito.when(securityService.getUserName()).thenReturn("testUser");
    }

    @Test
    void testGetConsentEndpoint_Invalid() {
        given()
                .when().get()
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testGetConsentEndpoint_Valid() {
        given()
                .when().get()
                .then()
                .statusCode(200)
                .body("universityId", is("012345"));
    }

    @Test
    void testSaveConsentEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mockConsentDO())
                .when()
                .post()
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testSaveConsentEndpoint_Valid() {
        given()
                .when().get()
                .then()
                .statusCode(200);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mockConsentDO())
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("universityId", is("012345"));
    }

    public ConsentDO mockConsentDO() {
        ConsentDO consentDO = new ConsentDO();
        consentDO.setUniversityId("012345");
        consentDO.setGivenDate(null);
        consentDO.setConsentGiven(true);
        return consentDO;
    }
}

package at.ac.tuwien.damap.rest;


import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(InternalStorageResource.class)
class InternalStorageResourceTest {

    @Inject
    TestDOFactory testDOFactory;

    @Test
    void testGetAllByLanguageEndpoint_Invalid() {
        given()
                .when().get("/eng")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testGetAllByLanguageEndpoint_Valid() {
        testDOFactory.prepareInternalStorageOption();
        given()
                .when().get("/eng")
                .then()
                .statusCode(200);
    }
}

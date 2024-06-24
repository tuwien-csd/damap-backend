package org.damap.base.r3data;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
class RepositoriesResourceTest {

    @Test
    void testRepositoriesEndpoint_Invalid() {
        given()
                .when().get("/api/repositories")
                .then()
                .statusCode(401);
    }

    @Test
    void testRepositoriesRecommendedEndpoint_Invalid() {
        given()
                .when().get("/api/repositories/recommended")
                .then()
                .statusCode(401);
    }

    @Test
    void testRepositoryByIdEndpoint_Invalid() {
        given()
                .when().get("/api/repositories/1")
                .then()
                .statusCode(401);
    }

    @Test
    void testRepositoriesSearchEndpoint_Invalid() {
        given()
                .when().get("/api/repositories/search?pidSystems=DOI")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testRepositoriesEndpoint_Valid() {
        given()
                .when().get("/api/repositories")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testRepositoriesRecommendedEndpoint_Valid() {
        given()
                .when().get("/api/repositories/recommended")
                .then()
                .statusCode(200)
                .body("id", hasItem("r3d100010468"));
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testRepositoryByIdEndpoint_Valid() {
        given()
                .when().get("/api/repositories/r3d100010468")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    void testRepositoriesSearchEndpoint_Valid() {
        given()
                .when().get("/api/repositories/search?pidSystems=DOI")
                .then()
                .statusCode(200);
    }

}

package at.ac.tuwien.damap.r3data;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
public class RepositoriesResourceTest {

    @Test
    public void testRepositoriesEndpoint_Invalid() {
        given()
                .when().get("/api/repositories")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRepositoriesRecommendedEndpoint_Invalid() {
        given()
                .when().get("/api/repositories/recommended")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRepositoryByIdEndpoint_Invalid() {
        given()
                .when().get("/api/repositories/1")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRepositoriesSearchEndpoint_Invalid() {
        given()
                .when().get("/api/repositories/search?pidSystems=DOI")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "personID", value = "012345")
    })
    public void testRepositoriesEndpoint_Valid() {
        given()
                .when().get("/api/repositories")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "personID", value = "012345")
    })
    public void testRepositoriesRecommendedEndpoint_Valid() {
        given()
                .when().get("/api/repositories/recommended")
                .then()
                .statusCode(200)
                .body("id", hasItem("r3d100013557"));
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "personID", value = "012345")
    })
    public void testRepositoryByIdEndpoint_Valid() {
        given()
                .when().get("/api/repositories/r3d100013557")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "personID", value = "012345")
    })
    public void testRepositoriesSearchEndpoint_Valid() {
        given()
                .when().get("/api/repositories/search?pidSystems=DOI")
                .then()
                .statusCode(200);
    }

}

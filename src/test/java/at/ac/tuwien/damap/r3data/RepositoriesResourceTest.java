package at.ac.tuwien.damap.r3data;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class RepositoriesResourceTest {

    @Test
    public void testRepositoriesEndpoint() {
        given()
                .when().get("/api/repositories")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRepositoryByIdEndpoint() {
        given()
                .when().get("/api/repositories/1")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRepositoriesSearchEndpoint() {
        given()
                .when().get("/api/repositories/search?pidSystems=DOI")
                .then()
                .statusCode(401);
    }

}

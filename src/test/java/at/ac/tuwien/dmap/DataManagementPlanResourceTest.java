package at.ac.tuwien.dmap;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class DataManagementPlanResourceTest {

    @Test
    public void testGetAllPlansEndpoint() {
        given()
            .when().get("/plans/all")
            .then()
            .statusCode(401);
    }
}

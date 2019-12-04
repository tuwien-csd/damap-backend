package at.ac.tuwien.r3data;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RepositoriesResourceTest {

    @Test
    public void testRepositoriesEndpoint() {
        given()
          .when().get("/repositories")
          .then()
             .statusCode(401);
    }

}

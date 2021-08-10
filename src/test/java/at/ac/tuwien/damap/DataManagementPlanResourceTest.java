package at.ac.tuwien.damap;

import at.ac.tuwien.damap.rest.domain.DmpDO;
import at.ac.tuwien.damap.rest.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.service.DmpService;
import at.ac.tuwien.damap.rest.service.SaveDmpWrapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.*;

@QuarkusTest
public class DataManagementPlanResourceTest {

    @InjectMock
    DmpService dmpService;

    @BeforeEach
    public void setup() {
        Mockito.when(dmpService.save(any(SaveDmpWrapper.class))).thenReturn(this.createDmpDO());
        Mockito.when(dmpService.getAll()).thenReturn(this.createDmpListItemDOList());
        Mockito.when(dmpService.getDmpById(anyLong())).thenReturn(this.createDmpDO());
        Mockito.when(dmpService.getDmpListByPersonId(anyString())).thenReturn(this.createDmpListItemDOList());
    }

    @Test
    public void testGetAllPlansEndpoint_Invalid() {
        given()
                .when().get("/dmps/all")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetAllPlansEndpoint_InvalidRole() {
        given()
                .when().get("/dmps/all")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "adminJwt", roles = "admin")
    public void testGetAllPlansEndpoint_ValidRole() {
        given()
                .when().get("/dmps/all")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetPlansByPersonEndpoint_Invalid() {
        given()
                .when().get("/dmps/person/012345")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetPlansByPersonEndpoint_InvalidRole() {
        given()
                .when().get("/dmps/person/012345")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "adminJwt", roles = "admin")
    public void testGetPlansByPersonEndpoint_ValidRole() {
        given()
                .when().get("/dmps/person/012345")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "tissID", value = "012345")
    })
    public void testPlansByPersonEndpoint_Valid() {
        given()
                .when().get("/dmps/list")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPlansByPersonEndpoint_Invalid() {
        given()
                .when().get("/dmps/list")
                .then()
                .statusCode(401);
    }

    @Test
    public void testPlanByIdEndpoint() {
        given()
                .when().get("/dmps/1")
                .then()
                .statusCode(401);
    }

    // TODO: POST & PUT should throw validation exceptions without body

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "tissID", value = "012345")
    })
    public void TestSavePlanEndpoint_Valid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.createDmpDO())
                .when().post("/dmps")
                .then()
                .statusCode(200);
    }

    @Test
    public void TestSavePlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.createDmpDO())
                .when().post("/dmps")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "tissID", value = "012345")
    })
    public void TestUpdatePlanEndpoint_Valid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .when().put("/dmps/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void TestUpdatePlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .when().put("/dmps/1")
                .then()
                .statusCode(401);
    }

    // TODO: Enhance

    private DmpDO createDmpDO() {
        DmpDO dmpDO = new DmpDO();
        dmpDO.setTitle("Mock Dmp");

        return dmpDO;
    }

    private List<DmpListItemDO> createDmpListItemDOList() {
        DmpListItemDO dmpDO = new DmpListItemDO();
        dmpDO.setTitle("Mock Dmp");
        List<DmpListItemDO> list = new ArrayList<>();
        list.add(dmpDO);

        return list;
    }
}

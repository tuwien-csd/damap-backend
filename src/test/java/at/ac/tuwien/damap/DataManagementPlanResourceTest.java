package at.ac.tuwien.damap;

import at.ac.tuwien.damap.rest.DataManagementPlanResource;
import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.DmpListItemDO;
import at.ac.tuwien.damap.rest.dmp.service.DmpService;
import at.ac.tuwien.damap.rest.dmp.service.SaveDmpWrapper;
import at.ac.tuwien.damap.validation.AccessValidator;
import io.quarkus.test.common.http.TestHTTPEndpoint;
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
@TestHTTPEndpoint(DataManagementPlanResource.class)
public class DataManagementPlanResourceTest {

    @InjectMock
    AccessValidator accessValidator;

    @InjectMock
    DmpService dmpService;

    @BeforeEach
    public void setup() {
        Mockito.when(accessValidator.canViewDmp(anyLong(), anyString())).thenReturn(true);
        Mockito.when(accessValidator.canEditDmp(anyLong(), anyString())).thenReturn(true);
        Mockito.when(dmpService.save(any(SaveDmpWrapper.class))).thenReturn(this.createDmpDO());
        Mockito.when(dmpService.getAll()).thenReturn(this.createDmpListItemDOList());
        Mockito.when(dmpService.getDmpById(anyLong())).thenReturn(this.createDmpDO());
        Mockito.when(dmpService.getDmpListByPersonId(anyString())).thenReturn(this.createDmpListItemDOList());
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

/*
    @Test
    public void testGetPlansByPersonEndpoint_Invalid() {
        given()
                .when().get("/person/012345")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetPlansByPersonEndpoint_InvalidRole() {
        given()
                .when().get("/person/012345")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "adminJwt", roles = "Damap Admin")
    public void testGetPlansByPersonEndpoint_ValidRole() {
        given()
                .when().get("/person/012345")
                .then()
                .statusCode(200);
    }
*/

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    @JwtSecurity(claims = {
            @Claim(key = "tissID", value = "012345")
    })
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
    public void testPlanByIdEndpoint() {
        given()
                .when().get("/1")
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
                .when().post("")
                .then()
                .statusCode(200);
    }

    @Test
    public void TestSavePlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.createDmpDO())
                .when().post("")
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
                .when().put("/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void TestUpdatePlanEndpoint_Invalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .when().put("/1")
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

package org.damap.base.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;
import java.util.List;
import org.damap.base.rest.storage.InternalStorageTranslationDO;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(InternalStorageTranslationResource.class)
class InternalStorageTranslationResourceTest {

  @Inject TestDOFactory testDOFactory;

  // Authorization tests
  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testPostEndpointAsUser_Unauthorized() {
    given().contentType("application/json").when().post().then().statusCode(403);
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testPutEndpointAsUser_Unauthorized() {
    given().contentType("application/json").when().put("/1").then().statusCode(403);
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testDeleteEndpointAsUser_Unauthorized() {
    given().when().delete("/1").then().statusCode(403);
  }

  // Get (by ID and all) tests
  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testGetByIdEndpointInvalidID_NotFound() {
    testDOFactory.prepareInternalStorageTranslationOption(false);

    given().when().get("/-1").then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testGetByIdEndpointValidID_Valid() {
    Long id = testDOFactory.prepareInternalStorageTranslationOption(false).get(1);

    InternalStorageTranslationDO response =
        given()
            .when()
            .get("/" + id)
            .then()
            .statusCode(200)
            .extract()
            .as(InternalStorageTranslationDO.class);
    assertEquals("Test Storage Title ENG", response.getTitle());
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testGetAllByStorageEndpointInvalidID_NotFound() {
    testDOFactory.prepareInternalStorageTranslationOption(false);

    given().when().get("/all/-1").then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testGetAllByStorageEndpointValidID_Valid() {
    Long id = testDOFactory.prepareInternalStorageTranslationOption(false).get(0);

    List<InternalStorageTranslationDO> response =
        given().when().get("/all/" + id).then().statusCode(200).extract().as(List.class);
    assertEquals(1, response.size());
  }

  // Create tests
  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testCreateTranslationInvalidStorageID_NotFound() {
    InternalStorageTranslationDO data = new InternalStorageTranslationDO();
    data.setStorageId(-1L);
    data.setTitle("Test Storage Title ENG");
    data.setDescription("Test Storage Description ENG");
    data.setLanguageCode("eng");
    data.setBackupFrequency("Test Storage Backup Frequency ENG");

    given().contentType("application/json").body(data).when().post().then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testCreateTranslationDuplicateLanguageCode_BadRequest() {

    Long id = testDOFactory.prepareInternalStorageTranslationOption(false).get(0);

    InternalStorageTranslationDO data = new InternalStorageTranslationDO();
    data.setStorageId(id);
    data.setTitle("Title");
    data.setBackupFrequency("Test Storage Backup Frequency ENG");
    data.setLanguageCode("eng");
    data.setDescription("Test Storage Description ENG");

    ValidatableResponse response =
        given().contentType("application/json").body(data).when().post().then().statusCode(400);
    response.body("message", startsWith("Translation for language code eng already exists"));
  }

  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testCreateEndpointValidData_Created() {
    Long id = testDOFactory.prepareInternalStorageOption();

    InternalStorageTranslationDO data = new InternalStorageTranslationDO();
    data.setStorageId(id);
    data.setTitle("Test Storage Title DEU");
    data.setDescription("Test Storage Description DEU");
    data.setLanguageCode("deu");
    data.setBackupFrequency("Test Storage Backup Frequency DEU");

    InternalStorageTranslationDO response =
        given()
            .contentType("application/json")
            .body(data)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .as(InternalStorageTranslationDO.class);
    assertEquals("Test Storage Title DEU", response.getTitle());

    List<InternalStorageTranslationDO> response2 =
        given().when().get("/all/" + id).then().statusCode(200).extract().as(List.class);
    assertEquals(2, response2.size());
  }

  // Update tests
  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testUpdateEndpointInvalidID_NotFound() {
    InternalStorageTranslationDO data = new InternalStorageTranslationDO();
    data.setTitle("Test Storage Title ENG");
    data.setDescription("Test Storage Description ENG");
    data.setLanguageCode("eng");
    data.setBackupFrequency("Test Storage Backup Frequency ENG");

    given().contentType("application/json").body(data).when().put("/-1").then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testUpdateEndpointValidData_Updated() {
    List<Long> ids = testDOFactory.prepareInternalStorageTranslationOption(false);

    InternalStorageTranslationDO data = new InternalStorageTranslationDO();
    data.setId(ids.get(1));
    data.setTitle("Test Storage Title DEU");
    data.setDescription("Test Storage Description DEU");
    data.setLanguageCode("deu");
    data.setBackupFrequency("Test Storage Backup Frequency DEU");
    data.setStorageId(ids.get(0));

    InternalStorageTranslationDO response =
        given()
            .contentType("application/json")
            .body(data)
            .when()
            .put("/" + ids.get(1))
            .then()
            .statusCode(200)
            .extract()
            .as(InternalStorageTranslationDO.class);
    assertEquals("Test Storage Title DEU", response.getTitle());

    InternalStorageTranslationDO response2 =
        given()
            .when()
            .get("/" + ids.get(1))
            .then()
            .statusCode(200)
            .extract()
            .as(InternalStorageTranslationDO.class);
    assertEquals("Test Storage Title DEU", response2.getTitle());
  }

  // Delete tests
  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testDeleteEndpointInvalidID_NotFound() {
    given().when().delete("/-1").then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testDeleteEndpointLastTranslation_BadRequest() {
    List<Long> ids = testDOFactory.prepareInternalStorageTranslationOption(false);

    ValidatableResponse response = given().when().delete("/" + ids.get(1)).then().statusCode(400);
    response.body(
        "message", startsWith("Cannot delete the last translation for an internal storage"));
  }

  @Test
  @TestSecurity(user = "adminJwt", roles = "Damap Admin")
  void testDeleteEndpointValidID_Deleted() {
    Long id = testDOFactory.prepareInternalStorageTranslationOption(true).get(2);

    given().when().delete("/" + id).then().statusCode(204);

    given().when().get("/" + id).then().statusCode(404);
  }
}

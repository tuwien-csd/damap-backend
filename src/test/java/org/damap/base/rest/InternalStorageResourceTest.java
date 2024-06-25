package org.damap.base.rest;

import static io.restassured.RestAssured.given;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(InternalStorageResource.class)
class InternalStorageResourceTest {

  @Inject TestDOFactory testDOFactory;

  @Test
  void testGetAllByLanguageEndpoint_Invalid() {
    given().when().get("/eng").then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testGetAllByLanguageEndpoint_Valid() {
    testDOFactory.prepareInternalStorageOption();
    given().when().get("/eng").then().statusCode(200);
  }
}

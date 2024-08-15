package org.damap.base.rest;

import static io.restassured.RestAssured.given;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.damap.base.rest.invenio_damap.InvenioDAMAPResource;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(InvenioDAMAPResource.class)
class InvenioDamapResourceDisabledTest {
  @Test
  void givenConfigNotEnabled_thenNoEndpointsFound() {
    // GET DMPs endpoint
    given().when().get().then().statusCode(404);

    // POST dataset endpoint
    given().when().post().then().statusCode(404);
  }
}

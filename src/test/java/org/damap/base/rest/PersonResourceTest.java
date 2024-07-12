package org.damap.base.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.Search;
import org.damap.base.rest.persons.MockUniversityPersonServiceImpl;
import org.damap.base.security.SecurityService;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
class PersonResourceTest {

  @Inject TestDOFactory testDOFactory;

  @InjectMock SecurityService securityService;

  @InjectMock MockUniversityPersonServiceImpl mockPersonService;

  @BeforeEach
  public void setup() {
    Mockito.when(securityService.getUserId()).thenReturn("012345");
    Mockito.when(securityService.getUserName()).thenReturn("testUser");
    Mockito.when(mockPersonService.read(anyString(), notNull()))
        .thenReturn(testDOFactory.getTestContributorDO());
    Mockito.when(mockPersonService.search(any()))
        .thenReturn(
            ResultList.fromItemsAndSearch(
                List.of(testDOFactory.getTestContributorDO(), testDOFactory.getTestContributorDO()),
                new Search()));
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testReadPerson() {
    MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
    params.add("searchService", "University");

    given().params(params).get("/1").then().statusCode(200).body("firstName", equalTo("Jane"));

    verify(mockPersonService, times(1)).read("1", params);
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testSearchPersons() {
    MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
    params.add("searchService", "University");

    given().params(params).get("/").then().statusCode(200).body("items.size()", equalTo(2));

    verify(mockPersonService, times(1)).search(params);
  }
}

package org.damap.base.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
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
import java.util.List;
import org.damap.base.rest.base.ResultList;
import org.damap.base.rest.base.Search;
import org.damap.base.rest.projects.MockProjectServiceImpl;
import org.damap.base.security.SecurityService;
import org.damap.base.util.TestDOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@TestHTTPEndpoint(ProjectResource.class)
class ProjectResourceTest {

  @Inject TestDOFactory testDOFactory;

  @InjectMock SecurityService securityService;

  @InjectMock MockProjectServiceImpl mockProjectService;

  @BeforeEach
  public void setup() {
    Mockito.when(securityService.getUserId()).thenReturn("012345");
    Mockito.when(securityService.getUserName()).thenReturn("testUser");
    Mockito.when(mockProjectService.read(anyString())).thenReturn(testDOFactory.getTestProjectDO());
    Mockito.when(mockProjectService.getRecommended(any()))
        .thenReturn(
            ResultList.fromItemsAndSearch(
                List.of(testDOFactory.getRecommendedTestProjectDO()), new Search()));
  }

  @Test
  @TestSecurity(user = "userJwt", roles = "user")
  void testGetRecommendedProjects() {
    given()
        .get("/recommended")
        .then()
        .statusCode(200)
        .body("items.size()", equalTo(1))
        .body("items.title", everyItem(containsStringIgnoringCase("recommend")));

    verify(mockProjectService, times(1)).getRecommended(notNull());
  }
}

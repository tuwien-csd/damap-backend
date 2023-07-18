package at.ac.tuwien.damap.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import at.ac.tuwien.damap.rest.base.ResultList;
import at.ac.tuwien.damap.rest.base.Search;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
@TestHTTPEndpoint(ProjectResource.class)
public class ProjectResourceTest {

    @Inject
    TestDOFactory testDOFactory;

    @InjectMock
    SecurityService securityService;

    @InjectMock
    MockProjectServiceImpl mockProjectService;

    @BeforeEach
    public void setup() {
        Mockito.when(securityService.getUserId()).thenReturn("012345");
        Mockito.when(securityService.getUserName()).thenReturn("testUser");
        Mockito.when(mockProjectService.read(anyString())).thenReturn(testDOFactory.getTestProjectDO());
        Mockito.when(mockProjectService.getRecommended(any())).thenReturn(
                ResultList.fromItemsAndSearch(List.of(testDOFactory.getRecommendedTestProjectDO()), new Search()));
    }

    @Test
    @TestSecurity(user = "userJwt", roles = "user")
    public void testGetRecommendedProjects() {
        given()
                .get("/recommended")
                .then()
                .statusCode(200)
                .body("items.size()", equalTo(1))
                .body("items.title", everyItem(containsStringIgnoringCase("recommend")));

        verify(mockProjectService, times(1)).getRecommended(notNull());
    }
}

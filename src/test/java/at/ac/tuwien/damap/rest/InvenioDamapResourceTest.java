package at.ac.tuwien.damap.rest;

import at.ac.tuwien.damap.rest.dmp.domain.DmpDO;
import at.ac.tuwien.damap.rest.dmp.domain.DatasetDO;
import at.ac.tuwien.damap.rest.madmp.dto.Dataset;
import at.ac.tuwien.damap.rest.invenio_damap.InvenioDAMAPResource;
import at.ac.tuwien.damap.rest.projects.MockProjectServiceImpl;
import at.ac.tuwien.damap.security.SecurityService;
import at.ac.tuwien.damap.util.TestDOFactory;
import io.quarkus.runtime.util.StringUtil;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import at.ac.tuwien.damap.rest.dmp.mapper.DatasetDOMapper;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.BeforeEach;
import at.ac.tuwien.damap.rest.dmp.mapper.MapperService;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.util.List;

import io.restassured.http.Header;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;

@QuarkusTest
@TestHTTPEndpoint(InvenioDAMAPResource.class)
class InvenioDamapResourceTest {

    @Inject
    TestDOFactory testDOFactory;

    @Inject
    MapperService mapperService;

    @InjectMock
    SecurityService securityService;

    final String personEndpoint = "/person";
    // TODO: when upgrading to quarkus version > v3.2, use TestConfigProperty on the
    // test class to set it specifically for these Tests.
    // See https://quarkus.io/guides/getting-started-testing#testing-components

    final Header authHeader = new Header("Authorization", "secret stuff or token");

    private String getUserId() {
        return RandomStringUtils.randomAlphanumeric(64);
    }

    @Test
    void getDmpsForPersonThenUnauthorized() {
        String userId = getUserId();
        given()
                .when()
                .get(String.format("%s/%s", personEndpoint, userId))
                .then()
                .statusCode(403);

        given()
                .when()
                .header("Authorization", "wrong-credentials")
                .get(String.format("%s/%s", personEndpoint, userId))
                .then()
                .statusCode(403);

    }

    @Test
    void getDmpsForPersonThenValid() {
        String userId = getUserId();
        given()
                .when()
                .header(authHeader)
                .get(String.format("%s/%s", personEndpoint, userId))
                .then()
                .statusCode(200)
                .body("", empty());

        DmpDO dmpDO = testDOFactory.createDmp("invenio-damap", true, userId);

        given()
                .when()
                .header(authHeader)
                .get(String.format("%s/%s", personEndpoint, userId))
                .then()
                .statusCode(200)
                .body("", hasSize(1))
                .body("[0].title", equalTo(dmpDO.getTitle()));

    }

    @Test
    void addDataSetToDmpThenValid() throws com.fasterxml.jackson.core.JsonProcessingException {
        String datasetData = """
                    {
                    "dataset_id" : {
                        "type": "doi",
                        "identifier": "repository/123.456"
                    },
                    "description" : "Dataset description",
                    "distribution" : [ {
                    "access_url" : "https://repository.tugraz.at/",
                    "byte_size" : 705032704,
                    "data_access" : "open",
                    "description" : "Distribution description",
                    "format" : [ "Standard office documents", "Images", "Raw data", "Scientific and statistical data formats", "Plain text" ],
                    "host" : {
                        "description" : "An institutional repository at Graz University of Technology to enable storing, sharing and publishing research data, publications and open educational resources. It provides open access services and follows the FAIR principles.",
                        "pid_system" : [ "doi" ],
                        "storage_type" : "institutional",
                        "support_versioning" : "yes",
                        "title" : "TU Graz Repository",
                        "url" : "https://repository.tugraz.at/"
                    },
                    "license" : [ {
                        "license_ref" : "https://creativecommons.org/licenses/by-nc-sa/4.0/"
                    } ],
                    "title" : "Host title"
                    } ],
                    "personal_data" : "no",
                    "security_and_privacy" : [ ],
                    "sensitive_data" : "no",
                    "title" : "Dataset title",
                    "type" : "Standard office documents, Images, Raw data, Scientific and statistical data formats, Plain text"
                }""";

        String userId = getUserId();
        DmpDO dmpDO = testDOFactory.createDmp("invenio-damap", true, userId);
        Dataset madmpDataset = (new ObjectMapper()).readValue(datasetData, Dataset.class);

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .body(madmpDataset)
                .post(String.format("/%s/%s", dmpDO.getId(), userId))
                .then()
                .statusCode(200)
                .body("", not(empty()))
                .body("datasets", hasSize(3))
                .body("datasets[2].title", equalTo(madmpDataset.getTitle()));

        Dataset minimalDataset = new Dataset();
        minimalDataset.setTitle("minimal");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .body(minimalDataset)
                .post(String.format("/%s/%s", dmpDO.getId(), userId))
                .then()
                .statusCode(200)
                .body("", not(empty()))
                .body("datasets", hasSize(4))
                .body("datasets[3].title", equalTo(minimalDataset.getTitle()));
    }

}
